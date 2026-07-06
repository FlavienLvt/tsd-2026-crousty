# Test Plan: Demo Web Shop

## 1. Introduction and Scope
This document outlines the testing strategy for the **Demo Web Shop** project. The goal is to verify the core functionalities of the e-commerce platform to ensure a smooth user experience. 
* **In Scope:** User Authentication, Shopping Cart Management, Product Search, Customer Address Book, and Checkout Process.
* **Out of Scope:** Payment gateway integration, Admin dashboard, backend order processing, and performance testing.

## 2. Test Objectives
* Ensure users can successfully register, log in, and manage their profiles.
* Verify that the core e-commerce flow (search -> add to cart -> checkout) works flawlessly.
* Identify and document UI/UX defects or functional bugs before release.

## 3. Test Types
* **Functional Testing:** Black-box testing to verify features against requirements.
* **Boundary & Equivalence Testing:** Testing input fields (e.g., quantity limits, password lengths).
* **Negative Testing:** Ensuring the system handles invalid inputs gracefully.
* **UI Automation (Future):** Selected critical flows will be automated using Selenium in Lab 4.

## 4. Test Environment
* **OS:** Windows 11 / macOS
* **Browser:** Google Chrome (Latest Version)
* **Application URL:** https://flavienlvt.github.io/tsd-2026-crousty/demo-shop/

## 5. Risks and Mitigation
* **Risk:** The demo application might reset its database, causing test data loss.
* **Mitigation:** Test cases are designed to create their own test data (e.g., registering a new user) before execution when possible, or we will use standard existing demo credentials.