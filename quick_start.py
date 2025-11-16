#!/usr/bin/env python3
"""Quick start - analyze existing coverage and generate one test immediately."""

import sys
from pathlib import Path
import xml.etree.ElementTree as ET
import re
from google import genai
import subprocess
import time
from google.genai import errors as genai_errors

PROJECT_ROOT = Path(__file__).parent.resolve()
CODEBASE_PATH = PROJECT_ROOT / "codebase"
model_name = "gemini-2.5-pro"
client = genai.Client(api_key="AIzaSyBNkWDRQkdkyq7zfobp7JAzgnFpwQqdtbg")

def get_package_name(java_file_content: str) -> str:
    match = re.search(r"^\s*package\s+([^;]+);", java_file_content, re.MULTILINE)
    return match.group(1) if match else ""

print("="*70)
print("QUICK TEST GENERATION")
print("="*70)

# Check for coverage report
jacoco_report = CODEBASE_PATH / "target/site/jacoco/jacoco.xml"
print(f"\n1. Checking for coverage report...")

if not jacoco_report.exists():
    print(f"   ❌ Coverage report not found: {jacoco_report}")
    print("\n   Generating coverage report with Maven...")
    try:
        with open(CODEBASE_PATH / "maven_output.txt", "w", encoding='utf-8') as f:
            result = subprocess.run(
                ["mvn", "clean", "verify", "-X"],
                cwd=str(CODEBASE_PATH),
                stdout=f,
                stderr=subprocess.STDOUT,
                text=True,
                shell=True,
                timeout=300
            )
        if result.returncode != 0:
            print("   ❌ Maven build failed! See maven_output.txt for details.")
            sys.exit(1)
        print("   ✅ Coverage report generated!")
    except Exception as e:
        print(f"   ❌ Error running Maven: {e}")
        sys.exit(1)
else:
    print(f"   ✅ Coverage report found")

# Parse coverage
print(f"\n2. Parsing coverage report...")

try:
    tree = ET.parse(str(jacoco_report))
    root = tree.getroot()
    
    found = False
    for package in root.findall('package'):
        for class_elem in package.findall('class'):
            class_name_attr = class_elem.get('name')
            if not class_name_attr:
                continue
            
            for method in class_elem.findall('method'):
                counter = method.find("./counter[@type='INSTRUCTION']")
                
                if counter is not None:
                    missed = counter.get('missed')
                    if missed and int(missed) > 0:
                        source_file = class_elem.get('sourcefilename')
                        class_name = class_name_attr.split('/')[-1]
                        method_name = method.get('name')
                        method_sig = f"{method_name}{method.get('desc')}"
                        package_path = package.get('name')
                        full_path = CODEBASE_PATH / f"src/main/java/{package_path}/{source_file}"
                        
                        print(f"\n3. Found uncovered method:")
                        print(f"   Class: {class_name}")
                        print(f"   Method: {method_sig}")
                        print(f"   File: {full_path}")
                        
                        # Read source
                        source_content = full_path.read_text(encoding='utf-8')
                        package_name = get_package_name(source_content)
                        test_file = CODEBASE_PATH / f"src/test/java/{package_name.replace('.', '/')}/{class_name}Test.java"
                        
                        print(f"\n4. Target test file: {test_file}")
                        
                        if not test_file.exists():
                            print(f"   ERROR: Test file does not exist!")
                            sys.exit(1)
                        
                        # Generate test
                        print(f"\n5. Calling Gemini API ({model_name})...")
                        
                        snippet = source_content[:2000] if len(source_content) > 2000 else source_content
                        prompt = f"""Write a JUnit 5 test method for:
Class: {class_name}
Method: {method_sig}

Source context:
```java
{snippet}
```

Requirements:
- Use @Test annotation
- Descriptive test name
- Include assertions
- Output ONLY the test method code"""

                        response = None
                        code = None
                        for i in range(3):
                            try:
                                response = client.models.generate_content(model=model_name, contents=prompt)
                                code = response.text
                                break
                            except genai_errors.ServerError as e:
                                print(f"   API call failed with {e}. Retrying in 5 seconds...")
                                time.sleep(5)
                        
                        if not code:
                            print("\n6. Failed to generate test after multiple retries.")
                            sys.exit(1)
                        
                        if code:
                            print("\n6. Generated test:")
                            print("-"*70)
                            print(code[:300] + "..." if len(code) > 300 else code)
                            print("-"*70)
                            
                            # Extract code
                            if "```java" in code:
                                code = code.split("```java", 1)[1].split("```", 1)[0]
                            elif "```" in code:
                                parts = code.split("```")
                                if len(parts) >= 3:
                                    code = parts[1]
                            
                            new_method = f"\n    {code.strip()}\n"
                            
                            # Add to test file
                            test_content = test_file.read_text(encoding='utf-8')
                            last_brace = test_content.rfind('}')
                            if last_brace == -1:
                                print("ERROR: Cannot find insertion point")
                                sys.exit(1)
                            
                            updated = test_content[:last_brace].strip() + "\n" + new_method + "}\n"
                            test_file.write_text(updated, encoding='utf-8')
                            
                            print(f"\n7. Test added to: {test_file}")
                            print("\n" + "="*70)
                            print("SUCCESS! Test generated and added.")
                            print("="*70)
                            print("\nNext steps:")
                            print("  1. Review the test in your editor")
                            print("  2. Run: cd codebase && mvn test")
                            print("  3. Run: python quick_start.py (to generate more tests)")
                            print("="*70)
                        else:
                            print("\n6. No test generated.")

                        found = True
                        break
            if found:
                break
        if found:
            break
    
    if not found:
        print("\nNo uncovered methods found!")
        
except Exception as e:
    print(f"\nERROR: {e}")
    import traceback
    traceback.print_exc()
    sys.exit(1)
