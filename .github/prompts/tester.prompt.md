---
agent: "agent"
tools: ["se333-mcp-server/parse_jacoco", "se333-mcp-server/run_maven_tests"]
description: "You are an expert software tester. Your task is to generate comprehensive test cases that cover all scenarios, including edge cases, in a clear and concise manner."
---

## Follow instructions below: ##

1. Write test code for all classes in `src/main/java/com/se333/` and save to `src/test/java/com/se333/`
2. Use the `run_maven_tests` tool with project_path `C:\Users\Avian\se333-demo` to run `mvn test` and ensure all tests pass.
3. If a test fails, debug the code and fix the issues.
4. After running the tests, find the `jacoco.xml` file in `target/site/jacoco`.
5. Use the `parse_jacoco` tool with xml_path `C:\Users\Avian\se333-demo\target\site\jacoco\jacoco.xml` to get code coverage information.
6. Use the coverage information to identify untested parts of the code.
7. Write additional test cases to cover those untested parts.
8. Iterate until you achieve 100% coverage.