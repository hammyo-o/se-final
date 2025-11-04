from fastmcp import FastMCP
import os

mcp = FastMCP(name="Test Generator Tool")

@mcp.tool
def generate_junit_tests(java_file_path: str) -> str:
    """
    Analyze a Java source file and generate basic JUnit test stubs for each method.
    """
    if not os.path.exists(java_file_path):
        return f"File not found: {java_file_path}"

    tests = []
    with open(java_file_path, "r") as f:
        lines = f.readlines()

    for line in lines:
        if line.strip().startswith("public") and "(" in line and ")" in line:
            method_name = line.split("(")[0].split()[-1]
            test_stub = f"@Test\nvoid test_{method_name}() {{\n    // TODO: implement test\n}}"
            tests.append(test_stub)

    return "\n\n".join(tests)

if __name__ == "__main__":
    mcp.run(transport="sse", host="127.0.0.1", port=5001)
