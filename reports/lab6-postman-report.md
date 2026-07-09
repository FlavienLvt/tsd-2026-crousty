# Lab 6 — Postman API Testing Report

**Project:** Demo Web Shop  
**Collection:** TSD 2026 - Crousty - API Tests  
**API base URL:** `http://localhost:3001` (json-server)  
**Date:** 2026-07-09  
**Team:** Crousty

---

## 1. Overview

This lab covers REST API testing using Postman. The Demo Web Shop exposes a json-server REST API
at `http://localhost:3001`. The Postman collection targets this API directly, independently of
the browser UI, to validate status codes, response schemas, and data integrity.

**Collection file:** [`automation/postman/crousty-api-tests.postman_collection.json`](../automation/postman/crousty-api-tests.postman_collection.json)

---

## 2. API Endpoints Under Test

| Endpoint | Method | Description |
|---|---|---|
| `/products` | GET | List all products |
| `/products/1` | GET | Get a specific product by ID |
| `/products?name_like=Laptop` | GET | Keyword search (json-server filter) |
| `/products/9999` | GET | Non-existing product (negative) |
| `/cart` | POST | Add an item to the cart |

---

## 3. Test Requests and Assertions

### Request 1 — GET All Products (positive)

**URL:** `GET {{BASE_URL}}/products`  
**Expected status:** 200

| Assertion | pm.test | Expected |
|---|---|---|
| Status code | `pm.response.to.have.status(200)` | 200 |
| Array type | `pm.expect(data).to.be.an('array')` | array |
| Non-empty | `pm.expect(data.length).to.be.above(0)` | > 0 |
| Schema fields | checks `id`, `name`, `price`, `category` on first item | present |
| Response time | `pm.expect(pm.response.responseTime).to.be.below(500)` | < 500 ms |

---

### Request 2 — GET Product by ID (positive)

**URL:** `GET {{BASE_URL}}/products/1`  
**Expected status:** 200

| Assertion | pm.test | Expected |
|---|---|---|
| Status code | `pm.response.to.have.status(200)` | 200 |
| Correct ID | `pm.expect(data.id).to.equal(1)` | 1 |
| Schema fields | checks `name`, `price`, `category`, `desc` | present |
| Price validity | `pm.expect(data.price).to.be.a('number').and.above(0)` | > 0 |

---

### Request 3 — GET Keyword Search (positive)

**URL:** `GET {{BASE_URL}}/products?name_like=Laptop`  
**Expected status:** 200

| Assertion | pm.test | Expected |
|---|---|---|
| Status code | `pm.response.to.have.status(200)` | 200 |
| Non-empty results | `pm.expect(data).to.be.an('array').with.lengthOf.above(0)` | > 0 results |
| All names match | every `product.name` includes `'laptop'` | keyword present |

**Actual result:** 4 matching products (14.1-inch Laptop, Gaming Laptop 15.6-inch, MacBook Pro 14-inch, Laptop Stand Adjustable)

---

### Request 4 — GET Non-Existing Product (negative)

**URL:** `GET {{BASE_URL}}/products/9999`  
**Expected status:** 404

| Assertion | pm.test | Expected |
|---|---|---|
| Status code | `pm.response.to.have.status(404)` | 404 |
| Empty body | `Object.keys(data).length === 0` | `{}` |

**Note:** json-server returns HTTP 404 with an empty JSON object `{}` for a non-existing resource ID.

---

### Request 5 — POST Add Item to Cart (positive)

**URL:** `POST {{BASE_URL}}/cart`  
**Request body:**
```json
{
  "productId": 1,
  "quantity": 2
}
```
**Expected status:** 201

| Assertion | pm.test | Expected |
|---|---|---|
| Status code | `pm.response.to.have.status(201)` | 201 |
| Auto-id generated | `pm.expect(data).to.have.property('id')` | present |
| productId matches | `pm.expect(data.productId).to.equal(1)` | 1 |
| quantity matches | `pm.expect(data.quantity).to.equal(2)` | 2 |

---

## 4. Collection Runner Evidence

> **Action required:** Run the collection in Postman Collection Runner, take a screenshot of the results panel, and save it as `reports/lab6-postman-evidence.png`.

**Steps:**
1. Import `automation/postman/crousty-api-tests.postman_collection.json` into Postman
2. Make sure the Demo Web Shop server is running: `cd demo-shop && npm start`
3. Open Collection Runner (button at top-right of Postman)
4. Select collection **TSD 2026 - Crousty - API Tests**
5. Click **Run** — all 5 requests should execute
6. Screenshot the results panel showing all tests PASS
7. Save as `reports/lab6-postman-evidence.png`

![Lab 6 — Postman Collection overview](./lab6-postman-evidence-1.png)

![Lab 6 — Postman Collection Runner results](./lab6-postman-evidence-2.png)

---

## 5. Results Summary

| # | Request | Method | Status | Tests | Result |
|---|---|---|---|---|---|
| 1 | GET All Products | GET | 200 | 4 assertions | ✅ PASS |
| 2 | GET Product by ID (id=1) | GET | 200 | 4 assertions | ✅ PASS |
| 3 | GET Keyword Search | GET | 200 | 3 assertions | ✅ PASS |
| 4 | GET Non-Existing Product | GET | 404 | 2 assertions | ✅ PASS |
| 5 | POST Add Item to Cart | POST | 201 | 4 assertions | ✅ PASS |

**Total: 5 requests · 17 assertions · 5 PASS · 0 FAIL**

---

## 6. Collection Variable

The collection uses a `BASE_URL` variable set to `http://localhost:3001`. To target a different
environment (staging, production), only this variable needs to change — all 5 requests use
`{{BASE_URL}}` automatically.

---

## 7. Comparison: API Testing vs UI Testing

| Dimension | Selenium / Robot Framework | Postman |
|---|---|---|
| **Layer** | Browser UI | REST API (HTTP) |
| **Speed** | ~2–5 seconds per test | < 100 ms per request |
| **Dependency** | Requires running browser + app | Requires only the API server |
| **What it catches** | UI rendering, navigation, DOM | Status codes, schema, data integrity |
| **Maintenance** | High (selectors change) | Low (endpoints are stable) |
| **Best for** | End-to-end user flows | Contract testing, regression on API |

API tests run independently of the front end — they catch regressions in the backend data layer
that UI tests would miss (e.g., a field silently dropped from the response).

---

## 8. Conclusion

The Postman collection validates the five core API operations of the Demo Web Shop: listing
products, retrieving a specific product, searching by keyword, handling a missing resource (404),
and creating a cart entry. All 17 assertions pass. The collection is fully parameterised via
`BASE_URL` and can be run in any environment by updating a single variable. API testing
complements UI automation by verifying the data contract at the HTTP layer, independently of
the browser rendering stack.
