# Test Plan: Demo Web Shop

## 1. Introduction and Scope

This document outlines the testing strategy for the **Demo Web Shop** project. The goal is to verify the core functionalities of the e-commerce platform to ensure a smooth user experience.

- **In Scope:** User Authentication, Shopping Cart Management, Product Search, and Checkout Process.
- **Out of Scope:** Payment gateway integration, admin dashboard, backend order processing, performance testing, and security testing.

## 2. Test Objectives

- Ensure users can successfully log in and manage their sessions.
- Verify that the core e-commerce flow (search → add to cart → checkout) works correctly.
- Identify and document functional defects, including boundary and negative scenarios.
- Automate the highest-value test cases using Selenium, Robot Framework, and Postman.

## 3. Test Types

| Type | Description | Labs |
| :--- | :--- | :--- |
| **Manual Functional Testing** | Black-box testing of features against requirements | Lab 3 |
| **Unit Testing** | Isolated class-level tests with JUnit 5 | Lab 2 |
| **Boundary & Equivalence Testing** | Input fields: quantity limits, password lengths | Lab 3 |
| **Negative Testing** | Invalid inputs, empty states, wrong credentials | Lab 3 |
| **UI Automation** | Selected critical flows automated with Selenium + Robot Framework | Labs 4 & 5 |
| **API Testing** | REST endpoints validated with Postman | Lab 6 |

## 4. Test Environment

| Dimension | Value |
| :--- | :--- |
| **OS** | Windows 11 |
| **Browser** | Google Chrome (latest) |
| **Live URL** | https://tsd-2026-crousty.onrender.com |
| **Local URL** | http://localhost:3001 (run `cd demo-shop && npm start`) |
| **Java** | 17+ (Selenium + JUnit) |
| **Python** | 3.10+ (Robot Framework) |
| **Node.js** | 18+ (json-server backend) |

## 5. Automation Criteria

A test case is selected for automation if it meets **all three** of the following:

1. The UI flow is stable and unlikely to change frequently
2. The assertion is clear and deterministic
3. The test has high regression value (runs frequently, critical path)

## 6. Test Data Strategy

- **Selenium tests:** `localStorage.clear()` in `@BeforeEach` to prevent state leakage between tests.
- **Robot Framework tests:** Fresh browser instance per test case.
- **Postman:** `BASE_URL` variable for environment switching (local ↔ Render).
- **db.json reset:** Run `git restore demo-shop/db.json` to restore the clean initial dataset between full test runs.

## 7. Risks and Mitigation

| Risk | Mitigation |
| :--- | :--- |
| Async DOM updates causing flaky tests | Explicit waits only — no Thread.sleep() or fixed delays |
| Cart/order state persisting between test runs | localStorage.clear() for Selenium; git restore for db.json |
| Render free tier cold start (30s delay) | UptimeRobot monitor pings the URL every 5 minutes to keep the instance warm |
