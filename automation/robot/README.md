# Robot Framework Tests — Demo Web Shop

## Prerequisites

| Tool | Version | Note |
|------|---------|------|
| Python | 3.8+ | Required |
| Chrome | Latest stable | Required |
| Node.js | 18+ | Required (demo-shop server) |

> ChromeDriver is managed automatically by Selenium Manager (bundled with SeleniumLibrary 6+).

---

## Step 1 — Install Python dependencies

```cmd
cd automation\robot
python -m pip install -r requirements.txt
```

This installs `robotframework 7.1.1` and `robotframework-seleniumlibrary 6.7.1`.

---

## Step 2 — Start the Demo Web Shop server

The tests require the backend to be running at `http://localhost:3001`.

```bash
cd demo-shop
npm install   # first time only
npm start
```

Leave the terminal open.

---

## Step 3 — Run the tests

Open a second terminal:

```cmd
cd automation\robot
python -m robot --outputdir results tests/demo_shop_tests.robot
```

Results are saved to `automation/robot/results/`:
- `report.html` — human-readable test report (open in browser)
- `log.html` — detailed step-by-step execution log
- `output.xml` — machine-readable results (not committed)

---

## Test cases covered

| Test Name | TC ID | Type | Description |
|---|---|---|---|
| TC-001 Valid Login Shows User Email In Header | TC-001 | Positive | Valid credentials → email appears in header |
| TC-004 Invalid Login Shows Error Message | TC-004 | Negative | Wrong password → error banner displayed |

---

## Demo credentials

| Field | Value |
|---|---|
| Email | `demo@webshop.com` |
| Password | `demo123` |

---

## Troubleshooting

**`Connection refused`** — the backend is not running. Follow Step 2.

**`SessionNotCreatedException`** — Chrome is not installed or outdated.

**`No module named 'robot'`** — run `pip install -r requirements.txt` first.
