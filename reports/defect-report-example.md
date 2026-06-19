# Defect Report

**Defect ID:** BUG-001  
**Title:** System allows adding 0 or negative quantities of a product to the cart.  
**Date Reported:** 2026-06-19  
**Reporter:** Team Crousty 

## 1. Classification
* **Severity:** Medium (Causes confusion but doesn't crash the app)
* **Priority:** Medium
* **Component:** Shopping Cart

## 2. Environment
* **Application:** Demo Web Shop
* **OS:** Windows 11
* **Browser:** Google Chrome (Version 126)
* **Test Case Reference:** `TC-008` (Boundary testing variation)

## 3. Steps to Reproduce
1. Navigate to the Demo Web Shop homepage.
2. Click on any product to open its details page (e.g., "14.1-inch Laptop").
3. In the 'Qty' (Quantity) input field, delete the default value "1" and enter "0" (or "-1").
4. Click the "Add to cart" button.
5. Check the green notification bar at the top of the screen.

## 4. Expected Result
The system should reject the input and display a validation error message such as "Quantity must be greater than 0".

## 5. Actual Result
The system displays a green success message: "The product has been added to your shopping cart". However, the cart counter remains at (0) and no item is actually added. This is highly confusing for the user.

## 6. Attachments
* *[Placeholder for Screenshot: screenshot-bug-quantity.png showing the success message with a 0 quantity]*