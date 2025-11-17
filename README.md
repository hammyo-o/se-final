# se-final — Test Coverage Toolkit

AI-assisted targeted test generation and coverage tracking for a Java/Maven project (Apache Commons Lang3) using JaCoCo + MCP extensions.

**Merged Repository**: This combines two team member contributions — automated test generation workflows and MCP server tooling for Maven + Git automation.

## Table of Contents

- Quick Start
- Installation & Configuration
- Project Structure
- MCP Tool Documentation
- Demo Workflow & Logging Format
- Troubleshooting & FAQ
- Notes

## Quick Start

Build + coverage:

```powershell
cd codebase
mvn clean verify -D maven.test.failure.ignore=true
```

Artifacts:

- HTML: `codebase/target/site/jacoco/index.html`
- XML: `codebase/target/site/jacoco/jacoco.xml`

Run tests only:

```powershell
cd codebase
mvn test
```

## Installation & Configuration

Prerequisites:

- Java (JDK 21) & Maven on PATH
- Python 3.10+
- `pip install fastmcp google-generativeai`

Start MCP server:

```powershell
cd mcp-agent
python server.py
```

Optional coverage logging:

```powershell
python scripts/coverage_summary.py
```

## MCP Tool Documentation

| Tool | Purpose | Inputs | Output |
|------|---------|--------|--------|
| `run_maven_coverage_report` | Build & generate JaCoCo report | none | Status string / errors |
| `coverage_summary` | Aggregate instruction & branch coverage | none | Multi-line summary |
| `find_first_uncovered_method` | Locate next uncovered method | none | `path;Class;method(sig)` or message |
| `generate_targeted_junit_test` | Append focused JUnit test | source_file_path, class_name, method_to_test | Success or error message |
| `project` | Snapshot of codebase & first uncovered | optional path | Multi-line summary |
| `git_status` | Show repo state | none | Porcelain status |
| `git_add_all` | Stage changes | none | Confirmation |
| `git_commit` | Commit staged changes | message | Commit log / warning |
| `git_push` | Push commits | remote, branch | Push output |
| `git_pull` | Pull latest | remote, branch | Pull output |

### Extension Tools (Phase 5)

- `find_first_uncovered_method`: Eliminates manual XML scanning.
- `generate_targeted_junit_test`: Produces one deterministic test method for targeted improvement.
- New Feature: `coverage_summary` for measurable before/after deltas.

### Prompt (Example)

See `.github/prompts/test_generation_prompt.md` for test generation prompt template.

## Demo Workflow & Logging Format

Demo steps (recorded/live):

1. `coverage_summary` (baseline)
2. `find_first_uncovered_method`
3. `generate_targeted_junit_test`
4. Git: status → add → commit → push
5. `run_maven_coverage_report`
6. `coverage_summary` (delta)

Log row format (append to `demo/demo_log.md`):

| Timestamp (UTC) | Step | Tool | Outcome | Instr % Before→After | Notes |
|-----------------|------|------|---------|----------------------|-------|

## Troubleshooting & FAQ

| Issue | Cause | Fix |
|-------|-------|-----|
| Coverage XML missing | Build not run on `verify` | Run `mvn clean verify` |
| Empty test generation | LLM response blank | Re-run tool; verify prompt tokens |
| Test file not found | Missing matching `*Test.java` | Create baseline test class manually |
| Nothing to commit | No staged changes | Run `git_add_all` first |
| API errors | Invalid key / network | Reconfigure environment variable / retry |

**Why one test per run?** Focus, easy review, avoids noisy commits.

**Can I batch?** Planned future enhancement.

**Prevent brittle tests?** Prompt enforces determinism; avoid randomness/time.

## Project Structure

``` components:

codebase/ - Apache Commons Lang3 (our target for improvement)
mcp-agent/ - MCP server with AI tools
testing-agent-demo/ - Smaller demo project
scripts/ - Coverage utilities
mcp-agent/docs/ - Team reflection document
The MCP agent automates the entire test generation workf
finalproject/
├── codebase/               # Apache Commons Lang3 Java project (target for test gen)
│   ├── src/main/java/      # Production code
│   ├── src/test/java/      # JUnit tests
│   ├── pom.xml             # Maven config with JaCoCo
│   └── target/             # Build artifacts (jacoco.xml, reports)
│
├── mcp-agent/              # MCP server for test automation
│   ├── server.py           # FastMCP server with tools
│   ├── coverage_parser.py  # JaCoCo XML parser
│   ├── git_tools.py        # Git helpers
│   ├── pyproject.toml      # Python dependencies
│   └── docs/               # Reflection docs (PDF + LaTeX)
│
├── testing-agent-demo/     # Smaller demo Java project
│   ├── src/main/java/      # App.java
│   └── src/test/java/      # Generated tests
│
├── scripts/                # Utility scripts
│   ├── coverage_summary.py
│   └── coverage_summary_for.py
│
├── docs/                   # Coverage history log
│   └── coverage_history.md
│
└── demo/                   # Demo documentation
    └── final_demo_script.md
```

**Key Folders**:
- `codebase/`: Main Java project for coverage improvement (Apache Commons Lang3)
- `mcp-agent/`: AI agent server that automates test generation and git operations
- `mcp-agent/docs/`: Contains the team reflection document (`.tex` and `.pdf`)
- `docs/coverage_history.md`: Historical coverage tracking
- `docs/` Coverage history log.
- `demo/` Demo script & logs.
- `report/` LaTeX reflection stub.
- `.github/prompts/` Prompt templates.

## Notes

- Build artifacts ignored via `.gitignore`.
- JaCoCo phase set to `verify` for full report generation.
- Coverage history appended by `scripts/coverage_summary.py`.

For deeper workflow details see `TEST_COVERAGE_WORKFLOW.md` and `mcp-agent/README.md`.
