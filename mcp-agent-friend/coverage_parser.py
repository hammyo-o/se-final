import xml.etree.ElementTree as ET

JACOCO_XML = r"C:\se333\testing-agent-demo\target\site\jacoco\jacoco.xml"

def parse_coverage(xml_path):
    tree = ET.parse(xml_path)
    root = tree.getroot()
    
    uncovered = []
    
    for package in root.findall('package'):
        pkg_name = package.get('name')
        for clazz in package.findall('class'):
            class_name = clazz.get('name')
            for method in clazz.findall('method'):
                method_name = method.get('name')
                coverage = int(method.get('line-rate', 0) * 100) if method.get('line-rate') else 0
                if coverage < 100:
                    uncovered.append(f"{pkg_name}.{class_name}.{method_name} ({coverage}% covered)")
    
    return uncovered

if __name__ == "__main__":
    uncovered_items = parse_coverage(JACOCO_XML)
    if uncovered_items:
        print("Uncovered methods/classes:")
        for item in uncovered_items:
            print(" -", item)
    else:
        print("All methods/classes fully covered")