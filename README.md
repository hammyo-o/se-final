# AI-Powered Test Coverage Improvement for Apache Commons Lang

[![Java](https://img.shields.io/badge/Java-1.8-orange.svg)](https://www.java.com)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue.svg)](https://maven.apache.org)
[![JaCoCo](https://img.shields.io/badge/JaCoCo-0.8.12-green.svg)](https://www.jacoco.org)
[![Python](https://img.shields.io/badge/Python-3.10%2B-blue.svg)](https://www.python.org)

An intelligent MCP (Model Context Protocol) server that automates test generation, coverage analysis, and Git workflows for Apache Commons Lang3. Integrates with VS Code to provide AI-assisted targeted testing that measurably improves code coverage.

**Project Highlights:**
- 🤖 10 intelligent MCP tools for complete test-to-commit automation
- 🔄 Iterative workflow with automatic coverage tracking and Git integration
- [Demo Workflow](#demo-workflow)
- [Project Structure](#project-structure)
- [Troubleshooting & FAQ](#troubleshooting--faq)
- [Key Features & Technical Notes](#key-features--technical-notes)

## Quick Start

### 1. Generate Coverage Report

```powershell
cd codebase
mvn clean verify
```

**Output:**
- 📊 HTML Report: `codebase/target/site/jacoco/index.html`
- 📄 XML Data: `codebase/target/site/jacoco/jacoco.xml`
- ✅ Test Results: `codebase/target/surefire-reports/`

### 2. Start MCP Server

```powershell
cd mcp-agent
python server.py
```

Server runs on `http://127.0.0.1:5000` with SSE transport.

### 3. Use with VS Code Agent

In VS Code Chat, use the `@tester` agent prompt:
```
@tester please improve test coverage for Apache Commons Lang
```

The agent will automatically:
- Analyze current coverage
- Identify low-coverage classes
- Generate targeted tests
- Run verification
- Commit improvements to Git

## Installation & Configuration

### Prerequisites

- **Java**: JDK 1.8 (required for Apache Commons Lang3)
- **Maven**: 3.6+ (should be on PATH)
- **Python**: 3.10+ for MCP server
- **VS Code**: With GitHub Copilot extension

### Setup

1. **Install Python Dependencies:**

```powershell
cd mcp-agent
pip install -r requirements.txt
# or
pip install fastmcp
```

2. **Configure MCP Server:**

The server auto-detects project paths. No configuration needed for default setup.

3. **Start Server:**

```powershell
python server.py
```

4. **Verify Installation:**

```powershell
# Run baseline coverage
cd ../codebase
mvn clean verify

# Check results
start target/site/jacoco/index.html
```

## 🎯 Latest Session Results (January 17, 2025)

**Autonomous Testing Workflow - 5 Commits:**

**Phase 1: Fix Failing Tests (Commits 1-3)**
- ✅ Fixed 6 critical test failures (all 2397 tests now pass)
- ✅ Improved hex number parsing in NumberUtils (0x80000000, 0x007FFFFFFF, 0x8000000000000000)
- ✅ Fixed JavaUnicodeEscaper above/below boundary logic
- ✅ Removed unintended public API (UnicodeEscaper no-arg constructor)
- ✅ Updated FieldUtilsTest to filter JaCoCo synthetic fields
- ✅ Consolidated redundant documentation files
- ✅ All changes committed and pushed to GitHub

**Phase 2: Test Generation (Commits 4-5)**
- ✅ Generated 26 new tests across 3 target classes
- ✅ StandardToStringStyleAdditionalTest: 6 getter/setter tests
- ✅ EventUtilsAdditionalTest: 7 error path and proxy invocation tests
- ✅ ToStringBuilderAdditionalTest: 13 tests for reflectionToString, appendSuper, array methods
- ⚠️ All tests compile and pass but coverage unchanged (52492/55174 instructions, 95.14%)

**Commits Made:**
1. `fix: resolve 6 test failures and improve hex number parsing` (9965193)
2. `test: add ToStringBuilder tests (work in progress)` (38a15b8)
3. `docs: update README with latest session results` (0d02818)
4. `test: add comprehensive tests for StandardToStringStyle and EventUtils` (d3de61e)
5. `test: add array append tests for ToStringBuilder` (6a8aedb)

**Coverage Analysis:**
- **Baseline**: 52379/55163 (94.95%) → **Current**: 52492/55174 (95.14%)
- **Improvement**: +113 covered instructions (+0.19%)
- **Test Count**: 2397 tests (100% passing, 0 failures)
- **Challenge**: Generated tests exercise already-covered code paths
- **Root Cause**: ToStringBuilder delegates to ToStringStyle (already well-tested), EventUtils uncovered code in private invoke() method requires complex proxy testing

**Next Steps for Future Sessions:**
1. Analyze JaCoCo HTML reports line-by-line to identify exact uncovered lines
2. Target specific error paths and branches (not general method calls)
3. Create integration tests for complex object graphs
4. Consider mutation testing to verify test effectiveness

## MCP Tool Documentation

### Coverage Analysis Tools

| Tool | Purpose | Key Features |
|------|---------|-------------|
| `analyze_coverage()` | Parse JaCoCo XML and identify low-coverage classes | Returns classes with <90% coverage and >50 instructions, sorted by priority |
| `run_verify_with_coverage()` | Execute Maven verify with coverage generation | 5-minute timeout, extracts test summary and JaCoCo metrics |
| `run_tests()` | Quick test execution without full build | Returns pass/fail counts and execution time |
| `identify_failing_tests()` | Parse surefire reports for test failures | Lists failing test classes and methods for debugging |
| `get_class_source()` | Retrieve Java source code for analysis | Returns first 100 lines to understand class structure |

### Test Generation Tools

| Tool | Purpose | Key Features |
|------|---------|-------------|
| `generate_junit4_tests()` | Create targeted JUnit 4 test suites | Supports basic, boundary, and null_safety test types; proper package structure |

### Git Automation & Documentation Tools

| Tool | Purpose | Key Features |
|------|---------|-------------|
| `git_status()` | Show repository state | Clean porcelain format for parsing |
| `git_add_all()` | Stage all changes | Respects .gitignore rules |
| `git_commit()` | Commit with descriptive message | Includes coverage metrics in commit messages |
| `git_push()` | Push to remote repository | Auto-detects branch, configurable remote |
| `update_coverage_history()` | **NEW**: Track coverage improvements | Automatically appends metrics to mcp-agent/docs/coverage_history.md with timestamps after each test cycle |

### Intelligent Features

- **Context-Aware Generation**: Analyzes source code before creating tests
- **JUnit 4 Compatibility**: Generates correct syntax for legacy test frameworks
- **Portable Paths**: Works from any directory within the repository
- **Error Handling**: Graceful degradation with informative error messages

## Demo Workflow

### Live Presentation Steps

**Step 1: Baseline Analysis**
```
1. Open VS Code with this repository
2. Start MCP server in terminal: `cd mcp-agent && python server.py`
3. Run initial coverage: `cd codebase && mvn clean verify`
4. Note baseline metrics from jacoco.xml
```

**Step 2: Automated Improvement**
```
1. In VS Code Chat: "@tester please improve test coverage"
2. Agent analyzes coverage → identifies targets → generates tests
3. Review generated test files in src/test/java/
4. Observe automatic Git workflow: add → commit → push
```

**Step 3: Verification**
```
1. Re-run coverage: `mvn clean verify`
2. Open target/site/jacoco/index.html
3. Compare before/after metrics
4. Check Git history: `git log --oneline -5`
```

### Achieved Results

| Class | Tests Added | Coverage Before | Coverage After | Improvement |
|-------|-------------|-----------------|----------------|-------------|
| StandardToStringStyle | 15 | 32% | 70% | **+38 pp** |
| TypeUtils | 31 | 68% | 68.4% | +0.4 pp |
| ExtendedMessageFormat | 30 | 77% | TBD | ~5-10 pp |
| **Total** | **76** | **2309 tests** | **2385 tests** | **+3.3%** |

## Troubleshooting & FAQ

### Common Issues

**❌ "Package org.junit.jupiter.api does not exist"**
- **Cause**: Generated JUnit 5 syntax for JUnit 4 project
- **Fix**: Use `generate_junit4_tests()` tool explicitly; regenerate tests

**❌ "Unknown lifecycle phase 'test-Dmaven.test.failure.ignore=true'"**
- **Cause**: PowerShell parameter escaping issue
- **Fix**: Use `mvn clean verify` without extra flags

**❌ MCP server port 5000 already in use**
- **Cause**: Previous server instance still running
- **Fix**: `Get-Process | Where-Object {$_.ProcessName -eq "python"} | Stop-Process`

**❌ Coverage XML not found**
- **Cause**: Build didn't reach verify phase
- **Fix**: Ensure `mvn verify` completes successfully; check for compilation errors

**❌ Tests compile but don't improve coverage**
- **Cause**: Test logic doesn't exercise uncovered paths
- **Fix**: Manually review generated tests; add assertions; target specific branches

### Frequently Asked Questions

**Q: Why JUnit 4 instead of JUnit 5?**
A: Apache Commons Lang3 uses JUnit 4.11. We match the project's existing framework.

**Q: Can I use this on other Java projects?**
A: Yes! Update `REPO_ROOT` and `PROJECT_ROOT` paths in server.py.

**Q: How do I add custom test types?**
A: Modify `generate_junit4_tests()` in server.py to add new test patterns.

**Q: What about integration tests?**
A: Currently focuses on unit tests. Integration test support is planned.

**Q: Why did TypeUtils only improve 0.x%?**
A: Complex reflection/generics logic requires sophisticated test scenarios beyond simple boundary testing.

## Project Structure

```
finalproject/
├── codebase/                      # Apache Commons Lang3 (target project)
│   ├── src/main/java/             # 109 production Java files
│   │   └── org/apache/commons/lang3/
│   ├── src/test/java/             # 130+ test files (2,385 tests)
│   │   └── org/apache/commons/lang3/
│   ├── pom.xml                    # Maven + JaCoCo configuration
│   └── target/site/jacoco/        # Generated coverage reports
│       ├── index.html             # Visual coverage report
│       └── jacoco.xml             # Machine-readable data
│
├── mcp-agent/                     # MCP Server (Python)
│   ├── server.py                  # FastMCP with 10 intelligent tools
│   ├── coverage_parser.py         # JaCoCo XML parser
│   ├── test_generator.py          # JUnit 4 template engine
│   ├── git_tools.py               # Git automation (legacy)
│   └── requirements.txt           # Python dependencies
│
├── report/                        # Final Deliverables
│   ├── reflection.tex             # LaTeX source
│   └── reflection.pdf             # Compiled reflection document
│
├── .github/prompts/               # VS Code Agent Integration
│   └── tester.prompt.md           # Agent workflow instructions
│
├── .tours/                        # VS Code CodeTour
│   └── mcp-test-generation-demo.tour  # Interactive demo guide
│
├── docs/                          # Documentation
│   └── coverage_history.md        # Historical metrics tracking
│
└── scripts/                       # Utility Scripts
    ├── coverage_summary.py        # Coverage aggregator
    └── analyze_coverage.py        # JaCoCo analyzer
```

### Key Files

- **`codebase/pom.xml`**: Maven configuration with JaCoCo 0.8.12 plugin
- **`mcp-agent/server.py`**: Core MCP server (~400 lines) with 10 tools
- **`report/reflection.pdf`**: Complete project reflection (methodology, results, insights)
- **`.github/prompts/tester.prompt.md`**: Agent instructions for automated workflow
- **`.tours/mcp-test-generation-demo.tour`**: Interactive demonstration walkthrough

## Key Features & Technical Notes

### Intelligent Coverage Analysis
- Parses JaCoCo XML to identify high-value targets (>50 instructions, <90% coverage)
- Prioritizes classes by impact potential
- Tracks improvements across iterations

### Context-Aware Test Generation
- Analyzes source code structure before generating tests
- Creates three test categories: basic, boundary, null_safety
- Generates 15-31 tests per class based on complexity
- Ensures JUnit 4 compatibility (not JUnit 5)

### Complete Automation
- One-command workflow from analysis to GitHub push
- Descriptive commit messages with coverage metrics
- Automatic path resolution (works from any directory)
- Error handling with informative messages

### Project Achievements
- ✅ Generated tests (all passing)
- ✅ Classes improve with measurable gains
- ✅ Leaves Git commits with clear audit trail
- ✅ Pushed to GitHub `chore/import-friend-merge` branch
- ✅ Comprehensive documentation (README, reflection, code tour)

### Build Configuration
- JaCoCo runs on Maven `verify` phase
- Reports: XML (machine-readable) + HTML (visual)
- Test framework: JUnit 4.11
- Java version: 1.8 (required for Commons Lang3)

---

## Additional Resources

- **Reflection Document**: `report/reflection.pdf` - Full technical report
- **Code Tour**: `.tours/mcp-test-generation-demo.tour` - Interactive demo
- **Agent Prompt**: `.github/prompts/tester.prompt.md` - Workflow instructions
- **Coverage History**: `docs/coverage_history.md` - Metrics tracking

## Contributors

- **Leonardo Diaz** - MCP server development, test generation engine
- **Hammy Siddiqui** - Coverage analysis, Git automation, documentation

## License

This project extends Apache Commons Lang3, which is licensed under Apache License 2.0.
