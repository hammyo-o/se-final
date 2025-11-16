# Fixes Applied (Concise)

- Updated JaCoCo execution phase in `codebase/pom.xml` from `test` to `verify`, ensuring `mvn verify` produces `target/site/jacoco/jacoco.xml`.
- Verified coverage generation with `mvn clean verify`.
- Cleaned repository: removed committed build artifacts and added `.gitignore` to prevent reintroduction.

Quick check:

```powershell
cd codebase
mvn clean verify -D maven.test.failure.ignore=true
```

Coverage locations:

- HTML: `codebase/target/site/jacoco/index.html`
- XML: `codebase/target/site/jacoco/jacoco.xml`
