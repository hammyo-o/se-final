# Final Demo Script (5 Minutes)

## Logging Format

Each step logged to `demo/demo_log.md` as a Markdown table row:

| Timestamp (UTC) | Step | Command / Tool | Outcome | Coverage (Instr %) | Notes |
|-----------------|------|----------------|---------|--------------------|-------|

Automation appends rows; manual narration references them.

## Segment 1: Project Overview (~45s)

1. Goal: AI-assisted targeted test generation with MCP extensions.
2. Stack: Java + Maven + JaCoCo; Python (FastMCP); Google Gemini model.
3. Extensions: `find_first_uncovered_method`, `generate_targeted_junit_test`, plus new `coverage_summary`.

## Segment 2: Live Workflow (~2m)

1. Show current coverage summary (tool: `coverage_summary`).
2. List first uncovered method (tool: `find_first_uncovered_method`).
3. Generate test (tool: `generate_targeted_junit_test`).
4. Git lifecycle: `git_status` → `git_add_all` → `git_commit` → `git_push`.
5. Rebuild & regenerate coverage (tool: `run_maven_coverage_report`).
6. New coverage summary for delta (tool: `coverage_summary`).

## Segment 3: Coverage Improvement Showcase (~1m)

1. Display before/after instruction & branch %.
2. Show diff of test file with new method added.
3. Discuss targeting efficiency vs. manual scanning.

## Segment 4: Reflection (~1m 15s)

- Challenge: Parsing large XML quickly & safe insertion into existing test classes.
- AI Help: Rapid test skeleton; human ensures semantic assertions.
- Trade-offs: Single-method focus vs. batch generation (chosen for review clarity).
- Future: Mutation score integration (PIT), batching uncovered methods, flaky test detection.

## Segment 5: Closing (~30s)

- Recap measurable delta.
- Point to README Tool Docs & `report/reflection.tex`.
- Invite Q&A or async follow-up (discussion board).

## Demo Commands (PowerShell Examples)

```powershell
# 1. Start MCP server (if not already)
cd mcp-agent
python server.py

# 2. Coverage summary (via MCP client)
# coverage_summary

# 3. First uncovered
# find_first_uncovered_method

# 4. Generate test (example placeholders)
# generate_targeted_junit_test "c:/path/File.java" "ClassName" "methodName(args)"

# 5. Git ops
# git_status; git_add_all; git_commit "test: add focused JUnit"; git_push

# 6. Re-run build
cd ..\codebase
mvn clean verify -D maven.test.failure.ignore=true

# 7. Coverage summary again
# coverage_summary
```

## Logging Helper (Optional)

A script can append entries:

```markdown
| 2025-11-16T20:02:11Z | generate_test | generate_targeted_junit_test | success | 72.31 -> 72.89 | Added test for Range.isAfter |
```

## Key Metrics To Capture

- Instruction coverage % before/after
- Branch coverage % before/after
- Test count delta
- Time from uncovered detection to committed test (<30s target)

## Quality Checklist

- No build artifacts committed
- Test compiles & passes
- Commit message semantic prefix
- Coverage delta non-zero

## FAQ Talking Points

- "Why only one test per run?" → Focus & review quality
- "Can this batch generate?" → Yes, planned extension
- "How avoid brittle tests?" → Deterministic prompt constraints
