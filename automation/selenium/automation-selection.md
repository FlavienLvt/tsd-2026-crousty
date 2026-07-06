# Automation Candidate Selection — Lab 4

Base URL: `http://localhost:3001`  
Tool chosen: **Selenium WebDriver (Java)**

## Decision Table

| TC ID  | Title                          | Automate? | Reason                                                                                  |
| :----- | :----------------------------- | :-------: | :-------------------------------------------------------------------------------------- |
| TC-001 | Successful Login               | ✅ Yes    | Stable, repetitive, clear expected result (email visible in header). Good regression candidate. |
| TC-002 | Search existing product        | ✅ Yes    | Deterministic input/output. Result list is verifiable with a single assertion.          |
| TC-003 | Add item to cart               | ✅ Yes    | Core e-commerce action. Toast message and cart badge are stable locators.               |
| TC-004 | Login with wrong password      | ❌ No     | Covered implicitly when TC-001 is a regression test. Error message is static text — low additional value. |
| TC-005 | Search non-existing product    | ❌ No     | Low priority negative test. Easy to verify manually in seconds.                         |
| TC-006 | Checkout with empty cart       | ❌ No     | Requires precise pre-condition (cart must be empty). State management adds fragility.   |
| TC-007 | Password minimum length        | ❌ No     | Boundary test. Best verified manually to observe exact validation UX behavior.          |
| TC-008 | Add zero or negative quantity  | ❌ No     | Boundary test. Requires specific numeric input; acceptable to keep manual.             |
| TC-009 | Full Checkout Flow (Guest)     | ❌ No     | Long multi-step flow with form data. High maintenance cost; better suited for end-to-end framework. |
| TC-010 | Full Checkout Flow (Registered)| ❌ No     | Depends on login state + address data. Too many dependencies for a basic Selenium test. |

## Summary

- **Automated:** TC-001, TC-002, TC-003 (3 tests)
- **Kept manual:** TC-004 to TC-010
- **Main reason for keeping manual:** tests involving complex multi-step flows, boundary inputs, or heavy pre-conditions are fragile and expensive to maintain at this stage.
