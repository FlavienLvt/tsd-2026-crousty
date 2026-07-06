import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Selenium WebDriver UI tests for Demo Web Shop (http://localhost:3001)
 *
 * Covers:
 *   TC-001 — Successful Login
 *   TC-002 — Search existing product
 *   TC-003 — Add item to cart
 *
 * Prerequisites:
 *   1. Node.js installed
 *   2. Server running:  cd demo-shop && npm install && npm start
 *   3. Chrome browser installed (ChromeDriver is managed automatically by Selenium 4)
 *
 * Run:
 *   cd automation/selenium && mvn test
 */
class DemoWebShopTest {

    private static final String BASE_URL = "http://localhost:3001";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Clear session state before each test for isolation
        driver.get(BASE_URL);
        ((JavascriptExecutor) driver).executeScript("localStorage.clear();");
        driver.navigate().refresh();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("site-header")));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC-001: Successful Login
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC-001: Valid login shows user email in header")
    void TC001_shouldLoginWithValidCredentials() {
        // Navigate to login page
        driver.get(BASE_URL + "/#/login");

        // Fill in credentials
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-email")))
                .sendKeys("demo@webshop.com");
        driver.findElement(By.id("login-pwd")).sendKeys("demo123");
        driver.findElement(By.cssSelector("#app button[type='submit']")).click();

        // Assert: user email link appears in header after successful login (TC-001 expected result)
        WebElement userLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".header-actions a[href='#/account']")));

        assertTrue(
                userLink.getText().contains("demo@webshop.com"),
                "Expected 'demo@webshop.com' to be shown in the header after login"
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC-002: Search existing product
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC-002: Searching 'Laptop' displays matching products")
    void TC002_shouldDisplayResultsWhenSearchingForLaptop() {
        // Wait for search input to be available
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-input")));

        // Enter search keyword and submit
        driver.findElement(By.id("search-input")).sendKeys("Laptop");
        driver.findElement(By.cssSelector(".search-wrap button[type='submit']")).click();

        // Assert: page title contains the search keyword (TC-002 expected result)
        WebElement pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("page-title")));

        assertTrue(
                pageTitle.getText().contains("Laptop"),
                "Expected search results page title to contain 'Laptop'"
        );

        // Assert: at least one product card is displayed
        List<WebElement> productCards = driver.findElements(By.className("product-card"));
        assertTrue(
                productCards.size() > 0,
                "Expected at least one product to appear for search term 'Laptop'"
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC-003: Add item to cart
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC-003: Adding a product shows success notification and updates cart count")
    void TC003_shouldAddProductToCartAndShowNotification() {
        // Wait for at least one product card to be rendered (async fetch)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("product-card")));

        // Record initial cart count
        int initialCount = Integer.parseInt(
                driver.findElement(By.id("cart-count")).getText().trim()
        );

        // Click "Add to cart" on the first visible product
        driver.findElement(By.cssSelector(".product-card .btn-primary")).click();

        // Assert: success toast notification appears (TC-003 expected result)
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("toast")));

        assertTrue(
                toast.getText().contains("added to your shopping cart"),
                "Expected success toast message after adding product to cart"
        );

        // Assert: cart badge count has increased
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBe(By.id("cart-count"), String.valueOf(initialCount))
        ));

        int newCount = Integer.parseInt(
                driver.findElement(By.id("cart-count")).getText().trim()
        );
        assertTrue(
                newCount > initialCount,
                "Expected cart count to increase from " + initialCount + " but got " + newCount
        );
    }
}
