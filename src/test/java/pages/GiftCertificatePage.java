package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Config;
import utils.WaitUtils;

public class GiftCertificatePage extends WaitUtils {

    private final By recipientNameField  = By.id("input-to-name");
    private final By recipientEmailField = By.id("input-to-email");
    private final By senderNameField     = By.id("input-from-name");
    private final By senderEmailField    = By.id("input-from-email");
    private final By messageField        = By.id("input-message");
    private final By amountField         = By.id("input-amount");
    private final By agreeCheckbox       = By.cssSelector("input[name='agree']");
    private final By continueButton      = By.cssSelector("input[type='submit']");
    private final By successAlert        = By.cssSelector(".alert-success");
    private final By voucherThemeFirst   = By.cssSelector("input[name='voucher_theme_id']");

    public GiftCertificatePage(WebDriver driver) {
        super(driver);
    }

    public GiftCertificatePage navigateToGiftCertificate() {
        driver.get(Config.baseUrl + "index.php?route=account/voucher");
        return this;
    }

    public GiftCertificatePage enterRecipientName(String name) {
        WebElement el = waitForElementToBeVisible(recipientNameField);
        el.clear();
        el.sendKeys(name);
        return this;
    }

    public GiftCertificatePage enterRecipientEmail(String email) {
        WebElement el = waitForElementToBeVisible(recipientEmailField);
        el.clear();
        el.sendKeys(email);
        return this;
    }

    public GiftCertificatePage enterSenderName(String name) {
        try {
            WebElement el = driver.findElement(senderNameField);
            el.clear();
            el.sendKeys(name);
        } catch (Exception ignored) {}
        return this;
    }

    public GiftCertificatePage enterSenderEmail(String email) {
        try {
            WebElement el = driver.findElement(senderEmailField);
            el.clear();
            el.sendKeys(email);
        } catch (Exception ignored) {}
        return this;
    }

    public GiftCertificatePage selectFirstVoucherTheme() {
        try {
            waitForElementToBeClickable(voucherThemeFirst).click();
        } catch (Exception ignored) {}
        return this;
    }

    public GiftCertificatePage enterMessage(String message) {
        try {
            WebElement el = driver.findElement(messageField);
            el.clear();
            el.sendKeys(message);
        } catch (Exception ignored) {}
        return this;
    }

    public GiftCertificatePage enterAmount(String amount) {
        WebElement el = waitForElementToBeVisible(amountField);
        el.clear();
        el.sendKeys(amount);
        return this;
    }

    public GiftCertificatePage clickAgree() {
        waitForElementToBeClickable(agreeCheckbox).click();
        return this;
    }

    public GiftCertificatePage clickContinue() {
        waitForElementToBeClickable(continueButton).click();
        return this;
    }

    public String getSuccessMessage() {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("voucher/success"));
            return "Success";
        } catch (Exception e) {
            try {
                return waitForElementToBeVisible(successAlert).getText();
            } catch (Exception ex) {
                return "Failed";
            }
        }
    }

    public boolean isOnGiftCertificatePage() {
        return driver.getCurrentUrl().contains("route=account/voucher");
    }
}
