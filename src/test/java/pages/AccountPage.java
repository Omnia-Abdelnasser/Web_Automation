package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.Config;
import utils.WaitUtils;

public class AccountPage extends WaitUtils {

    // Edit Account
    private final By firstNameField    = By.id("input-firstname");
    private final By lastNameField     = By.id("input-lastname");
    private final By emailField        = By.id("input-email");
    private final By telephoneField    = By.id("input-telephone");
    private final By continueButton    = By.cssSelector("input[type='submit']");

    // Change Password
    private final By passwordField     = By.id("input-password");
    private final By confirmField      = By.id("input-confirm");

    // Newsletter
    private final By subscribeRadio    = By.cssSelector("input[name='newsletter'][value='1']");
    private final By unsubscribeRadio  = By.cssSelector("input[name='newsletter'][value='0']");

    // Address Book
    private final By addrFirstName     = By.id("input-firstname");
    private final By addrLastName      = By.id("input-lastname");
    private final By addrCompany       = By.id("input-company");
    private final By addrAddress1      = By.id("input-address-1");
    private final By addrCity          = By.id("input-city");
    private final By addrPostCode      = By.id("input-postcode");
    private final By addrCountry       = By.id("input-country");
    private final By addrZone          = By.id("input-zone");

    // Success / Alert messages
    private final By successAlert      = By.cssSelector(".alert-success");
    private final By contentHeader     = By.cssSelector("#content h2");
    private final By pageH1            = By.cssSelector("#content h1");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    // ==================== Navigation ====================

    public AccountPage navigateToEditAccount() {
        driver.get(Config.baseUrl + "index.php?route=account/edit");
        return this;
    }

    public AccountPage navigateToChangePassword() {
        driver.get(Config.baseUrl + "index.php?route=account/password");
        return this;
    }

    public AccountPage navigateToNewsletter() {
        driver.get(Config.baseUrl + "index.php?route=account/newsletter");
        return this;
    }

    public AccountPage navigateToAddNewAddress() {
        driver.get(Config.baseUrl + "index.php?route=account/address/add");
        return this;
    }

    public AccountPage navigateToOrderHistory() {
        driver.get(Config.baseUrl + "index.php?route=account/order");
        return this;
    }

    public AccountPage waitForAccountPageToLoad() {
        waitForElementToBeVisible(contentHeader);
        return this;
    }

    // ==================== Edit Account ====================

    public AccountPage clearAndEnterFirstName(String firstName) {
        WebElement el = waitForElementToBeVisible(firstNameField);
        el.clear();
        el.sendKeys(firstName);
        return this;
    }

    public AccountPage clearAndEnterLastName(String lastName) {
        WebElement el = waitForElementToBeVisible(lastNameField);
        el.clear();
        el.sendKeys(lastName);
        return this;
    }

    public AccountPage clearAndEnterEmail(String email) {
        WebElement el = waitForElementToBeVisible(emailField);
        el.clear();
        el.sendKeys(email);
        return this;
    }

    public AccountPage clearAndEnterTelephone(String telephone) {
        WebElement el = waitForElementToBeVisible(telephoneField);
        el.clear();
        el.sendKeys(telephone);
        return this;
    }

    public AccountPage clickContinue() {
        waitForElementToBeClickable(continueButton).click();
        return this;
    }

    // ==================== Change Password ====================

    public AccountPage enterPassword(String password) {
        waitForElementToBeVisible(passwordField).sendKeys(password);
        return this;
    }

    public AccountPage enterConfirmPassword(String confirmPassword) {
        waitForElementToBeVisible(confirmField).sendKeys(confirmPassword);
        return this;
    }

    // ==================== Newsletter ====================

    public AccountPage clickSubscribe() {
        waitForElementToBeClickable(subscribeRadio).click();
        return this;
    }

    public AccountPage clickUnsubscribe() {
        waitForElementToBeClickable(unsubscribeRadio).click();
        return this;
    }

    // ==================== Address Book ====================

    public AccountPage enterAddressFirstName(String firstName) {
        WebElement el = waitForElementToBeVisible(addrFirstName);
        el.clear();
        el.sendKeys(firstName);
        return this;
    }

    public AccountPage enterAddressLastName(String lastName) {
        WebElement el = waitForElementToBeVisible(addrLastName);
        el.clear();
        el.sendKeys(lastName);
        return this;
    }

    public AccountPage enterAddressAddress1(String address) {
        WebElement el = waitForElementToBeVisible(addrAddress1);
        el.clear();
        el.sendKeys(address);
        return this;
    }

    public AccountPage enterAddressCity(String city) {
        WebElement el = waitForElementToBeVisible(addrCity);
        el.clear();
        el.sendKeys(city);
        return this;
    }

    public AccountPage enterAddressPostCode(String postCode) {
        WebElement el = waitForElementToBeVisible(addrPostCode);
        el.clear();
        el.sendKeys(postCode);
        return this;
    }

    public AccountPage selectAddressCountry(String country) {
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(d -> {
                try {
                    org.openqa.selenium.WebElement dropdown = d.findElement(addrCountry);
                    return dropdown.isEnabled();
                } catch (Exception e) {
                    return false;
                }
            });
        org.openqa.selenium.WebElement dropdown = waitForElementToBeClickable(addrCountry);
        new Select(dropdown).selectByVisibleText(country);
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }

    public AccountPage selectAddressZone(String zone) {
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(d -> {
                try {
                    org.openqa.selenium.WebElement dropdown = d.findElement(addrZone);
                    Select select = new Select(dropdown);
                    for (org.openqa.selenium.WebElement option : select.getOptions()) {
                        if (option.getText().equalsIgnoreCase(zone)) {
                            return true;
                        }
                    }
                } catch (Exception e) {}
                return false;
            });
        org.openqa.selenium.WebElement dropdown = waitForElementToBeClickable(addrZone);
        new Select(dropdown).selectByVisibleText(zone);
        return this;
    }

    // ==================== Verification ====================

    public String getSuccessMessage() {
        return waitForElementToBeVisible(successAlert).getText();
    }

    public String getOrderHistoryHeader() {
        return waitForElementToBeVisible(pageH1).getText();
    }
}
