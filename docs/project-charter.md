# Project Charter

This document defines the initial testing scope for our semester project.

## 1. Tested Application
* **Name:** Demo Web Shop
* **URL:** http://localhost:3001 (run `cd demo-shop && npm start` first)
* **Short Description:** A realistic demo e-commerce platform designed for software testing. It allows users to browse product categories, manage a shopping cart, and simulate the checkout process.

## 2. Business Purpose
* **Purpose:** To provide a complete and functional online shopping experience for customers. The platform handles everything from product discovery and cart management to simulating the final checkout, serving as a reliable sandbox for automated and manual testing.

## 3. Main Users
* **Unregistered Visitor:** Can browse products, use the search function, and add items to the cart, but cannot proceed to checkout without registering.
* **Registered Customer:** Can log in, manage their personal information, maintain an address book, view past orders, and complete the checkout process.

## 4. Selected Features
| Feature | Description | Priority |
| :--- | :--- | :--- |
| **User Authentication** | Account creation (insertion), login, and password recovery. | **High** |
| **Shopping Cart Management** | Adding products to the cart (insertion), updating item quantities (modification), and removing items (deletion). | **High** |
| **Customer Address Book** | Adding new shipping/billing addresses, editing existing ones, and deleting old addresses from the user profile. | **Medium** |
| **Product Search** | Searching for specific items using keywords and filtering results by categories or price. | **Medium** |
| **Checkout Process** | Navigating through the billing, shipping, and shipping method steps to confirm an order. | **High** |

## 5. Out of Scope
The following areas will **not** be tested during this project:
* Payment gateway (actual credit card processing)
* Real order processing and fulfillment backend
* Admin dashboard and inventory management
* Performance, load, and security testing

## 6. Initial Risks
| Risk | Impact | Mitigation |
| :--- | :--- | :--- |
| **App changes during semester** | Automated scripts and locators might break if the UI is updated. | Keep test cases modular and use robust locators (e.g., specific IDs or XPath). |
| **Test data management** | Creating new accounts and addresses continuously can clutter the system or cause conflicts. | Design automated tests to clean up their own data (e.g., delete the address at the end of the test) or use randomized emails. |
| **Dynamic UI elements** | Selenium scripts might become flaky if elements take time to load. | Use explicit waits (`WebDriverWait`) instead of hardcoded `Thread.sleep()`. |
| **No public API available** | We may not be able to perform standard API testing with Postman. | Verify if the application has hidden endpoints via the browser's network tab, or consult the instructor for alternatives. |