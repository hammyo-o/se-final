# Repository Status & Fix Summary

Concise snapshot of the current state and key fixes.

## What’s Fixed

- JaCoCo report execution phase updated in `codebase/pom.xml` from `test` to `verify` so `mvn verify` creates `target/site/jacoco/` (including `jacoco.xml`).
- Repository hygiene: removed tracked build artifacts and added comprehensive `.gitignore` to prevent future bloat.
- Build verified via Maven; coverage generation works as expected.

## How to Build and Generate Coverage

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

## Optional: MCP Agent

If you use an MCP-compatible client, start the helper server:

```powershell
cd mcp-agent
python server.py
```

See `mcp-agent/README.md` for a brief overview.
