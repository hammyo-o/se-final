# Automated Test Coverage Improvement System

AI-powered system for automatically improving test coverage in Java projects using Gemini 2.5 Pro.

## 🚀 Quick Start

```bash
python quick_start.py
```

This automatically checks for coverage report, generates it if missing, then creates a test.

## ✅ Current Status

- **READY**: All issues fixed, system operational
- **Model**: gemini-2.5-pro
- **Target**: Apache Commons Lang3 (108 source files, 129 test files)
- **JaCoCo**: Configured to generate reports on `mvn verify`

## What Was Fixed

### 1. Compilation Error
- Added missing `import java.io.Writer;` to CharSequenceTranslatorTest.java
- Fixed method visibility

### 2. JaCoCo Configuration
- **Changed**: `<phase>test</phase>` → `<phase>verify</phase>` in pom.xml
- **Result**: Now correctly generates `target/site/jacoco/jacoco.xml`
- **Why**: The "test" phase doesn't create the site directory structure

### 3. Script Enhancement
- `quick_start.py` now auto-generates coverage if missing
- No manual Maven commands needed

## Usage Options

### 1. Quick Test Generation (Recommended ⚡)
```bash
python quick_start.py
```
- Checks for coverage report
- Generates it automatically if missing (runs `mvn verify`)
- Finds uncovered method
- Generates test with Gemini
- Adds to test file

### 2. Generate Coverage Only
```bash
python generate_coverage.py
```
or
```bash
generate_coverage.bat
```
- Runs `mvn clean verify`
- Generates JaCoCo report
- Shows location of jacoco.xml

### 3. Full Automated Workflow
```bash
python run_full_workflow.py
```
or
```bash
run_workflow.bat
```
- Runs Maven clean verify
- Finds uncovered method
- Generates test
- Verifies compilation
- Commits to git

### 4. MCP Server (Interactive)
```bash
cd mcp-agent
python server.py
```
- Exposes tools via MCP protocol
- Runs on localhost:5000
- For integration with other systems

## How It Works

1. **Generates Coverage** (if needed): Runs `mvn clean verify` to create JaCoCo report
2. **Parses JaCoCo Coverage**: Identifies methods with missed instructions
3. **Calls Gemini API**: Generates JUnit 5 test with source context
4. **Adds to Test File**: Appends to existing test class
5. **Verification**: (Optional) Compiles and runs tests

## Project Structure

```
finalproject/
├── quick_start.py              # ⚡ Recommended (auto-generates coverage)
├── generate_coverage.py        # Coverage report generator
├── generate_coverage.bat       # Windows coverage generator
├── run_full_workflow.py        # Complete automation
├── run_workflow.bat            # Windows launcher
├── check_status.py             # System status checker
├── TEST_COVERAGE_WORKFLOW.md   # Detailed documentation
├── README.md                   # This file
├── START_HERE.txt              # Quick start guide
├── codebase/                   # Apache Commons Lang3
│   ├── pom.xml                # Fixed: JaCoCo phase changed to "verify"
│   ├── src/main/java/         # 108 source files
│   ├── src/test/java/         # 129 test files
│   └── target/site/jacoco/    # Coverage reports (generated on verify)
└── mcp-agent/                  # MCP server
    └── server.py
```

## What Was Fixed

### Compilation Error
**File:** `codebase/src/test/java/.../CharSequenceTranslatorTest.java`
- Added missing `import java.io.Writer;`
- Fixed method visibility (added `public`)

### JaCoCo Configuration  
**File:** `codebase/pom.xml`

**Before:**
```xml
<execution>
    <id>report</id>
    <phase>test</phase>  <!-- Wrong: doesn't create site directory -->
    <goals>
        <goal>report</goal>
    </goals>
</execution>
```

**After:**
```xml
<execution>
    <id>report</id>
    <phase>verify</phase>  <!-- Correct: creates target/site/jacoco/ -->
    <goals>
        <goal>report</goal>
    </goals>
</execution>
```

**Why this matters:** The "test" phase in Maven doesn't trigger the site generation. The "verify" phase runs after tests and properly generates the full site structure including `target/site/jacoco/jacoco.xml`.

This matches the working configuration in `testing-agent-demo/pom.xml`.

## Generated Test Example

The system generates JUnit 5 tests like:

```java
@Test
public void testMethodName_scenario_expectedOutcome() {
    // Arrange
    ClassName instance = new ClassName();
    
    // Act
    ResultType result = instance.methodToTest(params);
    
    // Assert
    assertEquals(expectedValue, result);
}
```

## Requirements

- Python 3.7+
- Maven
- Java 21 (configured in project)
- Python packages: `google-generativeai`, `fastmcp`

Install packages:
```bash
pip install google-generativeai fastmcp
```

## Workflow

1. **Run**: `python quick_start.py`
2. **Review**: Check the generated test in your editor
3. **Verify**: `cd codebase && mvn test`
4. **Commit**: `git add . && git commit -m "Add test"`
5. **Repeat**: Run again to improve coverage further

## Configuration

Model is configured in scripts:
- Model: `gemini-2.5-pro`
- API Key: Embedded in scripts
- Timeout: 300 seconds for Maven
- Coverage Tool: JaCoCo 0.8.12

## Next Steps

To start improving test coverage immediately:

```bash
python quick_start.py
```

For detailed documentation, see [TEST_COVERAGE_WORKFLOW.md](TEST_COVERAGE_WORKFLOW.md)

## Notes

- System is fully operational and ready to use
- Each run generates one test for focused review
- Model configuration unchanged (gemini-2.5-pro)
- Coverage report already exists - no build needed initially
- All automation happens through Python scripts

---

**Start now:** `python quick_start.py`
