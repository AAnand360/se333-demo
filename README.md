# SE333 Final Project — AI-Powered Software Testing Agent

**Student:** Avi Anand  
**Course:** SE333  
**Due Date:** March 11, 2026  

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Repository Structure](#repository-structure)
3. [MCP Tool / API Documentation](#mcp-tool--api-documentation)
4. [Installation & Configuration Guide](#installation--configuration-guide)
5. [Agent Prompt Files](#agent-prompt-files)
6. [Results](#results)
7. [Troubleshooting & FAQ](#troubleshooting--faq)
8. [Reflection Report](#reflection-report)

---

## Project Overview

This project implements an AI-powered software testing agent using the **Model Context Protocol (MCP)**. The agent autonomously generates JUnit 5 tests, executes them via Maven, parses JaCoCo XML coverage reports, and iterates until maximum coverage is achieved — all driven by a single prompt file.

The project consists of two repositories:

| Repository | Purpose |
|---|---|
| `se333-mcp` | MCP server exposing custom tools to the agent |
| `se333-demo` | Demo Java project where the agent generates and runs tests |

The agent was also applied to the real-world **Spring PetClinic** application as Phase 6.

---

## Repository Structure

```
se333-mcp/                          # MCP Server
├── main.py                         # FastMCP server with 3 tools
├── pyproject.toml
└── .venv/

se333-demo/                         # Java Demo Project
├── src/
│   ├── main/java/com/se333/
│   │   ├── Calculator.java         # Source class with intentional bug
│   │   └── StringUtils.java        # Source class
│   └── test/java/com/se333/
│       ├── CalculatorTest.java     # Agent-generated tests (25 tests)
│       └── StringUtilsTest.java    # Agent-generated tests (31 tests)
├── .github/
│   └── prompts/
│       ├── tester.prompt.md        # Agent testing prompt
│       └── reviewer.prompt.md      # Agent code review prompt
├── pom.xml
├── CODE_REVIEW.md                  # AI-generated code review report
└── .vscode/
    └── mcp.json                    # MCP server connection config
```

---

## MCP Tool / API Documentation

The MCP server is built with **FastMCP** and exposes three tools over HTTP/SSE at `http://127.0.0.1:8000/sse`.

---

### Tool 1: `add`

A simple arithmetic tool used to verify MCP server connectivity.

| Parameter | Type | Description |
|---|---|---|
| `a` | `int` | First number |
| `b` | `int` | Second number |

**Returns:** `int` — the sum of a and b

**Example:**
```
Input:  a=1, b=2
Output: 3
```

---

### Tool 2: `run_maven_tests`

Runs `mvn test jacoco:report` on a Java Maven project and returns the results.

| Parameter | Type | Description |
|---|---|---|
| `project_path` | `str` | Absolute path to the Maven project root |

**Returns:** JSON object with:
- `success` (bool) — whether the build passed
- `stdout` (str) — full Maven output
- `stderr` (str) — error output if any

**Example:**
```
Input:  project_path="C:\Users\Avian\se333-demo"
Output: { "success": true, "stdout": "[INFO] BUILD SUCCESS...", "stderr": "" }
```

---

### Tool 3: `parse_jacoco`

Parses a JaCoCo XML coverage report and returns a structured coverage summary.

| Parameter | Type | Description |
|---|---|---|
| `xml_path` | `str` | Absolute path to `jacoco.xml` |

**Returns:** JSON object with:
- `overall` — coverage percentages for LINE, BRANCH, METHOD, INSTRUCTION, CLASS
- `classes` — per-class breakdown with uncovered methods listed

**Example:**
```
Input:  xml_path="C:\Users\Avian\se333-demo\target\site\jacoco\jacoco.xml"
Output: {
  "overall": {
    "LINE": "100%",
    "BRANCH": "100%",
    "METHOD": "100%"
  },
  "classes": [
    {
      "name": "Calculator",
      "uncovered_methods": []
    }
  ]
}
```

---

## Installation & Configuration Guide

### Prerequisites

| Tool | Version | Notes |
|---|---|---|
| Windows 11 | — | WSL2 required for Jenkins |
| Java (Corretto) | 21 | Must be in PATH |
| Apache Maven | 3.9.x | Must be in PATH |
| Python | 3.14 | Via uv |
| uv | latest | `C:\Users\Avian\.local\bin` |
| VS Code | 1.109+ | With GitHub Copilot |
| Git | latest | — |

---

### Step 1: Clone the MCP Server

```powershell
git clone https://github.com/AAnand360/se333-mcp.git
cd se333-mcp
```

### Step 2: Set Up Python Virtual Environment

```powershell
$env:Path = "C:\Users\Avian\.local\bin;$env:Path"
uv venv
.venv\Scripts\activate
uv pip install fastmcp
```

### Step 3: Start the MCP Server

```powershell
python main.py
```

Expected output:
```
INFO:     Uvicorn running on http://127.0.0.1:8000 (Press CTRL+C to quit)
```

### Step 4: Clone the Demo Project

```powershell
git clone https://github.com/AAnand360/se333-demo.git
cd se333-demo
```

### Step 5: Configure VS Code MCP Connection

Create `.vscode\mcp.json` in the project root:

```json
{
  "servers": {
    "se333-mcp-server": {
      "url": "http://127.0.0.1:8000/sse",
      "type": "http"
    },
    "github": {
      "url": "https://api.githubcopilot.com/mcp/",
      "type": "http"
    }
  }
}
```

### Step 6: Verify Maven Build

```powershell
mvn clean test
```

Expected: `BUILD SUCCESS` with `Tests run: 0`

### Step 7: Run the Agent

1. Open VS Code in the `se333-demo` folder
2. Open Agent chat (`CTRL+SHIFT+P` → "Chat: Focus on Chat View")
3. Set mode to **Agent**
4. Verify `se333-mcp-server` tools are checked
5. Type in chat:

```
#tester.prompt.md
```

The agent will automatically write tests, run Maven, check coverage, and iterate.

---

## Agent Prompt Files

### tester.prompt.md

Located at `.github/prompts/tester.prompt.md`. Defines the iterative testing loop:

1. Read source files
2. Write JUnit 5 tests
3. Run `run_maven_tests` tool
4. Run `parse_jacoco` tool
5. Identify coverage gaps
6. Write additional tests
7. Iterate until 100% LINE coverage
8. Fix any bugs found
9. Commit via GitHub MCP tools (trunk-based workflow)

### reviewer.prompt.md

Located at `.github/prompts/reviewer.prompt.md`. Defines the code review workflow:

1. Read all Java source files
2. Analyze code quality, bugs, best practices
3. Generate `CODE_REVIEW.md` report with scores
4. Commit report via GitHub MCP tools

---

## Results

### se333-demo (Demo Project)

| Metric | Result |
|---|---|
| Total Tests | 56 (25 Calculator + 31 StringUtils) |
| LINE Coverage | 100% |
| BRANCH Coverage | 100% |
| METHOD Coverage | 100% |
| CLASS Coverage | 100% |
| Iterations Required | 1 |
| Bugs Found | 1 (`divide()` returning -1 instead of throwing `ArithmeticException`) |

**Coverage Iteration Log:**
```
Iteration 1: LINE 100% | BRANCH 100% | METHOD 100%
```

**Bug Fixed:**
```java
// BEFORE (bug)
if (b == 0) return -1;

// AFTER (fix)
if (b == 0) throw new ArithmeticException("Division by zero");
```

---

### spring-petclinic (Phase 6 — Real World Application)

| Metric | Before | After | Improvement |
|---|---|---|---|
| LINE Coverage | 94.26% | 97.30% | +3.04% |
| METHOD Coverage | 89.81% | 92.59% | +2.78% |
| INSTRUCTION Coverage | 90.49% | 93.69% | +3.20% |
| COMPLEXITY Coverage | 83.55% | 85.53% | +1.98% |
| CLASS Coverage | 95.45% | 100.00% | +4.55% |
| Total Tests | 57 | 65 | +8 new tests |

**New Test Files Created:**
- `WelcomeControllerTests.java` — 3 test cases
- `PetClinicRuntimeHintsTests.java` — 5 test cases

---

### Code Review Results (Phase 5)

The AI code reviewer scored the codebase **7.5/10** and identified:

- **Critical:** Recursive `factorial()` risks `StackOverflowError` for large inputs
- **Critical:** Integer overflow not handled in `multiply()`
- **Minor:** Unicode handling in `isPalindrome()`
- **Minor:** Missing JavaDoc documentation

---

## Troubleshooting & FAQ

**Q: MCP server shows "Running" but tools don't appear in Agent chat**  
A: Close and reopen VS Code. Make sure you are in Agent mode not Ask mode. Click the tools icon and manually check `se333-mcp-server` tools.

**Q: `mvn clean test` fails with "No POM in this directory"**  
A: Make sure you are in the project root folder containing `pom.xml`. Run `dir` to verify.

**Q: Agent says "I can't access your files"**  
A: The agent needs VS Code open in the correct project folder. Check the title bar — it must show `se333-demo` not `se333-mcp`. Also make sure `.vscode\mcp.json` exists in that folder.

**Q: MCP server fails to start**  
A: Make sure the virtual environment is activated:
```powershell
cd C:\Users\Avian\se333-mcp
$env:Path = "C:\Users\Avian\.local\bin;$env:Path"
.venv\Scripts\activate
python main.py
```

**Q: `parse_jacoco` returns an error**  
A: JaCoCo only generates `jacoco.xml` after tests have been run. Make sure `mvn test` ran successfully first and the file exists at `target/site/jacoco/jacoco.xml`.

**Q: Agent creates PR to wrong repository**  
A: Specify your GitHub username and repo name explicitly in the prompt. The agent sometimes defaults to the upstream fork origin.

**Q: spring-petclinic build fails with checkstyle error on mcp.json**  
A: Add `.vscode/` to `.gitignore` so the checkstyle plugin ignores it:
```powershell
echo ".vscode/" >> .gitignore
```

---

## Reflection Report

### Introduction

This project explored AI-assisted software testing using the Model Context Protocol (MCP). The core idea was to build a custom MCP server that exposes testing tools — Maven execution and JaCoCo parsing — and wire those tools into a VS Code agent that autonomously generates, runs, and iterates on tests. The goal was to evaluate whether an AI agent could meaningfully improve code coverage on both a simple demo project and a real-world Spring Boot application.

### Methodology

The project was structured in six phases. Phase 1 involved building the MCP server using FastMCP in Python, exposing three tools: `add` (for connectivity testing), `run_maven_tests` (to execute Maven builds), and `parse_jacoco` (to parse JaCoCo XML coverage reports). Phase 2 established the client workspace in VS Code with a dedicated prompt file that defined the agent's behavior. Phase 3 implemented the core iterative testing loop, where the agent read source files, generated tests, executed them, parsed coverage, and repeated until coverage goals were met. Phase 4 integrated GitHub MCP tools to enforce a trunk-based Git workflow, ensuring all agent-generated changes were tracked through feature branches and pull requests. Phase 5 added an AI code review capability via a separate prompt file. Phase 6 applied the entire pipeline to the Spring PetClinic application.

The agent prompt was designed as a step-by-step instruction set with explicit tool calls, project paths, and iteration criteria. The separation between the MCP server project and the client project mirrored real-world usage where shared tools serve multiple repositories.

### Results & Discussion

**Coverage Improvement Patterns**

On the demo project, the agent achieved 100% coverage in a single iteration. This was possible because the codebase was small and well-structured. On the Spring PetClinic application, improvement was more gradual and targeted — the agent focused on previously untested controller and runtime hints classes, achieving meaningful gains (+3-4%) without disrupting the existing test suite.

A key observation was that coverage improvement is non-linear. The first iteration typically yields the largest gains by covering obvious untested paths. Subsequent iterations face diminishing returns as remaining uncovered code tends to involve complex integration paths or external dependencies that are difficult to unit test in isolation.

**Insights from AI-Assisted Development**

The most valuable insight was that AI agents excel at repetitive, structured tasks like test generation but require precise instructions to work reliably. Vague prompts led to the agent asking for clarification instead of acting. Specific prompts with explicit file paths, tool names, and success criteria produced consistent results.

The agent was particularly effective at identifying edge cases that human developers might overlook — null inputs, empty strings, negative numbers, and boundary values were all covered without explicit instruction. However, the agent required human oversight for decisions about code architecture and bug fixes to ensure correctness.

The MCP architecture proved to be a clean separation of concerns. The server handled all Java-specific tooling while the agent focused on reasoning and test generation. This made the system composable and reusable across projects.

**Challenges**

The most significant technical challenge was workspace access. The VS Code agent could only see files in the currently open workspace, and the MCP server configuration needed to exist in each project separately. This caused repeated failures until the `.vscode/mcp.json` file was properly configured in each workspace.

Networking was another challenge when using Docker Dev Containers — the MCP server running on Windows was not reachable from inside the container due to hostname resolution differences. This was resolved by switching to a local Windows environment for Phase 6.

**Recommendations for Future Enhancements**

1. **Persistent MCP configuration** — A global MCP config that applies across all workspaces would eliminate the repeated setup problem.
2. **CI integration** — Integrating the agent into a GitHub Actions pipeline would allow automatic test generation on every pull request.
3. **Smarter iteration strategy** — Instead of iterating until 100% coverage, the agent could use branch coverage data to prioritize the highest-impact untested paths first.
4. **Multi-file awareness** — The agent should be given tools to list and read workspace files automatically rather than relying on hardcoded paths in the prompt.

### Conclusion

This project demonstrated that AI agents can meaningfully assist in software testing workflows when given well-defined tools and precise instructions. The MCP architecture provided a clean, extensible foundation for integrating AI capabilities into existing development workflows. The combination of automated test generation, coverage feedback loops, and Git automation represents a practical approach to AI-assisted quality assurance that could scale to real-world software projects.