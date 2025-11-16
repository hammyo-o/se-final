#!/usr/bin/env python3
"""
Automated Test Coverage Improvement Workflow
Runs the complete process from Maven build to test generation.
"""

import subprocess
import sys
from pathlib import Path
import xml.etree.ElementTree as ET
import re
from google import genai
import time

# Configuration
PROJECT_ROOT = Path(__file__).parent.resolve()
CODEBASE_PATH = PROJECT_ROOT / "codebase"
model_name = "gemini-2.5-pro"
client = genai.Client(api_key="AIzaSyBNkWDRQkdkyq7zfobp7JAzgnFpwQqdtbg")

def run_command(cmd, cwd):
    """Run a shell command and return success status."""
    print(f"Running: {' '.join(cmd)}")
    try:
        result = subprocess.run(
            cmd,
            cwd=str(cwd),
            capture_output=True,
            text=True,
            shell=True,
            timeout=300
        )
        if result.returncode != 0:
            print(f"Command failed with return code {result.returncode}")
            if result.stdout:
                print("STDOUT:", result.stdout[-500:])
            if result.stderr:
                print("STDERR:", result.stderr[-500:])
            return False
        print("Command completed successfully")
        return True
    except subprocess.TimeoutExpired:
        print("Command timed out")
        return False
    except Exception as e:
        print(f"Error running command: {e}")
        return False

def get_package_name(java_file_content: str) -> str:
    """Parses the package name from the content of a Java file."""
    match = re.search(r"^\s*package\s+([^;]+);", java_file_content, re.MULTILINE)
    return match.group(1) if match else ""

def run_maven_verify():
    """Run Maven clean verify to generate coverage report."""
    print("\n" + "="*60)
    print("Step 1: Running Maven clean verify")
    print("="*60)
    return run_command(["mvn", "clean", "verify"], CODEBASE_PATH)

def find_uncovered_method():
    """Parse JaCoCo XML report to find an uncovered method."""
    print("\n" + "="*60)
    print("Step 2: Finding uncovered methods")
    print("="*60)
    
    jacoco_report_path = CODEBASE_PATH / "target/site/jacoco/jacoco.xml"
    print(f"Parsing: {jacoco_report_path}")
    
    try:
        tree = ET.parse(str(jacoco_report_path))
        root = tree.getroot()
        
        for package in root.findall('package'):
            for class_elem in package.findall('class'):
                class_name_attr = class_elem.get('name')
                if not class_name_attr:
                    continue
                
                for method in class_elem.findall('method'):
                    counter = method.find("./counter[@type='INSTRUCTION']")
                    
                    if counter is not None:
                        missed_count_str = counter.get('missed')
                        if missed_count_str and int(missed_count_str) > 0:
                            source_file = class_elem.get('sourcefilename')
                            class_name = class_name_attr.split('/')[-1]
                            method_name = method.get('name')
                            method_signature = f"{method_name}{method.get('desc')}"
                            package_path = package.get('name')
                            full_path = CODEBASE_PATH / f"src/main/java/{package_path}/{source_file}"
                            
                            print(f"✓ Found: {class_name}.{method_signature}")
                            return str(full_path), class_name, method_signature
        
        print("No uncovered methods found!")
        return None, None, None
    except Exception as e:
        print(f"Error: {e}")
        return None, None, None

