import xml.etree.ElementTree as ET

# Parse the XML file
tree = ET.parse(r'c:\Users\bamha\finalproject\codebase\target\site\jacoco\jacoco.xml')
root = tree.getroot()

classes_data = []

# Iterate through packages
for package in root.findall('.//package'):
    package_name = package.get('name').replace('/', '.')
    
    # Skip test packages
    if 'test' in package_name.lower():
        continue
    
    # Only consider org.apache.commons.lang3 packages
    if not package_name.startswith('org.apache.commons.lang3'):
        continue
    
    # Iterate through classes in the package
    for cls in package.findall('class'):
        class_name = cls.get('name').split('/')[-1]
        full_class_name = package_name + '.' + class_name
        
        # Skip inner classes and test classes
        if '$' in class_name or 'Test' in class_name:
            continue
        
        # Get counters
        instruction_counter = None
        branch_counter = None
        
        for counter in cls.findall('counter'):
            if counter.get('type') == 'INSTRUCTION':
                instruction_counter = counter
            elif counter.get('type') == 'BRANCH':
                branch_counter = counter
        
        if instruction_counter is not None:
            missed_instructions = int(instruction_counter.get('missed', 0))
            covered_instructions = int(instruction_counter.get('covered', 0))
            total_instructions = missed_instructions + covered_instructions
            
            # Filter: more than 50 instructions
            if total_instructions <= 50:
                continue
            
            instruction_coverage = (covered_instructions / total_instructions * 100) if total_instructions > 0 else 0
            
            missed_branches = 0
            covered_branches = 0
            branch_coverage = 0
            
            if branch_counter is not None:
                missed_branches = int(branch_counter.get('missed', 0))
                covered_branches = int(branch_counter.get('covered', 0))
                total_branches = missed_branches + covered_branches
                branch_coverage = (covered_branches / total_branches * 100) if total_branches > 0 else 0
            
            # Filter: coverage less than 90%
            if instruction_coverage < 90 or branch_coverage < 90:
                classes_data.append({
                    'class': full_class_name,
                    'inst_cov': instruction_coverage,
                    'branch_cov': branch_coverage,
                    'missed_inst': missed_instructions,
                    'missed_branch': missed_branches,
                    'total_inst': total_instructions
                })

# Sort by lowest coverage (considering both instruction and branch)
classes_data.sort(key=lambda x: min(x['inst_cov'], x['branch_cov']))

# Print top 10
print('\n' + '='*130)
print(f"{'Rank':<6}{'Class Name':<65}{'Inst %':<10}{'Branch %':<12}{'Missed Inst':<15}{'Missed Branch'}")
print('='*130)

for i, cls_data in enumerate(classes_data[:10], 1):
    print(f"{i:<6}{cls_data['class']:<65}{cls_data['inst_cov']:<10.2f}{cls_data['branch_cov']:<12.2f}{cls_data['missed_inst']:<15}{cls_data['missed_branch']}")

print('='*130)
print(f'\nTotal classes needing improvement: {len(classes_data)}')
print('\nNote: Classes filtered by:')
print('  - Coverage < 90% (instruction OR branch)')
print('  - Total instructions > 50')
print('  - From org.apache.commons.lang3 packages (excluding test classes)')
