# Lab 5 — Robot Framework Report

**Project:** Demo Web Shop  
**Application URL:** http://localhost:3001  
**Date:** 2026-07-07  
**Tool:** Robot Framework 7.1.1 + SeleniumLibrary 6.7.1  

---

## 1. Tested Application

**Demo Web Shop** — a single-page e-commerce application built with Node.js (json-server 0.17.4)
and vanilla JavaScript. It is accessible at `http://localhost:3001` and uses hash-based routing
(`/#/login`, `/#/search`, etc.).

Key features under test: user authentication (login / error handling).

---

## 2. Automated Test Cases

### TC-001 — Valid Login (Positive)

| Field | Value |
|---|---|
| **Preconditions** | App running at `http://localhost:3001`; `demo@webshop.com` / `demo123` account exists |
| **Steps** | Navigate to `/#/login` → enter valid credentials → submit form |
| **Expected result** | Email address appears as a link in the page header |
| **Assertion** | `css:.header-actions a[href='#/account']` contains `demo@webshop.com` |
| **Keywords used** | `Open Demo Web Shop` → `Navigate To Login Page` → `Fill Login Form` → `Verify User Is Logged In` |

### TC-004 — Invalid Login (Negative)

| Field | Value |
|---|---|
| **Preconditions** | App running at `http://localhost:3001`; `demo@webshop.com` account exists |
| **Steps** | Navigate to `/#/login` → enter valid email + wrong password → submit form |
| **Expected result** | Error message "Login was unsuccessful. Please correct the errors and try again." is displayed |
| **Assertion** | `css:.alert-error` is visible and contains the expected message |
| **Keywords used** | `Open Demo Web Shop` → `Navigate To Login Page` → `Fill Login Form` → `Verify Login Error Message` |

---

## 3. Keywords Created

All custom keywords are defined in `automation/robot/resources/common_keywords.robot`.

| Keyword | Arguments | Description |
|---|---|---|
| `Open Demo Web Shop` | `${url}`, `${browser}` | Opens browser, navigates to app, maximises window |
| `Navigate To Login Page` | `${base_url}` | Goes to `/#/login`, waits for email field to be ready |
| `Fill Login Form` | `${email}`, `${password}` | Types credentials and submits the form |
| `Verify User Is Logged In` | `${expected_email}` | Asserts email link visible in page header |
| `Verify Login Error Message` | `${expected_message}` | Asserts error banner is displayed |
| `Close Demo Web Shop` | — | Closes the browser window |

---

## 4. Test Execution

### Run command

```cmd
cd automation\robot
python -m pip install -r requirements.txt
python -m robot --outputdir results tests/demo_shop_tests.robot
```

### Expected output

```
==============================================================================
Demo Shop Tests
==============================================================================
TC-001 Valid Login Shows User Email In Header                         | PASS |
TC-004 Invalid Login Shows Error Message                              | PASS |
==============================================================================
Demo Shop Tests                                                       | 2 PASS, 0 FAIL |
==============================================================================
Output:  results/output.xml
Log:     results/log.html
Report:  results/report.html
Elapsed time: 00:00:08.xxx
```

> **Evidence:**
> - `reports/lab5-robot-screenshot.png` — Chrome opened by Robot Framework showing the Demo Web Shop home page (auto-captured during test execution)
> - `reports/lab5-robot-report.png` — screenshot of `results/report.html` showing 2 PASS *(open `automation\robot\results\report.html` in browser and screenshot)*

---

## 5. Selenium vs Robot Framework Comparison

Robot Framework's keyword-driven approach makes test cases immediately readable by anyone on the team — each line reads as plain English (`Fill Login Form    demo@webshop.com    demo123`), whereas Selenium/JUnit requires Java code with explicit driver calls, waits, and assertion methods that only developers can easily parse. Robot Framework promotes re-use through keyword resource files (`common_keywords.robot`), while Selenium achieves the same goal with helper methods or the Page Object Model — more powerful but significantly more verbose to set up. Variables in Robot Framework (`${URL}`, `${BROWSER}`) have a dedicated section and can be trivially overridden from the command line (`--variable BROWSER:firefox`), making environment switching simpler than editing Java constants. The HTML report and execution log generated automatically by Robot Framework provide rich, formatted evidence with screenshots on failure, whereas Maven Surefire produces plain text that requires additional plugins for the same quality. For business-facing acceptance tests or teams with mixed technical backgrounds, Robot Framework lowers the barrier to writing and reviewing tests; for scenarios requiring fine-grained control such as JavaScript execution, complex waits, or custom retry logic, Selenium WebDriver code gives more flexibility and debugging capability.

---

## 6. Problems and Notes

| Problem | Solution |
|---|---|
| Hash routing: `Go To` with base URL renders the home page, not the login page | Always pass the full URL including the hash fragment: `${base_url}/#/login` |
| `css:` prefix required by SeleniumLibrary | CSS selectors must be written as `css:.alert-error` (vs raw `By.cssSelector()` in Java) |
| Async page content | `Wait Until Element Is Visible` with `timeout=10s` prevents flaky tests on slow responses |
| Each test opens its own browser | Fresh browser instance per test case guarantees no shared `localStorage` state between TC-001 and TC-004 |
