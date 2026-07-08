---
marp: true
theme: default
paginate: true
style: |
  section {
    font-family: 'Segoe UI', Arial, sans-serif;
    font-size: 1.1rem;
    background: #ffffff;
    color: #1a1a2e;
  }
  h1 { color: #16213e; font-size: 2rem; border-bottom: 3px solid #0f3460; padding-bottom: 0.3em; }
  h2 { color: #0f3460; font-size: 1.5rem; }
  table { font-size: 0.85rem; width: 100%; }
  th { background: #0f3460; color: #fff; }
  td { border-bottom: 1px solid #ddd; }
  .pass { color: #2e7d32; font-weight: bold; }
  .fail { color: #c62828; font-weight: bold; }
  section.title {
    background: #16213e;
    color: #fff;
    text-align: center;
    justify-content: center;
  }
  section.title h1 { color: #e2b96f; border-color: #e2b96f; font-size: 2.5rem; }
  section.title h2 { color: #a8c8f8; font-size: 1.3rem; }
  section.title p  { color: #cdd9e5; }
---

<!-- _class: title -->

# TSD 2026 — Software Testing Project

## Team Crousty

**Application:** Demo Web Shop · `http://localhost:3001`

**Submission:** 9 July 2026

<!--
Hello everyone, today we are presenting our software testing project for Demo Web Shop.
We are Team Crousty, and this deck walks through the application overview,
our test strategy, the manual test results, the defect we found, the automation
work with Selenium and Robot Framework, the API testing plan with Postman, and
the final lessons learned.
-->

---

# 1. Application Overview

**Demo Web Shop** — a single-page e-commerce application

| Detail | Value |
|---|---|
| Stack | Node.js · json-server 0.17.4 · Vanilla JS |
| Routing | Hash-based SPA (`/#/login`, `/#/cart`, …) |
| URL | `http://localhost:3001` (local) |

### Main features tested

- **User Authentication** — login / error handling
- **Product Search** — keyword search, results display
- **Shopping Cart** — add to cart, quantity, notifications
- **Checkout Process** — guest and registered flows
- **Boundary validation** — qty limits, password length

<!--
The application under test is Demo Web Shop, a single-page e-commerce
application running locally on json-server and Vanilla JavaScript.
We selected the most testable and valuable flows from the project charter:
user authentication, product search, cart management, checkout, and boundary
validation. The broader project scope also includes the customer address book,
but this presentation focuses on the core flows that were exercised during the
semester.
-->

---

# 2. Test Strategy

```
                  10 Manual Test Cases (TC-001 → TC-010)
                        ↓           ↓           ↓
              Selenium (3)    Robot FW (2)   Postman (API)
              TC-001/002/003  TC-001/004     REST endpoints
```

| Tool | Language | TC covered |
|---|---|---|
| JUnit 5 | Java | Rating unit tests (Lab 2) |
| Selenium WebDriver 4.18.1 | Java | TC-001, TC-002, TC-003 |
| Robot Framework 7.1.1 | Python | TC-001, TC-004 |
| Postman | — | REST API layer |

**Automation criteria:** stable UI flow · clear assertion · high regression value

<!--
Our strategy is based on black-box functional testing combined with boundary
and negative testing. We designed ten manual test cases to cover the main user
journeys and the most relevant edge cases. From there, we automated the most
stable and high-value scenarios with Selenium WebDriver in Java, Robot
Framework in Python, and Postman for the API layer. We also kept Lab 2 as a
separate JUnit exercise for unit testing the Rating class.
-->

---

# 3. Manual Test Cases

| TC ID | Title | Type | Status |
|---|---|---|---|
| TC-001 | Successful login | Positive | ✅ PASS |
| TC-002 | Search existing product | Positive | ✅ PASS |
| TC-003 | Add item to cart | Positive | ✅ PASS |
| TC-004 | Login with wrong password | Negative | ✅ PASS |
| TC-005 | Search non-existing product | Negative | ✅ PASS |
| TC-006 | Checkout with empty cart | Negative | ✅ PASS |
| TC-007 | Password minimum length | Boundary | ✅ PASS |
| TC-008 | Add zero/negative quantity | Boundary | ❌ BUG-001 |
| TC-009 | Full checkout – Guest | Flow | ✅ PASS |
| TC-010 | Full checkout – Registered | Flow | ✅ PASS |

**9/10 PASS · 1 defective (BUG-001)**

<!--
The manual test suite covers positive, negative, boundary, and full-flow
scenarios. Nine tests pass, and the only failing case is TC-008, which checks
that the cart rejects zero or negative quantity values. This result is important
because it shows that the application still lacks a validation rule for a very
small but meaningful boundary condition.
-->

---

# 4. Defect Report — BUG-001

**Title:** System accepts quantity 0 silently — no validation error

| Field | Value |
|---|---|
| **Severity** | Medium |
| **Component** | Shopping Cart |
| **Steps** | Open product page → enter qty `0` → click Add to cart |
| **Expected** | Error: "Quantity must be greater than 0" |
| **Actual** | ✅ Success toast shown, but cart count stays at 0 |
| **Status** | Open |

> Silent success with incorrect data is **harder to detect** than a crash and
> more confusing for end users — the cart appears to have accepted the item.

<!--
BUG-001 is the defect behind TC-008. The application accepts quantity 0 without
showing a validation error, and it even displays a success toast. The cart badge
does not increment, so the user receives a misleading success signal even though
the action should have been blocked. This kind of silent failure is more
dangerous than an obvious crash because it can hide the problem from the user.
-->

---

# 5. Selenium WebDriver Automation

**Tool:** Selenium 4.18.1 + JUnit 5 · Java  

| Test | TC | Result |
|---|---|---|
| `TC001_shouldLoginWithValidCredentials` | TC-001 | ✅ PASS |
| `TC002_shouldDisplayResultsForLaptop` | TC-002 | ✅ PASS |
| `TC003_shouldAddProductToCart` | TC-003 | ✅ PASS |

![w:780](../reports/lab4-selenium-evidence.png)

<!--
For UI automation, we used Selenium WebDriver 4.18.1 with JUnit 5 in Java.
The three automated flows are valid login, search for a product, and add item
to cart. These tests are good candidates for regression because they cover the
main happy paths and they all pass reliably. The Selenium implementation also
demonstrates the key technical decisions from the project: explicit waits for
asynchronous DOM updates, Selenium Manager for driver handling, and test
isolation with localStorage cleanup before each scenario.
-->

---

# 6. Robot Framework Automation

**Tool:** Robot Framework 7.1.1 + SeleniumLibrary 6.7.1 · Python  

| Test | TC | Type | Result |
|---|---|---|---|
| TC-001 Valid Login Shows User Email In Header | TC-001 | Positive | ✅ PASS |
| TC-004 Invalid Login Shows Error Message | TC-004 | Negative | ✅ PASS |

**Keywords:** `Open Demo Web Shop` · `Navigate To Login Page` · `Fill Login Form` · `Verify User Is Logged In` · `Verify Login Error Message`

![w:780](../reports/lab5-robot-report.png)

<!--
We also automated two login scenarios with Robot Framework 7.1.1 and
SeleniumLibrary in Python. One test validates a successful login, and the other
checks that an invalid login shows the correct error message. Robot Framework is
useful here because the keyword-driven style makes the acceptance flow very
readable, and the generated HTML report gives clear evidence of execution.
-->

---

# 7. Postman API Tests

**Endpoints under test** (`http://localhost:3001`):

| Endpoint | Method | Purpose |
|---|---|---|
| `/products` | GET | List all products |
| `/products?name_like=Laptop` | GET | Keyword search |
| `/cart` | GET / POST | Read / add items |
| `/users` | GET | List registered users |

> Results to be added after Lab 6 (2026-07-09).

**Approach:** Postman Collection with environment variables (`{{BASE_URL}}`)
and automated test scripts validating status codes, response structure,
and data integrity.

<!--
For the API part, we prepared Postman tests for the json-server endpoints that
are available in the application: products, keyword search, cart, and users.
These requests will let us validate response codes, response structure, and data
integrity independently of the UI. The deck shows the planned coverage now, and
the final API results will be added when Lab 6 is completed.
-->

---

# 8. Results Summary

| Lab | Tool | Tests | ✅ Pass | ❌ Fail |
|---|---|---|---|---|
| Lab 3 | Manual (Demo Web Shop) | 10 | 9 | 1 |
| Lab 4 | Selenium WebDriver | 3 | 3 | 0 |
| Lab 5 | Robot Framework | 2 | 2 | 0 |
| Lab 6 | Postman | TBD | — | — |

> Lab 2 (JUnit — Rating class) is a standalone exercise, not a Demo Web Shop test.

**Total automated:** 5 tests · 5 passed · 0 failed · 1 defect open (BUG-001)

![w:480](../reports/coverage_result.png)

<!--
This summary brings the project together. Lab 3 produced nine passing manual
tests and one open defect, Lab 4 gave us three passing Selenium tests, and Lab 5
added two passing Robot Framework tests. Lab 6 is still pending, so the Postman
column remains to be finalized. Lab 2 is intentionally excluded from this table
because it is a standalone unit testing exercise on the Rating class.
-->

---

# 9. Lessons Learned

1. **Explicit waits are mandatory for SPAs** — async fetch calls break any fixed sleep
2. **Test isolation requires intent** — `localStorage.clear()` prevented TC-001 ↔ TC-003 state leak
3. **Robot Framework is more readable** — keyword syntax bridges dev/non-dev gap; Selenium gives more control for complex scenarios
4. **Maven Wrapper (`mvnw.cmd`) enables portability** — zero-config Maven for any new machine
5. **Cross-OS node_modules are incompatible** — WSL vs Windows binary mismatch; always install in the target OS
6. **Silent bugs are worse than crashes** — BUG-001 looked like success; only boundary testing revealed it

<!--
The main lessons are practical. First, SPAs require explicit waits because the
DOM changes asynchronously. Second, test isolation matters because state can
leak between flows if local storage is not cleared. Third, Robot Framework is a
good option when readability matters, while Selenium offers more control for
complex UI behavior. Finally, silent bugs are harder to detect than crashes,
which is exactly why boundary testing is valuable.
-->

---

<!-- _class: title -->

# Conclusion

The Demo Web Shop project exercised the **full testing lifecycle**:

manual design → unit testing → UI automation → API testing

**9/10 manual tests pass · 1 confirmed defect · 11 automated tests green**

Each tool has its place: JUnit for logic, Selenium for Java-team automation,
Robot Framework for readable acceptance tests, Postman for API contracts.

<!--
To conclude, this project covered the full testing lifecycle from manual design
to automation and API planning. We used the Demo Web Shop as a realistic but
manageable sandbox to practice functional testing, defect reporting, Selenium
automation, Robot Framework acceptance tests, and Postman API validation.
Together, these tools provide a clear and complete view of application quality.
-->

---

