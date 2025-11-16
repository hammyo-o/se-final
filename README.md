# se-final — Test Coverage Toolkit

Tools and project setup for analyzing JaCoCo coverage and improving tests for a Java/Maven codebase (Apache Commons Lang3).

## Quick Start

Generate a clean build and coverage report:

```powershell
cd codebase
mvn clean verify -D maven.test.failure.ignore=true
```

- Coverage HTML: `codebase/target/site/jacoco/index.html`
- Coverage XML: `codebase/target/site/jacoco/jacoco.xml`

Run tests only:

```powershell
cd codebase
mvn test
```

## Optional: MCP Agent

An MCP server is included to assist with targeted test generation and basic git automation. Start it if you use an MCP-compatible client:

```powershell
cd mcp-agent
python server.py
```

See `mcp-agent/README.md` for a brief description of available tools.

## Project Layout

- `codebase/`: Maven project (Apache Commons Lang3) with JaCoCo configured to run on `verify`.
- `mcp-agent/`: Minimal MCP server exposing tools to generate coverage and targeted tests.
- `testing-agent-demo/`: Small demo Maven project used for reference.
- `TEST_COVERAGE_WORKFLOW.md`: Focused how-to for coverage and workflow.

## Notes

- Build artifacts (`target/`) and node assets are ignored via `.gitignore`.
- JaCoCo execution phase was updated to `verify` so `mvn verify` creates `target/site/jacoco/`.

For detailed steps, see `TEST_COVERAGE_WORKFLOW.md`.
