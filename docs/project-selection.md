# Project Selection

## Project name

Demo Web Shop

## Project URLs

| Environment | URL |
| :--- | :--- |
| **Live (Render)** | https://tsd-2026-crousty.onrender.com |
| **Local** | http://localhost:3001 (run `cd demo-shop && npm start`) |

## Application type

Single-page web application (SPA) with a REST API backend powered by json-server.

## Main functionality

A realistic demo e-commerce platform that allows users to browse product categories, manage a shopping cart, and simulate the checkout process. Data is persisted in a `db.json` file served by json-server, which also exposes a full REST API (`/products`, `/cart`, `/users`, `/orders`).

## Features selected for testing

1. **User Authentication** — Login with valid/invalid credentials, password validation
2. **Shopping Cart Management** — Add items, update quantity (boundary), remove items
3. **Product Search** — Keyword search (`name_like` filter), empty results handling
4. **Checkout Process** — Guest and registered user flows, empty cart protection
5. **Boundary Validation** — Quantity = 0, password minimum length

## Manual testing possibilities

The application is highly suitable for manual testing. We designed 10 test cases covering positive, negative, boundary, and full-flow scenarios. The hash-based SPA routing and async fetch calls also offered interesting edge cases for exploratory testing.

## Automation possibilities

| Tool | Use case |
| :--- | :--- |
| **Selenium WebDriver** | UI automation of TC-001 (login), TC-002 (search), TC-003 (add to cart) |
| **Robot Framework** | Keyword-driven tests for TC-001 (positive login) and TC-004 (negative login) |
| **Postman** | REST API testing against `/products`, `/cart` — no external sandbox needed, the app exposes its own API |
| **JUnit 5** | Standalone unit testing exercise on the `Rating` class (Lab 2, independent of Demo Web Shop) |

## API availability

The json-server backend exposes a full REST API at the same base URL as the frontend. Key endpoints: `GET /products`, `GET /products/:id`, `GET /products?name_like=X`, `POST /cart`, `DELETE /cart/:id`. No external API sandbox was required.
