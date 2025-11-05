import subprocess
import sys
import os

REPO_PATH = r"C:\Users\moust\mcp-agent"

def run_cmd(cmd: list):
    """Runs git command inside the repo directory."""
    try:
        result = subprocess.run(
            cmd,
            cwd=REPO_PATH,      
            check=True,
            text=True,
            capture_output=True
        )
        if result.stdout:
            print(result.stdout.strip())
        if result.stderr:
            print(result.stderr.strip())
        return result

    except subprocess.CalledProcessError as e:
        print("Command failed:", " ".join(cmd))
        print(e.stderr)
        sys.exit(1)


def git_add(path="."):
    """Stage changes."""
    print(f"Staging changes ({path})...")
    run_cmd(["git", "add", path])
    print("Staged files")


def git_commit(message: str):
    """Commit staged files."""
    print(f"Committing changes: {message}")
    run_cmd(["git", "commit", "-m", message])
    print("Commit created")


def git_push(remote="origin"):
    """Push current branch."""
    print("Pushing to remote...")
    run_cmd(["git", "push", "--set-upstream", remote, "HEAD"])
    print(f"Pushed to {remote}")


def git_pull_request(base="main", title=None, body=None):
    """Create PR via GitHub CLI."""
    if not title:
        raise ValueError("title is required")

    print("Creating pull request...")

    run_cmd([
        "gh", "pr", "create",
        "--base", base,
        "--title", title,
        "--body", body or "",
    ])

    print("Pull request created successfully")