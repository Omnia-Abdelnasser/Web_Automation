package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;
public class LoginPage {
    WebDriver driver;
    WaitUtils waitUtils;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void enterMyAccount() {
        waitUtils.waitForElementToBeClickable(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[1]")).click();
    }

    public void enterLogin() {
        waitUtils.waitForElementToBeClickable(By.linkText("Login")).click();
    }

    public void clickForgottenPassword() {
        waitUtils.waitForElementToBeClickable(By.linkText("Forgotten Password")).click();
    }

    public void enterEmail(String Email) {
        driver.findElement(By.id("input-email")).sendKeys(Email);
    }

    public void enterPassword(String Password) {
        driver.findElement(By.id("input-password")).sendKeys(Password);
    }
    public void clickLoginButton() {
        waitUtils.waitForElementToBeClickable(By.cssSelector("input[type='submit']")).click();
    }
       public String getAlertText() {
        return waitUtils.waitForElementToBeVisible(By.cssSelector(".alert.alert-success")).getText();
    }

    //secure page
    public boolean getSuccessMessage(String expectedMessage)  {
        return driver.getCurrentUrl().contains(expectedMessage);
    }
    public String getErrorMessage() {
        return driver.findElement(By.cssSelector(".alert.alert-danger.alert-dismissible")).getText();
    }

}
