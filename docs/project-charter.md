# Project Charter

This document defines the initial testing scope for our semester project.

## 1. Tested Application

| Field | Value |
| :--- | :--- |
| **Name** | Demo Web Shop |
| **Live URL** | https://tsd-2026-crousty.onrender.com |
| **Local URL** | http://localhost:3001 (run `cd demo-shop && npm start`) |
| **Type** | Single-page e-commerce application (SPA) |

**Short description:** A realistic demo e-commerce platform designed for software testing. It allows users to browse product categories, manage a shopping cart, and simulate the checkout process. The backend is a json-server REST API backed by a local `db.json` file.

## 2. Business Purpose

To provide a complete and functional online shopping experience. The platform handles product discovery, cart management, and checkout simulation, making it a reliable and fully controllable sandbox for automated and manual testing.

## 3. Main Users

| User Type | Description |
| :--- | :--- |
| **Unregistered Visitor** | Can browse products, search, and add to cart. Cannot checkout without registering. |
| **Registered Customer** | Can log in, manage profile and address, view past orders, and complete checkout. |

## 4. Selected Features

| Feature | Description | Priority |
| :--- | :--- | :--- |
| **User Authentication** | Login and password validation | **High** |
| **Shopping Cart Management** | Add, update quantity, remove items | **High** |
| **Product Search** | Search by keyword, filter by category | **Medium** |
| **Checkout Process** | Guest and registered checkout flow | **High** |
| **Boundary Validation** | Quantity limits, password length | **Medium** |

## 5. Out of Scope

- Payment gateway (no real card processing)
- Real order fulfillment backend
- Admin dashboard and inventory management
- Performance, load, and security testing
- Mobile / responsive layout

## 6. Initial Risks

| Risk | Impact | Mitigation |
| :--- | :--- | :--- |
| **App changes during semester** | Automated scripts may break if UI is updated | Use robust locators (IDs, CSS selectors); keep tests modular |
| **Test data management** | Continuous account/cart creation can cause conflicts | Use localStorage.clear() and git restore for db.json between runs |
| **Dynamic UI elements** | Async fetch calls may cause flaky Selenium tests | Use explicit waits (WebDriverWait) — never Thread.sleep() |
| **API availability** | *(Resolved)* Initially unknown whether the app exposed a REST API | json-server exposes a full REST API at the same URL — used for Lab 6 Postman tests |
| **Git merge conflicts** | Parallel work on same files may cause conflicts | One branch per task; small commits; communicate before merging to main |
