#!/usr/bin/env python3
"""Generate JaCoCo coverage report."""

import subprocess
import sys
from pathlib import Path

PROJECT_ROOT = Path(__file__).parent.resolve()
CODEBASE_PATH = PROJECT_ROOT / "codebase"

print("="*70)
print("GENERATING JACOCO COVERAGE REPORT")
print("="*70)
print(f"\nCodebase: {CODEBASE_PATH}")
print("\nRunning: mvn clean verify")
print("This may take 1-2 minutes...\n")
print("-"*70)

try:
    result = subprocess.run(
        ["mvn", "clean", "verify"],
        cwd=str(CODEBASE_PATH),
        shell=True,
        timeout=300
    )
    
    print("-"*70)
    
    if result.returncode == 0:
        jacoco_report = CODEBASE_PATH / "target/site/jacoco/jacoco.xml"
        if jacoco_report.exists():
            print("\n✅ SUCCESS! Coverage report generated at:")
            print(f"   {jacoco_report}")
            print("\n" + "="*70)
            print("Next step: Run 'python quick_start.py' to generate tests")
            print("="*70)
        else:
            print("\n⚠️  Build succeeded but jacoco.xml not found at:")
            print(f"   {jacoco_report}")
            print("\n   Checking target/site directory...")
            site_dir = CODEBASE_PATH / "target/site"
            if site_dir.exists():
                print(f"   Contents of {site_dir}:")
                for item in site_dir.iterdir():
                    print(f"     - {item.name}")
            else:
                print(f"   Site directory does not exist: {site_dir}")
    else:
        print(f"\n❌ Maven build failed with return code {result.returncode}")
        sys.exit(1)
        
except subprocess.TimeoutExpired:
    print("\n❌ Maven build timed out after 5 minutes")
    sys.exit(1)
except Exception as e:
    print(f"\n❌ Error: {e}")
    sys.exit(1)
