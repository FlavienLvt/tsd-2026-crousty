# Lab 4 — Selenium UI Test Automation Report

**Project:** Demo Web Shop  
**Application URL:** http://localhost:3001  
**Date:** 2026-07-06  
**Tool:** Selenium WebDriver 4.18.1 (Java) + JUnit 5  

---

## 1. Selected Test Cases

Three test cases were selected for automation based on the decision table in
`automation/selenium/automation-selection.md`.

| TC ID  | Title                   | Why automated |
|--------|-------------------------|---------------|
| TC-001 | Successful Login        | Stable UI flow, clear assertion (email in header), high regression value |
| TC-002 | Search existing product | Deterministic input/output, easily verifiable with element locators |
| TC-003 | Add item to cart        | Core e-commerce action, toast and badge are stable DOM targets |

---

## 2. Tool Used

**Selenium WebDriver 4.18.1 with Java**

- WebDriver 4 includes built-in Selenium Manager — no manual ChromeDriver configuration required.
- Tests are written with JUnit 5 (`@Test`, `@BeforeEach`, `@AfterEach`, `@DisplayName`).
- Explicit waits (`WebDriverWait` + `ExpectedConditions`) are used instead of `Thread.sleep()` for reliability.
- `localStorage.clear()` is executed via `JavascriptExecutor` before each test to ensure test isolation.

Test file: `automation/selenium/src/test/java/DemoWebShopTest.java`

---

## 3. Execution Evidence

Run date: **2026-07-06 at 13:39 (UTC+2)**  
Command: `cd automation\selenium && mvnw.cmd test`

```
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 15.63 s -- in DemoWebShopTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] BUILD SUCCESS
[INFO] Total time: 37.205 s
[INFO] Finished at: 2026-07-06T13:39:00+02:00
```

Screenshot: `reports/lab4-selenium-evidence.png`

| Test | Status | Assertion verified |
|------|--------|--------------------|
| TC-001 — Valid Login | ✅ PASS | `a[href='#/account']` text contains `demo@webshop.com` |
| TC-002 — Search "Laptop" | ✅ PASS | `.page-title` contains "Laptop" AND `product-card` count > 0 |
| TC-003 — Add to cart | ✅ PASS | `#toast` visible with "added to your shopping cart" AND `#cart-count` increased |

---

## 4. Problems Encountered

| Problem | Solution |
|---------|----------|
| Cart badge uses two identifiers (`id="cart-count"` and `class="cart-qty"`) | Used `By.id("cart-count")` — the ID is more specific and stable |
| Products load asynchronously via `fetch()` | Used `ExpectedConditions.presenceOfElementLocated` before interacting with product cards |
| Cart count update is async (server round-trip) | Used `ExpectedConditions.not(textToBe(...))` instead of a fixed sleep |
| `localStorage` persists between tests in the same session | Cleared storage via `JavascriptExecutor` in `@BeforeEach` |

---

## 5. What Should Remain Manual

The following test cases are intentionally kept manual:

| TC ID  | Title                          | Reason |
|--------|--------------------------------|--------|
| TC-004 | Login with wrong password      | Static error message, easy to check manually. Covered implicitly by TC-001 regression. |
| TC-005 | Search non-existing product    | Negative path, trivial to verify visually. |
| TC-006 | Checkout with empty cart       | Requires controlled pre-condition (empty cart). Complex state setup adds fragility. |
| TC-007 | Password minimum length        | Boundary test — manual observation better captures the full UX validation behavior. |
| TC-008 | Add zero or negative quantity  | Boundary test — specific numeric input, best explored manually. |
| TC-009 | Full Guest Checkout            | Long multi-step form flow. High maintenance cost; better suited for a dedicated E2E framework. |
| TC-010 | Full Registered Checkout       | Requires login + address state. Too many dependencies for basic WebDriver tests. |

**Key insight:** Automation adds the most value for stable, repetitive, high-traffic paths (login,
search, add to cart). Complex flows (checkout), boundary inputs, and error messages have higher
maintenance cost than their automation benefit — especially at this project scale.
