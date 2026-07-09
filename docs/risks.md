# Project Risks and Mitigations

This document tracks potential risks associated with testing the Demo Web Shop application and their resolution status.

## Risk Register

| Risk | Potential Impact | Mitigation | Status |
| :--- | :--- | :--- | :--- |
| **Application updates during the semester** | Automated scripts and locators break if the UI structure changes | Keep test cases modular; use stable locators (ID, CSS selectors); avoid absolute XPaths | ✅ No breaking changes occurred |
| **Test data accumulation** | Continuous cart/order creation causes state conflicts between test runs | localStorage.clear() in @BeforeEach (Selenium); git restore demo-shop/db.json before full runs | ✅ Mitigated |
| **Dynamic UI elements** | Async fetch calls make Selenium scripts flaky | Strict use of explicit waits (WebDriverWait, Wait Until Element Is Visible) — no Thread.sleep() | ✅ Mitigated |
| **Lack of public API** | No REST API available for Postman testing | json-server exposes a full REST API at the same base URL — no external sandbox needed | ✅ Resolved — used own API |
| **Git merge conflicts** | Parallel work on the same files causes lost changes | One branch per task; small focused commits; communicate before merging to main | ✅ Mitigated |
| **Render free tier cold start** | Service spins down after 15 min inactivity; first request is slow | UptimeRobot monitor pings the URL every 5 minutes | ✅ Mitigated |

## Defects Found

| ID | Title | Severity | Status |
| :--- | :--- | :--- | :--- |
| BUG-001 | Cart accepts quantity 0 silently — no validation error | Medium | ✅ Fixed |
