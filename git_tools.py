import subprocess
import sys


def run_cmd(cmd: list):
    """Helper function to run a shell command and print the output."""
    try:
        result = subprocess.run(cmd, check=True, text=True, capture_output=True)
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
    """
    Adds files to staging. Default = all files in working directory.
    """
    print(f"Staging changes ({path})...")
    run_cmd(["git", "add", path])
    print("Staged files")


def git_commit(message: str):
    """
    Commits staged files with a commit message
    """
    print(f"Committing changes: {message}")
    run_cmd(["git", "commit", "-m", message])
    print("Commit created")


def git_push(remote="origin"):
    """
    Pushes current branch to the remote repository.
    """
    print("Pushing to remote...")
    run_cmd(["git", "push", "--set-upstream", remote, "HEAD"])
    print(f"Pushed to {remote}")


def git_pull_request(base="main", title=None, body=None):
    """
    Creates a pull request using GitHub CLI (`gh`).
    Requires: gh auth login
    """
    if not title:
        raise ValueError("title is required to create a pull request")

    body = body or ""

    print("Creating pull request...")

    run_cmd([
        "gh", "pr", "create",
        "--base", base,
        "--title", title,
        "--body", body,
    ])

    print("Pull request created successfully")