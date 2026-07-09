package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;
public class ContactUsPage {
    WebDriver driver;
    WaitUtils waitUtils;

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void fillContactForm(String name, String email, String enquiry) {
        driver.findElement(By.id("input-name")).sendKeys(name);
        driver.findElement(By.id("input-email")).sendKeys(email);
        driver.findElement(By.id("input-enquiry")).sendKeys(enquiry);
    }

    public void clickSubmitButton() {
        waitUtils.waitForElementToBeClickable(By.cssSelector("input[type='submit']")).click();
    }
// secure page
    public String getSuccessMessage() {
        return waitUtils.waitForElementToBeVisible(By.cssSelector("#content p")).getText();
    }
}