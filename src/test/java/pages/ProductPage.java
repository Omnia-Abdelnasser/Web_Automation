package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;

import java.time.Duration;

public class ProductPage extends WaitUtils {

    private final By addToCartButton = By.id("button-cart");
    private final By quantityInput   = By.id("input-quantity");
    private final By productName     = By.cssSelector("#content h1");
    private final By productPrice    = By.cssSelector("#content .price");
    private final By successAlert    = By.cssSelector(".alert-success");
    private final By cartTotal       = By.id("cart-total");

    // Locators for TC-14
    private final By descriptionTab  = By.cssSelector("a[href='#tab-description']");
    private final By productImage     = By.cssSelector("ul.thumbnails li a.thumbnail img");
    private final By wishlistButton   = By.cssSelector("button[onclick*='wishlist.add']");
    private final By compareButton    = By.cssSelector("button[onclick*='compare.add']");
    private final By reviewsTab       = By.cssSelector("a[href='#tab-review']");
    private final By priceHeader      = By.xpath("//div[@id='content']//h2[contains(text(), '$')]");


    // ==================== Constructor ====================
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    // ==================== Navigation ====================

    public ProductPage navigateToProduct(int productId) {
        driver.get("https://awesomeqa.com/ui/index.php?route=product/product&product_id=" + productId);
        waitForElementToBeVisible(productName);
        return this;
    }

    // ==================== Actions ====================

    public ProductPage setQuantity(int quantity) {
        WebElement qtyInput = waitForElementToBeClickable(quantityInput);
        qtyInput.clear();
        qtyInput.sendKeys(String.valueOf(quantity));
        return this;
    }


    public ProductPage addToCart() {

        String beforeCartText = "";
        try {
            beforeCartText = driver.findElement(cartTotal).getText();
        } catch (Exception ignored) {}

        // Click the button via JavaScript to avoid overlay/intercept issues
        org.openqa.selenium.WebElement btn = waitForElementToBeClickable(addToCartButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

        final String before = beforeCartText;
        WebDriverWait cartWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            cartWait.until(driver1 -> {
                String current = driver1.findElement(By.id("cart-total")).getText();
                return !current.equals(before);
            });
        } catch (Exception ignored) {
            try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        return this;
    }

    public ProductPage addToCart(int quantity) {
        setQuantity(quantity);
        addToCart();
        return this;
    }

    // ==================== Verification ====================

    public boolean isSuccessAlertDisplayed() {
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement alert = alertWait.until(
                    ExpectedConditions.visibilityOfElementLocated(successAlert));
            return alert.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getProductName() {
        return waitForElementToBeVisible(productName).getText();
    }

    public String getProductPrice() {
        return waitForElementToBeVisible(productPrice).getText();
    }

    public String getCartTotal() {
        return waitForElementToBeVisible(cartTotal).getText();
    }


    public ProductPage addToCartFromHomePage(int productId) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("cart.add('" + productId + "');");
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }

    public boolean isProductNameDisplayed() {
        return waitForElementToBeVisible(productName).isDisplayed();
    }

    public boolean isProductPriceDisplayed() {
        return waitForElementToBeVisible(priceHeader).isDisplayed();
    }

    public boolean isDescriptionTabDisplayed() {
        return waitForElementToBeVisible(descriptionTab).isDisplayed();
    }

    public boolean isProductImageDisplayed() {
        return waitForElementToBeVisible(productImage).isDisplayed();
    }

    public boolean isAddToCartButtonDisplayed() {
        return waitForElementToBeVisible(addToCartButton).isDisplayed();
    }

    public boolean isWishlistButtonDisplayed() {
        return waitForElementToBeVisible(wishlistButton).isDisplayed();
    }

    public boolean isCompareButtonDisplayed() {
        return waitForElementToBeVisible(compareButton).isDisplayed();
    }

    public boolean isReviewsTabDisplayed() {
        return waitForElementToBeVisible(reviewsTab).isDisplayed();
    }
}
