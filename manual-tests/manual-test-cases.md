# Manual Test Cases - Demo Web Shop

*Note: Tests marked with 🤖 are prime candidates for UI Automation (Selenium) in Lab 4.*

| Test Case ID | Title | Feature | Priority | Type | Preconditions | Test Data | Steps to Execute | Expected Result | Automation |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **TC-001** | Successful Login | Authentication | High | Positive | User is registered | Valid email & password | 1. Go to Login page<br>2. Enter credentials<br>3. Click 'Log in' | User is logged in, email appears in header. | 🤖 **Yes** |
| **TC-002** | Search existing product | Search | High | Positive | Home page is loaded | Keyword: "Laptop" | 1. Type keyword in search bar<br>2. Click Search | Products related to "Laptop" are displayed. | 🤖 **Yes** |
| **TC-003** | Add item to cart | Shopping Cart | High | Positive | User is on a product page | Product: Any available item | 1. Click "Add to cart" button | Success message appears, cart quantity increases. | 🤖 **Yes** |
| **TC-004** | Login with wrong password | Authentication | Medium | Negative | User is registered | Valid email, invalid password | 1. Go to Login page<br>2. Enter credentials<br>3. Click 'Log in' | Error message "Login was unsuccessful" is displayed. | 🤖 **Robot Framework** |
| **TC-005** | Search non-existing product | Search | Low | Negative | Home page is loaded | Keyword: "Xyz123Random" | 1. Type keyword in search<br>2. Click Search | Message "No products were found" is displayed. | No |
| **TC-006** | Checkout with empty cart | Checkout | Medium | Negative | Cart is empty | N/A | 1. Go to Shopping Cart<br>2. Attempt to click Checkout | Checkout button is disabled or error is shown. | No |
| **TC-007** | Password minimum length | Authentication | Medium | Boundary | On Registration page | Password: "12345" (5 chars) | 1. Fill valid details<br>2. Enter short password<br>3. Click Register | Error "Password must have at least 6 characters" is shown. | No |
| **TC-008** | Add zero or negative quantity | Shopping Cart | Medium | Boundary | On a product page | Quantity: "0" or "-1" | 1. Enter quantity<br>2. Click "Add to cart" | Error "Quantity must be greater than 0" is shown. | No |
| **TC-009** | Full Checkout Flow (Guest) | Checkout | High | Flow | Cart has 1 item | Guest email, random address | 1. Go to Cart -> Checkout<br>2. Choose "Guest"<br>3. Fill details<br>4. Confirm | "Your order has been successfully processed!" is shown. | No |
| **TC-010** | Full Checkout Flow (Registered)| Checkout | High | Flow | Logged in, Cart has 1 item | Saved user address | 1. Go to Cart -> Checkout<br>2. Use existing address<br>3. Confirm | "Your order has been successfully processed!" is shown. | No |