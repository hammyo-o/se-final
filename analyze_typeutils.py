import xml.etree.ElementTree as ET
import sys

tree = ET.parse('c:/Users/bamha/finalproject/codebase/target/site/jacoco/jacoco.xml')
root = tree.getroot()

# Get class name from args or use Conversion
target_package = sys.argv[1] if len(sys.argv) > 1 else 'org/apache/commons/lang3'
target_class = sys.argv[2] if len(sys.argv) > 2 else 'Conversion'

# Find target class
for package in root.findall('.//package'):
    if package.get('name') == target_package:
        for cls in package.findall('class'):
            if target_class in cls.get('name') and cls.get('name').endswith(target_class):
                print(f"Class: {cls.get('name')}\n")
                
                # Find methods with missed branches
                methods = []
                for method in cls.findall('method'):
                    method_name = method.get('name')
                    method_desc = method.get('desc')
                    
                    missed_branches = 0
                    covered_branches = 0
                    
                    for counter in method.findall('counter'):
                        if counter.get('type') == 'BRANCH':
                            missed_branches = int(counter.get('missed'))
                            covered_branches = int(counter.get('covered'))
                    
                    if missed_branches > 0:
                        methods.append((method_name, method_desc, missed_branches, covered_branches))
                
                # Sort by missed branches
                methods.sort(key=lambda x: x[2], reverse=True)
                
                print("Top 10 methods with most missed branches:\n")
                for name, desc, missed, covered in methods[:10]:
                    total = missed + covered
                    pct = 100 * covered / total if total > 0 else 0
                    print(f"{name}{desc}")
                    print(f"  Missed: {missed}, Covered: {covered}, Total: {total}, Coverage: {pct:.1f}%\n")
