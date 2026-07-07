# Selenium WebDriver Tests — Demo Web Shop

## Prerequisites

| Tool | Version | Note |
|------|---------|------|
| Java | 17 or higher | Required |
| Chrome | Latest stable | Required |
| Node.js | 18+ | Required (to run the demo-shop server) |
| Maven | — | **Not required** — `mvnw.cmd` downloads Maven automatically |

> Selenium 4 includes built-in **Selenium Manager** — no manual ChromeDriver download needed.

---

## Step 1 — Start the Demo Web Shop server

The tests require the backend to be running at `http://localhost:3001`.

```bash
cd demo-shop
npm install   # required on every new machine (node_modules is not committed to git)
npm start
```

You should see:

```
  Demo Web Shop

  Frontend  ->  http://localhost:3001
  API base  ->  http://localhost:3001
```

Leave the terminal open.

---

## Step 2 — Run the Selenium tests

Open a second terminal:

```cmd
cd automation\selenium
mvnw.cmd test
```

`mvnw.cmd` downloads Maven automatically on first run (stored in `%USERPROFILE%\.m2\wrapper\dists\`), then downloads Selenium dependencies via Maven.

---

## Test cases covered

| Test method | TC ID | Description |
|---|---|---|
| `TC001_shouldLoginWithValidCredentials` | TC-001 | Valid login → email shown in header |
| `TC002_shouldDisplayResultsWhenSearchingForLaptop` | TC-002 | Search "Laptop" → results with product cards |
| `TC003_shouldAddProductToCartAndShowNotification` | TC-003 | Add to cart → success toast + cart count increases |

---

## Demo credentials

| Field | Value |
|---|---|
| Email | `demo@webshop.com` |
| Password | `demo123` |

---

## Troubleshooting

**`Connection refused` error** — the backend server is not running. Follow Step 1.

**`SessionNotCreatedException`** — Chrome is not installed or outdated. Install/update Chrome.

**Tests time out** — the page takes too long to load. Check that Node.js is version 18+ and the server started without errors.
