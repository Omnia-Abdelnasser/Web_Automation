package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

import java.util.List;

public class CartPage extends WaitUtils {

    // ==================== Locators ====================
    // The main cart table is inside a form inside #content
    private final By cartTable      = By.cssSelector("#content form .table-responsive table.table-bordered");
    private final By cartRows       = By.cssSelector("#content form .table-responsive table.table-bordered tbody tr");
    private final By checkoutButton = By.cssSelector("a.btn.btn-primary[href*='checkout/checkout']");
    private final By emptyCartMsg   = By.cssSelector("#content p");
    private final By cartTotalHeader = By.id("cart-total");

    // ==================== Constructor ====================
    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ==================== Navigation ====================

    public CartPage navigateToCart() {
        driver.get("https://awesomeqa.com/ui/index.php?route=checkout/cart");
        // Wait for page to load - either the table or the empty message
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }

    // ==================== Verification ====================

    public boolean isCartNotEmpty() {
        try {
            // First check: is there an empty cart message?
            List<WebElement> messages = driver.findElements(emptyCartMsg);
            for (WebElement msg : messages) {
                if (msg.getText().contains("empty")) {
                    return false;
                }
            }
            // Second check: is the cart table present?
            List<WebElement> tables = driver.findElements(cartTable);
            if (!tables.isEmpty() && tables.get(0).isDisplayed()) {
                return true;
            }
            // Third check: check cart rows directly
            List<WebElement> rows = driver.findElements(cartRows);
            return !rows.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if the empty cart message is displayed.
     */
    public boolean isCartEmpty() {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(d -> {
                    List<WebElement> messages = d.findElements(emptyCartMsg);
                    for (WebElement msg : messages) {
                        if (msg.getText().toLowerCase().contains("empty")) {
                            return true;
                        }
                    }
                    return false;
                });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getCartItemCount() {
        try {
            return driver.findElements(cartRows).size();
        } catch (Exception e) {
            return 0;
        }
    }


    public boolean isProductInCart(String productName) {
        try {
            List<WebElement> rows = driver.findElements(cartRows);
            for (WebElement row : rows) {
                List<WebElement> links = row.findElements(By.cssSelector("td a"));
                for (WebElement link : links) {
                    if (link.getText().toLowerCase().contains(productName.toLowerCase())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            // swallow
        }
        return false;
    }

    public String getProductNameInRow(int rowIndex) {
        List<WebElement> rows = driver.findElements(cartRows);
        if (rowIndex < rows.size()) {
            try {
                return rows.get(rowIndex).findElement(By.cssSelector("td:nth-child(2) a")).getText();
            } catch (Exception e) { return ""; }
        }
        return "";
    }

    public String getQuantityInRow(int rowIndex) {
        int retries = 5;
        while (retries > 0) {
            try {
                List<WebElement> rows = driver.findElements(cartRows);
                if (rowIndex < rows.size()) {
                    return rows.get(rowIndex).findElement(By.cssSelector("input.form-control")).getAttribute("value");
                }
            } catch (Exception e) {}
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            retries--;
        }
        return "";
    }

    // ==================== Actions ====================

    public CartPage updateQuantity(int rowIndex, int newQuantity) {
        List<WebElement> rows = driver.findElements(cartRows);
        if (rowIndex < rows.size()) {
            WebElement qtyInput = rows.get(rowIndex).findElement(By.cssSelector("input.form-control"));
            
            // Set value via JavaScript to be 100% robust and prevent empty value removal
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];", qtyInput, String.valueOf(newQuantity));
            
            WebElement updateBtn = rows.get(rowIndex).findElement(By.cssSelector("button.btn-primary"));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", updateBtn);
            
            // Wait for quantity value to update in DOM
            try {
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(d -> {
                        try {
                            List<WebElement> currentRows = d.findElements(cartRows);
                            if (rowIndex < currentRows.size()) {
                                String val = currentRows.get(rowIndex).findElement(By.cssSelector("input.form-control")).getAttribute("value");
                                return val.equals(String.valueOf(newQuantity));
                            }
                        } catch (Exception e) {}
                        return false;
                    });
            } catch (Exception ignored) {
                try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }
        return this;
    }

    public CartPage removeProduct(int rowIndex) {
        List<WebElement> rows = driver.findElements(cartRows);
        if (rowIndex < rows.size()) {
            int beforeCount = rows.size();
            WebElement removeBtn = rows.get(rowIndex).findElement(By.cssSelector("button.btn-danger"));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", removeBtn);
            
            // Wait for the row count to decrease or cart to be empty
            try {
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(d -> d.findElements(cartRows).size() < beforeCount || isCartEmpty());
            } catch (Exception ignored) {
                try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }
        return this;
    }

    public CartPage clickCheckout() {
        waitForElementToBeClickable(checkoutButton).click();
        return this;
    }

    public String getCartHeaderTotal() {
        return waitForElementToBeVisible(cartTotalHeader).getText();
    }

    public boolean hasStockWarning() {
        try {
            List<WebElement> alerts = driver.findElements(By.cssSelector(".alert-danger"));
            for (WebElement alert : alerts) {
                if (alert.getText().contains("not in stock") || alert.getText().contains("***")) {
                    return true;
                }
            }
            List<WebElement> rows = driver.findElements(cartRows);
            for (WebElement row : rows) {
                if (row.getText().contains("***")) {
                    return true;
                }
            }
        } catch (Exception ignored) {}
        return false;
    }

    public CartPage clearCart() {
        int maxRetries = 10;
        while (isCartNotEmpty() && maxRetries > 0) {
            int prevCount = getCartItemCount();
            if (prevCount == 0) {
                break;
            }
            removeProduct(0);
            maxRetries--;
            // If the item count did not decrease, break to avoid looping forever
            if (getCartItemCount() == prevCount) {
                break;
            }
        }
        return this;
    }

    // ==================== TC-17: Coupon Code ====================

    public CartPage expandCouponSection() {
        try {
            org.openqa.selenium.WebElement el = waitForElementToBeClickable(By.partialLinkText("Use Coupon Code"));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        } catch (Exception ignored) {}
        return this;
    }

    public CartPage enterCouponCode(String code) {
        org.openqa.selenium.WebElement input = waitForElementToBeVisible(By.id("input-coupon"));
        input.clear();
        input.sendKeys(code);
        return this;
    }

    public CartPage clickApplyCoupon() {
        waitForElementToBeClickable(By.id("button-coupon")).click();
        try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }

    public String getCouponWarningMessage() {
        return waitForElementToBeVisible(By.cssSelector(".alert-danger, .alert-warning")).getText();
    }

    public boolean hasShippingEstimateSection() {
        try {
            return !driver.findElements(org.openqa.selenium.By.partialLinkText("Estimate Shipping")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== TC-18: Estimate Shipping ====================

    public CartPage expandShippingEstimateSection() {
        try {
            org.openqa.selenium.WebElement el = waitForElementToBeClickable(By.partialLinkText("Estimate Shipping"));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        } catch (Exception ignored) {}
        return this;
    }

    public CartPage selectShippingCountry(String country) {
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(d -> {
                try {
                    org.openqa.selenium.WebElement dropdown = d.findElement(By.id("input-country"));
                    return dropdown.isEnabled();
                } catch (Exception e) {
                    return false;
                }
            });
        org.openqa.selenium.WebElement dropdown = waitForElementToBeClickable(By.id("input-country"));
        new org.openqa.selenium.support.ui.Select(dropdown).selectByVisibleText(country);
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }

    public CartPage selectShippingRegion(String region) {
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(d -> {
                try {
                    org.openqa.selenium.WebElement dropdown = d.findElement(By.id("input-zone"));
                    org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(dropdown);
                    for (org.openqa.selenium.WebElement option : select.getOptions()) {
                        if (option.getText().equalsIgnoreCase(region)) {
                            return true;
                        }
                    }
                } catch (Exception e) {}
                return false;
            });
        org.openqa.selenium.WebElement dropdown = waitForElementToBeClickable(By.id("input-zone"));
        new org.openqa.selenium.support.ui.Select(dropdown).selectByVisibleText(region);
        return this;
    }

    public CartPage enterShippingPostCode(String postCode) {
        org.openqa.selenium.WebElement input = waitForElementToBeVisible(By.id("input-postcode"));
        input.clear();
        input.sendKeys(postCode);
        return this;
    }

    public CartPage clickGetQuotes() {
        waitForElementToBeClickable(By.id("button-quote")).click();
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }

    public CartPage selectFirstShippingMethod() {
        org.openqa.selenium.WebElement radio = waitForElementToBeClickable(org.openqa.selenium.By.cssSelector("input[name='shipping_method']"));
        if (!radio.isSelected()) {
            radio.click();
        }
        return this;
    }

    public CartPage clickApplyShipping() {
        waitForElementToBeClickable(org.openqa.selenium.By.id("button-shipping")).click();
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }

    public boolean isSuccessAlertDisplayed() {
        try {
            return waitForElementToBeVisible(org.openqa.selenium.By.cssSelector(".alert-success")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isShippingResultsDisplayed() {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(d -> {
                    List<WebElement> elements = d.findElements(By.cssSelector("input[name='shipping_method'], #shipping-quote input[type='radio']"));
                    return !elements.isEmpty() && elements.get(0).isDisplayed();
                });
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