def generate_and_add_test(source_file_path: str, class_name: str, method_to_test: str):
    """Generate test using Gemini and add to test file."""
    print("\n" + "="*60)
    print("Step 3: Generating test with Gemini API")
    print("="*60)
    
    source_file = Path(source_file_path)
    if not source_file.is_file():
        print(f"Error: Source file not found: {source_file_path}")
        return False
    
    print(f"Source: {source_file}")
    
    source_content = source_file.read_text(encoding='utf-8')
    package_path = get_package_name(source_content).replace(".", "/")
    test_class_name = f"{class_name}Test"
    test_file_path = CODEBASE_PATH / f"src/test/java/{package_path}/{test_class_name}.java"
    
    print(f"Target test file: {test_file_path}")
    
    if not test_file_path.exists():
        print(f"Error: Test file does not exist: {test_file_path}")
        return False
    
    # Prepare context
    source_snippet = source_content[:2000] if len(source_content) > 2000 else source_content
    
    prompt = f"""You are an expert Java developer specializing in JUnit 5 testing.

Write a single, complete JUnit test method for:
Class: {class_name}
Method: {method_to_test}

Context (source code):
```java
{source_snippet}
```

Requirements:
- Use JUnit 5 (@Test annotation)
- Descriptive test method name
- Include meaningful assertions
- Provide ONLY the test method code, no explanations
- Make sure all imports are standard Java/JUnit

Output only the raw Java code for the @Test method."""
    
    try:
        print("Calling Gemini API...")
        response = client.models.generate_content(model=model_name, contents=prompt)
        generated_text = response.text
        
        if not generated_text or not generated_text.strip():
            print("Error: Empty response from Gemini")
            return False
        
        print("\nGenerated test code:")
        print("-" * 60)
        print(generated_text[:500])
        print("-" * 60)
        
        # Extract code
        code_to_add = generated_text
        if "```java" in code_to_add:
            code_to_add = code_to_add.split("```java", 1)[1].split("```", 1)[0]
        elif "```" in code_to_add:
            parts = code_to_add.split("```")
            if len(parts) >= 3:
                code_to_add = parts[1]
        
        new_test_method_code = f"\n    {code_to_add.strip()}\n"
        
        # Read current test file
        current_test_content = test_file_path.read_text(encoding='utf-8')
        last_brace_index = current_test_content.rfind('}')
        if last_brace_index == -1:
            print("Error: Cannot find insertion point")
            return False
        
        # Insert new test
        updated_test_content = current_test_content[:last_brace_index].strip() + "\n" + new_test_method_code + "}\n"
        test_file_path.write_text(updated_test_content, encoding='utf-8')
        
        print(f"✓ Test added to {test_file_path}")
        return True
    except Exception as e:
        print(f"Error: {e}")
        import traceback
        traceback.print_exc()
        return False

def verify_tests():
    """Run Maven test to verify the new test works."""
    print("\n" + "="*60)
    print("Step 4: Verifying new test compiles and passes")
    print("="*60)
    return run_command(["mvn", "test"], CODEBASE_PATH)

def commit_changes(class_name, method_sig):
    """Commit changes to git."""
    print("\n" + "="*60)
    print("Step 5: Committing changes")
    print("="*60)
    
    try:
        subprocess.run(["git", "add", "."], cwd=str(PROJECT_ROOT), shell=True, check=True)
        result = subprocess.run(
            ["git", "commit", "-m", f"Add test for {class_name}.{method_sig}"],
            cwd=str(PROJECT_ROOT),
            capture_output=True,
            text=True,
            shell=True
        )
        if "nothing to commit" in result.stdout.lower():
            print("No changes to commit")
            return True
        print(f"✓ Committed: Add test for {class_name}.{method_sig}")
        return True
    except Exception as e:
        print(f"Git error: {e}")
        return False

def main():
    """Run the complete workflow."""
    print("\n" + "="*80)
    print("AUTOMATED TEST COVERAGE IMPROVEMENT WORKFLOW")
    print("="*80)
    print(f"Target: {CODEBASE_PATH}")
    print(f"Model: {model_name}")
    print("="*80)
    
    # Step 1: Build and generate coverage
    if not run_maven_verify():
        print("\n❌ FAILED: Could not build project and generate coverage")
        sys.exit(1)
    
    # Step 2: Find uncovered method
    source_file, class_name, method_sig = find_uncovered_method()
    if not source_file:
        print("\n✅ SUCCESS: No uncovered methods found!")
        sys.exit(0)
    
    # Step 3: Generate test
    if not generate_and_add_test(source_file, class_name, method_sig):
        print("\n❌ FAILED: Could not generate test")
        sys.exit(1)
    
    # Step 4: Verify tests pass
    if not verify_tests():
        print("\n⚠️ WARNING: Tests may have issues - please review manually")
        # Don't exit, continue to commit
    
    # Step 5: Commit
    commit_changes(class_name, method_sig)
    
    print("\n" + "="*80)
    print("✅ WORKFLOW COMPLETE!")
    print("="*80)
    print("\nNext steps:")
    print("1. Review the generated test")
    print("2. Run this script again to improve coverage further")
    print("3. Run 'mvn verify' to regenerate coverage report")
    print("="*80)

if __name__ == "__main__":
    main()
