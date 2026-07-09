package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class ForgottenPasswordPage {
    WebDriver driver;
    WaitUtils waitUtils;

    public ForgottenPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void enterEmail(String email) {
        driver.findElement(By.id("input-email")).clear();
        waitUtils.waitForElementToBeVisible(By.id("input-email")).sendKeys(email);
    }

    public void clickContinue() {
        waitUtils.waitForElementToBeClickable(By.cssSelector("input.btn-primary")).click();
    }
}