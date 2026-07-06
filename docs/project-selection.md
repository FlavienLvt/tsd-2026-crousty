# Project Selection

## Project name
Demo Web Shop

## Project URL
http://localhost:3001 (run `cd demo-shop && npm start` first)

## Application type
Web *(Note: As identified in our risks, we might need to pair this with a public API sandbox like JSONPlaceholder or Restful Booker for Lab 6 if no internal API is accessible.)*

## Main functionality
A realistic demo e-commerce platform that allows users to browse product categories, manage a shopping cart, and simulate the checkout process.

## Features selected for testing
1. User Authentication (Registration, login, and password recovery)
2. Shopping Cart Management (Adding, updating, and removing items)
3. Customer Address Book (Adding, editing, and deleting addresses)
4. Product Search (Searching by keywords and filtering)
5. Checkout Process (Billing, shipping, and confirming orders)

## Manual testing possibilities
The application is highly suitable for manual testing. We can design test cases for various user flows (both registered and unregistered users), boundary value analysis on registration/address forms, and exploratory testing on the cart and checkout mechanics.

## Automation possibilities
* **Selenium:** Ideal for automating the main UI flows such as the checkout process, login, and dynamic cart updates.
* **Robot Framework:** Can be used to write keyword-driven acceptance tests for our selected features (e.g., `Given user is logged in`, `When user adds item to cart`, `Then cart count increases`).
* **Postman:** To be determined based on hidden endpoint discovery, or we will use an external API sandbox for this specific requirement.

## Risks
* **App changes during semester:** UI updates might break automated scripts and locators.
* **Test data management:** Continuous creation of accounts and addresses could cause conflicts.
* **Dynamic UI elements:** Flaky Selenium scripts due to loading times.
* **No public API available:** Might impact our ability to execute standard API testing.