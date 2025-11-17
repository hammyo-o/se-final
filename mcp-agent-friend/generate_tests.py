from fastmcp import FastMCP
from pathlib import Path

mcp = FastMCP(name="JUnit Test Generator")

@mcp.tool
def generate_junit_tests(java_file_path: str) -> str:
    """
    Generate a simple JUnit 5 test scaffold for a Java class.
    Args:
        java_file_path: Full path to a Java source file.
    Returns:
        Path to the generated test file.
    """
    java_file = Path(java_file_path)
    if not java_file.exists():
        return f"File not found: {java_file_path}"

    class_name = java_file.stem
    package = "com.example"  # Update if needed or parse package from source

    test_class_name = f"{class_name}GeneratedTest"
    test_file_path = java_file.parent.parent / "test" / "java" / package.replace(".", "/") / f"{test_class_name}.java"
    test_file_path.parent.mkdir(parents=True, exist_ok=True)

    test_content = f"""package {package};

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class {test_class_name} {{

    @Test
    void testMainMethod() {{
        assertDoesNotThrow(() -> {class_name}.main(new String[]{{}}));
    }}
}}
"""
    test_file_path.write_text(test_content)
    return f"JUnit test scaffold created at: {test_file_path}"
    
if __name__ == "__main__":
    mcp.run(transport="sse", host="127.0.0.1", port=5001)