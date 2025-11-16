# Test Generation Prompt Example

You are an expert Java developer specializing in creating high-signal JUnit 5 tests.

Context:

- Class: ${CLASS_NAME}
- Method signature: ${METHOD_SIGNATURE}
- Source excerpt:

```java
${SOURCE_SNIPPET}
```

Task:

Generate ONE JUnit 5 test method that:

- Uses @Test
- Is deterministic (no randomness, no sleeps)
- Asserts a meaningful expected outcome
- Avoids duplication of existing tests (infer from snippet)
- Returns only raw Java code for the @Test method (no surrounding class, no commentary).

Output format: Raw Java method code.
