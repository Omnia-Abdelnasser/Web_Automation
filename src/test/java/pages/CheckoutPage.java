package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;

import java.time.Duration;

public class CheckoutPage extends WaitUtils {

    private final WebDriverWait longWait;

    private final By checkoutOptionPanel = By.id("collapse-checkout-option");
    private final By guestRadio          = By.cssSelector("input[name='account'][value='guest']");
    private final By buttonAccount       = By.id("button-account");


    private final By paymentAddressPanel  = By.id("collapse-payment-address");
    private final By inputFirstName       = By.id("input-payment-firstname");
    private final By inputLastName        = By.id("input-payment-lastname");
    private final By inputEmail           = By.id("input-payment-email");
    private final By inputTelephone       = By.id("input-payment-telephone");
    private final By inputAddress1        = By.id("input-payment-address-1");
    private final By inputCity            = By.id("input-payment-city");
    private final By inputPostCode        = By.id("input-payment-postcode");
    private final By selectCountry        = By.id("input-payment-country");
    private final By selectZone           = By.id("input-payment-zone");
    private final By buttonPaymentAddress = By.id("button-guest");

    private final By shippingAddressPanel  = By.id("collapse-shipping-address");
    private final By buttonShippingAddress = By.id("button-shipping-address");

    private final By shippingMethodPanel  = By.id("collapse-shipping-method");
    private final By buttonShippingMethod = By.id("button-shipping-method");

    private final By paymentMethodPanel  = By.id("collapse-payment-method");
    private final By codRadio            = By.cssSelector("input[name='payment_method'][value='cod']");
    private final By agreeCheckbox       = By.cssSelector("input[name='agree']");
    private final By buttonPaymentMethod = By.id("button-payment-method");

    private final By confirmPanel  = By.id("collapse-checkout-confirm");
    private final By confirmTable  = By.cssSelector("#collapse-checkout-confirm .table");
    private final By buttonConfirm = By.id("button-confirm");

