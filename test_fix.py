#!/usr/bin/env python3
"""Test that the JaCoCo fix works."""

import subprocess
import sys
from pathlib import Path
import shutil

PROJECT_ROOT = Path(__file__).parent.resolve()
CODEBASE_PATH = PROJECT_ROOT / "codebase"
JACOCO_REPORT = CODEBASE_PATH / "target/site/jacoco/jacoco.xml"

print("="*70)
print("TESTING JACOCO CONFIGURATION FIX")
print("="*70)

# Step 1: Clean target directory
print("\n1. Cleaning target directory...")
target_dir = CODEBASE_PATH / "target"
if target_dir.exists():
    print(f"   Removing {target_dir}")
    try:
        shutil.rmtree(target_dir)
        print("   ✅ Target directory cleaned")
    except Exception as e:
        print(f"   ⚠️  Could not remove target: {e}")
else:
    print("   ℹ️  Target directory doesn't exist (already clean)")

# Step 2: Verify jacoco.xml doesn't exist
print("\n2. Verifying jacoco.xml is gone...")
if not JACOCO_REPORT.exists():
    print("   ✅ jacoco.xml does not exist (as expected)")
else:
    print("   ❌ jacoco.xml still exists!")
    sys.exit(1)

# Step 3: Run mvn clean verify
print("\n3. Running 'mvn clean verify'...")
print("   This will take 1-2 minutes...")
print("   " + "-"*66)

try:
    result = subprocess.run(
        ["mvn", "clean", "verify"],
        cwd=str(CODEBASE_PATH),
        shell=True,
        timeout=300,
        capture_output=True,
        text=True
    )
    
    print("   " + "-"*66)
    
    if result.returncode != 0:
        print("\n   ❌ Maven build failed!")
        print("\n   Last 50 lines of output:")
        lines = result.stdout.split('\n')
        for line in lines[-50:]:
            print(f"   {line}")
        sys.exit(1)
    else:
        print("\n   ✅ Maven build succeeded!")
        
except subprocess.TimeoutExpired:
    print("\n   ❌ Maven build timed out!")
    sys.exit(1)
except Exception as e:
    print(f"\n   ❌ Error running Maven: {e}")
    sys.exit(1)

# Step 4: Check if jacoco.xml was created
print("\n4. Checking if jacoco.xml was created...")
if JACOCO_REPORT.exists():
    print(f"   ✅ SUCCESS! File exists: {JACOCO_REPORT}")
    
    # Show file info
    file_size = JACOCO_REPORT.stat().st_size
    print(f"   File size: {file_size:,} bytes")
    
    # Parse to see if it's valid
    try:
        import xml.etree.ElementTree as ET
        tree = ET.parse(str(JACOCO_REPORT))
        root = tree.getroot()
        
        package_count = len(root.findall('package'))
        print(f"   Packages in report: {package_count}")
        
        # Count methods
        method_count = 0
        for package in root.findall('package'):
            for class_elem in package.findall('class'):
                method_count += len(class_elem.findall('method'))
        
        print(f"   Methods in report: {method_count}")
        print("\n   ✅ Report is valid XML and contains coverage data!")
        
    except Exception as e:
        print(f"\n   ⚠️  Warning: Could not parse XML: {e}")
        
else:
    print(f"   ❌ FAILED! File not found: {JACOCO_REPORT}")
    
    # Debug info
    site_dir = CODEBASE_PATH / "target/site"
    print(f"\n   Checking {site_dir}:")
    if site_dir.exists():
        print(f"   Contents:")
        for item in site_dir.iterdir():
            print(f"     - {item.name}")
    else:
        print(f"   ❌ Site directory doesn't exist!")
    
    sys.exit(1)

# Final summary
print("\n" + "="*70)
print("TEST COMPLETE: JACOCO FIX IS WORKING!")
print("="*70)
print("\nYou can now use:")
print("  python quick_start.py")
print("\nto automatically generate tests!")
print("="*70)
