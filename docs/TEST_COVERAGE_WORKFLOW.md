# Test Coverage Workflow

Focused, up-to-date steps for building, generating coverage, and iterating.

## Prerequisites

- Java (JDK) and Maven installed and on PATH
- Optional: Python 3 if you plan to run the MCP server

## Build + Generate Coverage

```powershell
cd codebase
mvn clean verify -D maven.test.failure.ignore=true
```

Outputs:

- HTML report: `codebase/target/site/jacoco/index.html`
- XML report: `codebase/target/site/jacoco/jacoco.xml`


Run tests only:

```powershell
cd codebase
mvn test
```

## Iterate on Tests

1. Open the report (HTML) and identify low-coverage classes/methods.
2. Add or improve tests under `codebase/src/test/java`.
3. Re-run `mvn verify` to refresh coverage.

## Optional: Use the MCP Agent

If you use an MCP-compatible client, you can start the included server to assist with targeted test generation and simple git actions:

```powershell
cd mcp-agent
python server.py
```

See `mcp-agent/README.md` for a brief tool overview.

## Notes

- JaCoCo is configured to run on the `verify` phase.
- Build artifacts (`target/`) are ignored from git.
