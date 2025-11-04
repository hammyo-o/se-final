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
            return "⚠️ Nothing to commit — no staged changes."

        return f"Commit created:\n{result.stdout}"
    except Exception as e:
        return f"Error committing changes: {str(e)}"


if __name__ == "__main__":
    print("MCP Git + Maven Agent running on SSE at localhost:5000")
    mcp.run(transport="sse", host="127.0.0.1", port=5000)