from fastmcp import FastMCP
import subprocess
import os

mcp = FastMCP(name="Maven + Git Automation Agent")

JAVA_PROJECT_PATH = r"C:\se333\testing-agent-demo"
MCP_AGENT_PATH = r"C:\Users\moust\mcp-agent"


@mcp.tool
def run_tests() -> str:
    """
    Runs Maven tests for the Java project and returns a summary.
    """
    try:
        result = subprocess.run(
            ["mvn", "clean", "test"],
            cwd=JAVA_PROJECT_PATH,
            capture_output=True,
            text=True,
            shell=True
        )

        output = result.stdout + "\n" + result.stderr

        summary = next((line for line in output.splitlines() if "Tests run:" in line), None)

        return summary or "Tests ran — no summary found."
    except Exception as e:
        return f"Error running Maven tests: {str(e)}"


@mcp.tool
def git_status() -> str:
    """
    Returns git status (clean, modified, conflicts, untracked files)
    """
    try:
        result = subprocess.run(
            ["git", "status", "--porcelain=2", "--branch"],
            cwd=MCP_AGENT_PATH,
            capture_output=True,
            text=True,
            shell=True
        )
        return result.stdout or "No git status output"
    except Exception as e:
        return f"Error running git status: {str(e)}"


@mcp.tool
def git_add_all() -> str:
    """
    Stages all changes (ignores build artifacts via .gitignore).
    """
    try:
        subprocess.run(["git", "add", "."], cwd=MCP_AGENT_PATH, shell=True)

        result = subprocess.run(
            ["git", "status", "--porcelain=1"],
            cwd=MCP_AGENT_PATH,
            capture_output=True,
            text=True,
            shell=True
        )

        if result.stdout.strip() == "":
            return "No changes staged (repo clean or only ignored files)"
        else:
            return f"Staged changes:\n{result.stdout}"

    except Exception as e:
        return f"Error staging changes: {str(e)}"
    
@mcp.tool
def git_commit(message: str) -> str:
    """
    Commits staged changes with the provided commit message.
    Example: git_commit("Add Git automation MCP tools")
    """
    try:
        result = subprocess.run(
            ["git", "commit", "-m", message],
            cwd=MCP_AGENT_PATH,
            capture_output=True,
            text=True,
            shell=True
        )

        if "nothing to commit" in result.stdout.lower():
            return "Nothing to commit — no staged changes."

        return f"Commit created:\n{result.stdout}"
    except Exception as e:
        return f"Error committing changes: {str(e)}"


@mcp.tool
def generate_spec_tests(method_name: str = "classifyNumber") -> str:
    """
    Generates a JUnit 5 test file using boundary value analysis and equivalence classes.
    Currently supports the 'classifyNumber(int)' method.
    """
    try:
        test_dir = os.path.join(JAVA_PROJECT_PATH, "src", "test", "java", "com", "example")
        os.makedirs(test_dir, exist_ok=True)
        test_file_path = os.path.join(test_dir, f"{method_name.capitalize()}SpecTest.java")

        test_content = f"""
package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class {method_name.capitalize()}SpecTest {{

    @Test
    public void testPositiveNumber() {{
        assertEquals("Positive", App.{method_name}(1));
    }}

    @Test
    public void testNegativeNumber() {{
        assertEquals("Negative", App.{method_name}(-1));
    }}

    @Test
    public void testZero() {{
        assertEquals("Zero", App.{method_name}(0));
    }}

    @Test
    public void testLargePositiveNumber() {{
        assertEquals("Positive", App.{method_name}(Integer.MAX_VALUE));
    }}

    @Test
    public void testLargeNegativeNumber() {{
        assertEquals("Negative", App.{method_name}(Integer.MIN_VALUE));
    }}
}}
"""

        with open(test_file_path, "w") as f:
            f.write(test_content.strip())

        return f"Specification-based tests generated at: {test_file_path}"

    except Exception as e:
        return f"Error generating specification-based tests: {str(e)}"


if __name__ == "__main__":
    print("MCP Git + Maven Agent running on SSE at localhost:5000")
    mcp.run(transport="sse", host="127.0.0.1", port=5000)