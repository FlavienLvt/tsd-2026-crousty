# Project Risks and Open Questions

This document tracks the potential risks associated with testing the Demo Web Shop application and lists open questions we need to resolve as the semester progresses.

## 1. Initial Project Risks

| Risk | Potential Impact | Mitigation Strategy |
| :--- | :--- | :--- |
| **Application updates during the semester** | Automated scripts and locators might break if the developers change the UI structure. | We will keep test cases modular and use robust locators (e.g., ID, CSS selectors, or reliable XPaths) instead of absolute paths. |
| **Test data accumulation** | Creating new accounts and addresses continuously can clutter the system, leading to conflicts or false test failures. | We will design automated tests to clean up their own data at the end of the execution (teardown), or use randomized data generators for emails and usernames. |
| **Dynamic UI elements** | UI elements taking time to load can make Selenium automation scripts flaky and unreliable. | We will strictly use explicit waits (`WebDriverWait`) instead of hardcoded `Thread.sleep()` to wait for elements to be clickable or visible. |
| **Lack of public API** | If the app does not have a documented API, the API testing phase (Postman) will be difficult to execute. | We will inspect the browser's Network tab to find hidden endpoints. If none are usable, we will ask the instructor for an alternative API to test. |
| **Git merge conflicts** | Multiple team members working on the same documentation or test files could lead to complex merge conflicts and lost work. | We will stick to the rule of using one branch per task, commit small changes, and quickly communicate with the team before merging into `main`. |

## 2. Open Questions (Homework for Lab 2)

* **API Endpoints:** Does the Demo Web Shop have functional REST API endpoints that we can easily trigger for the Postman lab, or will we need a secondary application for that specific lab?
* **Test Data Pre-condition:** Should we create a set of permanent "dummy" user accounts manually before starting automation, or should every test script create its own user?
* **Browser Scope:** Are we expected to run our Selenium automated tests only on Google Chrome, or do we need to ensure cross-browser compatibility (e.g., Firefox, Edge)?