# coverage_tool.py
import xml.etree.ElementTree as ET
from fastmcp import FastMCP

mcp = FastMCP(name="Coverage Analyzer Tool")

@mcp.tool
def analyze_coverage(jacoco_xml_path: str):
    """
    Simple tool to parse Jacoco XML report and list uncovered classes and methods.
    """
    tree = ET.parse(jacoco_xml_path)
    root = tree.getroot()
    uncovered = []
    for package in root.findall('package'):
        package_name = package.get('name')
        for clazz in package.findall('class'):
            class_name = clazz.get('name')
            for method in clazz.findall('method'):
                method_name = method.get('name')
                coverage = int(method.get('ci')) 
                if coverage > 0:
                    uncovered.append(f"{package_name}.{class_name}.{method_name}")

    return {"uncovered_methods": uncovered, "total_uncovered": len(uncovered)}

if __name__ == "__main__":
    mcp.run(transport="sse", host="127.0.0.1", port=5001)