    private final By successTitle   = By.cssSelector("#content h1");
    private final By successMessage = By.cssSelector("#content p");
    private final By continueButton = By.cssSelector("#content a.btn.btn-primary");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.longWait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }


    public CheckoutPage navigateToCheckout() {
        // Navigate via cart page to ensure the checkout flow initializes correctly
        driver.get("https://awesomeqa.com/ui/index.php?route=checkout/cart");
        pause(1500);
        // Click the Checkout button on the cart page
        try {
            WebElement checkoutBtn = longWait.until(
                ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a[href*='checkout/checkout']"))
            );
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutBtn);
        } catch (Exception e) {
            // Fallback: navigate directly
            driver.get("https://awesomeqa.com/ui/index.php?route=checkout/checkout");
        }
        pause(2000);
        // The checkout-option panel shows for guests; logged-in users may skip directly to payment.
        // We accept either scenario as long as the page loaded.
        try {
            longWait.until(ExpectedConditions.visibilityOfElementLocated(checkoutOptionPanel));
        } catch (Exception e) {
            // Panel not shown — site skipped to a later step; that's acceptable.
            System.out.println("checkout-option panel not found; site may have skipped to billing/payment step.");
        }
        return this;
    }


    public CheckoutPage selectGuestCheckout() {
        // Try to interact with the checkout options panel (guest vs. register)
        // If the panel is not visible, the site may have skipped directly to billing.
        try {
            longWait.until(ExpectedConditions.visibilityOfElementLocated(checkoutOptionPanel));
            // Click Guest radio via JS (avoids intercept issues)
            jsClick(guestRadio);
            pause(500);
            // Click Continue (#button-account) via JS
            jsClick(buttonAccount);
        } catch (Exception e) {
            System.out.println("Checkout option panel not found; skipping guest selection step.");
        }

        // Wait for billing form to appear (AJAX-injected)
        longWait.until(ExpectedConditions.visibilityOfElementLocated(inputFirstName));
        pause(500);
        return this;
    }

    // ==================== Step 2: Billing Details ====================

    public CheckoutPage fillBillingDetails(String firstName, String lastName, String email,
                                            String telephone, String address1, String city,
                                            String postCode, String country, String zone) {
        // Confirm form is ready
        longWait.until(ExpectedConditions.visibilityOfElementLocated(inputFirstName));

        clearAndType(inputFirstName, firstName);
        clearAndType(inputLastName, lastName);

        // Email field exists in guest checkout
        fillIfPresent(inputEmail, email);

        clearAndType(inputTelephone, telephone);
        clearAndType(inputAddress1, address1);
        clearAndType(inputCity, city);
        clearAndType(inputPostCode, postCode);

        // Country dropdown (triggers zone AJAX reload)
        selectDropdownByVisibleText(selectCountry, country);
        pause(3000); // wait for zone AJAX

        // Zone dropdown (loaded after country selection)
        longWait.until(ExpectedConditions.elementToBeClickable(selectZone));
        selectDropdownByVisibleText(selectZone, zone);
        pause(500);

        return this;
    }

    public CheckoutPage clickContinueBilling() {
        // The billing Continue button is AJAX-injected - wait for it to be present in DOM
        longWait.until(ExpectedConditions.presenceOfElementLocated(buttonPaymentAddress));
        // Then wait for it to be clickable (visible + enabled)
        longWait.until(ExpectedConditions.elementToBeClickable(buttonPaymentAddress));
        scrollAndJsClick(buttonPaymentAddress);
        pause(3000);
        return this;
    }

    // ==================== Step 3: Shipping Address ====================

    public CheckoutPage clickContinueShippingAddress() {
        longWait.until(ExpectedConditions.visibilityOfElementLocated(shippingAddressPanel));
        pause(1000);
        scrollAndJsClick(buttonShippingAddress);
        pause(3000);
        return this;
    }

    // ==================== Step 4: Shipping Method ====================

    public CheckoutPage selectFlatShippingAndContinue() {
        longWait.until(ExpectedConditions.visibilityOfElementLocated(shippingMethodPanel));
        pause(1000);
        scrollAndJsClick(buttonShippingMethod);
        pause(3000);
        return this;
    }

    // ==================== Step 3: Payment Method ====================

    /**
     * Handles the Payment Method step.
     * On this store there are no payment options, so we just:
     * 1. Agree to Terms & Conditions
     * 2. Click Continue
     */
    public CheckoutPage agreeAndContinuePayment() {
        longWait.until(ExpectedConditions.visibilityOfElementLocated(paymentMethodPanel));
        pause(1500);

        // Try to select COD if available (may not be) - FAST CHECK
        try {
            java.util.List<WebElement> codRadios = driver.findElements(codRadio);
            if (!codRadios.isEmpty()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", codRadios.get(0));
            }
        } catch (Exception ignored) {}

        // Agree to Terms & Conditions checkbox
        try {
            By specificAgree = By.cssSelector("#collapse-payment-method input[name='agree']");
            WebElement agree = longWait.until(
                    ExpectedConditions.presenceOfElementLocated(specificAgree));
            
            // Scroll to it so we can see it
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", agree);
            pause(500);

            if (!agree.isSelected()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", agree);
                System.out.println("Clicked Terms & Conditions checkbox.");
                pause(1000);
            }
        } catch (Exception e) {
            System.out.println("Could not click agree checkbox: " + e.getMessage());
        }

        // Click Continue button
        By specificContinue = By.cssSelector("#collapse-payment-method #button-payment-method");
        WebElement continueBtn = longWait.until(ExpectedConditions.elementToBeClickable(specificContinue));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);
        System.out.println("Clicked Payment Method Continue button.");
        
        // Fast-fail check: If the site throws a "Payment method required!" warning, don't wait 25 seconds.
        pause(1000); // give the alert a moment to appear
        try {
            java.util.List<WebElement> alerts = driver.findElements(By.cssSelector(".alert-danger"));
            for (WebElement alert : alerts) {
                if (alert.isDisplayed() && alert.getText().contains("Payment method required")) {
                    throw new RuntimeException("FAST FAIL: Site blocked checkout because no payment methods are available (Warning: Payment method required!).");
                }
            }
        } catch (RuntimeException re) {
            throw re; // rethrow the fast fail
        } catch (Exception ignored) {}

        return this;
    }

    public CheckoutPage selectCODPaymentAndContinue() {
        return agreeAndContinuePayment();
    }

    // ==================== Step 6: Confirm ====================

    public CheckoutPage confirmOrder() {
        longWait.until(ExpectedConditions.visibilityOfElementLocated(confirmPanel));
        pause(1000);
        scrollAndJsClick(buttonConfirm);
        pause(5000);
        return this;
    }

    public boolean isConfirmTableVisible() {
        try {
            return longWait.until(
                    ExpectedConditions.visibilityOfElementLocated(confirmTable)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== Success Page ====================

    public boolean isOnSuccessPage() {
        return driver.getCurrentUrl().contains("checkout/success");
    }

    public boolean isOrderSuccessPageDisplayed() {
        try {
            WebElement title = longWait.until(
                    ExpectedConditions.visibilityOfElementLocated(successTitle));
            return title.getText().contains("Your order has been placed!");
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessTitle() {
        return longWait.until(
                ExpectedConditions.visibilityOfElementLocated(successTitle)).getText();
    }

    public String getSuccessMessage() {
        try { return driver.findElement(successMessage).getText(); }
        catch (Exception e) { return ""; }
    }

    public CheckoutPage clickContinueOnSuccess() {
        waitForElementToBeClickable(continueButton).click();
        return this;
    }

    // ==================== Helpers ====================

    private void clearAndType(By locator, String text) {
        WebElement el = longWait.until(ExpectedConditions.elementToBeClickable(locator));
        el.clear();
        el.sendKeys(text);
    }

    /** Fill the field only if it exists and is visible. */
    private void fillIfPresent(By locator, String text) {
        try {
            WebElement el = driver.findElement(locator);
            if (el.isDisplayed()) {
                el.clear();
                el.sendKeys(text);
            }
        } catch (Exception ignored) {}
    }

    private void selectDropdownByVisibleText(By locator, String text) {
        WebElement dropdown = longWait.until(ExpectedConditions.elementToBeClickable(locator));
        new Select(dropdown).selectByVisibleText(text);
    }

    /** Click element via JavaScript (bypasses overlay/animation issues). */
    private void jsClick(By locator) {
        WebElement el = longWait.until(
                ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    /** Scroll element into center view then JS click. */
    private void scrollAndJsClick(By locator) {
        WebElement el = longWait.until(
                ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);
        pause(300);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    private void pause(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
