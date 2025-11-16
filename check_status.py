#!/usr/bin/env python3
"""System status checker - verifies everything is ready."""

from pathlib import Path
import sys

PROJECT_ROOT = Path(__file__).parent.resolve()
CODEBASE_PATH = PROJECT_ROOT / "codebase"

print("="*70)
print("TEST COVERAGE IMPROVEMENT SYSTEM - STATUS CHECK")
print("="*70)

errors = []
warnings = []

# Check Python
print("\n1. Python Environment:")
try:
    import google.genai
    print("   ✅ google-generativeai installed")
except ImportError:
    errors.append("google-generativeai not installed")
    print("   ❌ google-generativeai NOT installed")

try:
    import fastmcp
    print("   ✅ fastmcp installed")
except ImportError:
    warnings.append("fastmcp not installed (only needed for MCP server)")
    print("   ⚠️  fastmcp NOT installed (optional)")

# Check project structure
print("\n2. Project Structure:")
if CODEBASE_PATH.exists():
    print(f"   ✅ Codebase found: {CODEBASE_PATH}")
else:
    errors.append("Codebase directory not found")
    print(f"   ❌ Codebase NOT found: {CODEBASE_PATH}")

# Check coverage report
jacoco_report = CODEBASE_PATH / "target/site/jacoco/jacoco.xml"
if jacoco_report.exists():
    print(f"   ✅ Coverage report exists: {jacoco_report}")
else:
    warnings.append("Coverage report not found - run 'mvn verify' first")
    print(f"   ⚠️  Coverage report NOT found: {jacoco_report}")

# Check scripts
print("\n3. Workflow Scripts:")
scripts = [
    "quick_start.py",
    "run_full_workflow.py",
    "run_workflow.bat"
]
for script in scripts:
    if (PROJECT_ROOT / script).exists():
        print(f"   ✅ {script}")
    else:
        errors.append(f"{script} not found")
        print(f"   ❌ {script}")

# Check documentation
print("\n4. Documentation:")
docs = [
    "README.md",
    "TEST_COVERAGE_WORKFLOW.md",
    "START_HERE.txt",
    "EXECUTION_SUMMARY.md"
]
for doc in docs:
    if (PROJECT_ROOT / doc).exists():
        print(f"   ✅ {doc}")
    else:
        warnings.append(f"{doc} not found")
        print(f"   ⚠️  {doc}")

# Check fixed file
print("\n5. Compilation Fix:")
test_file = CODEBASE_PATH / "src/test/java/org/apache/commons/lang3/text/translate/CharSequenceTranslatorTest.java"
if test_file.exists():
    content = test_file.read_text()
    if "import java.io.Writer;" in content:
        print("   ✅ Writer import present in CharSequenceTranslatorTest.java")
    else:
        errors.append("Writer import missing in test file")
        print("   ❌ Writer import MISSING in CharSequenceTranslatorTest.java")
else:
    errors.append("CharSequenceTranslatorTest.java not found")
    print("   ❌ CharSequenceTranslatorTest.java NOT found")

# Summary
print("\n" + "="*70)
if errors:
    print("STATUS: ❌ ERRORS FOUND")
    print("\nErrors:")
    for err in errors:
        print(f"  • {err}")
elif warnings:
    print("STATUS: ⚠️  READY (with warnings)")
    print("\nWarnings:")
    for warn in warnings:
        print(f"  • {warn}")
else:
    print("STATUS: ✅ FULLY READY")

print("\n" + "="*70)

if not errors:
    print("\n🚀 READY TO START!\n")
    print("Run this command to generate your first test:")
    print("\n    python quick_start.py\n")
    print("="*70)
else:
    print("\n⚠️  Please fix errors before proceeding.")
    print("="*70)
    sys.exit(1)
