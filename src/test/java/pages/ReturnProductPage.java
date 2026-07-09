package pages;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;
import org.openqa.selenium.By;

public class ReturnProductPage  {
    WebDriver driver;
    WaitUtils waitUtils;

    public ReturnProductPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);

    }


    public void enterFirstName(String firstName) {
        driver.findElement(By.id("input-firstname")).sendKeys(firstName);
    }
    public void enterLastName(String lastName) {
        driver.findElement(By.id("input-lastname")).sendKeys(lastName);
    }
    public void enterEmail(String email) {
        driver.findElement(By.id("input-email")).sendKeys(email);
    }
    public void enterTelephone(String telephone) {
        driver.findElement(By.id("input-telephone")).sendKeys(telephone);
    }

    public void enterOrderId(String orderId) {
        driver.findElement(By.id("input-order-id")).sendKeys(orderId);
    }
    public void enterOrderDate(String orderDate) {
        driver.findElement(By.id("input-date-ordered")).sendKeys(orderDate);
    }
    public void enterProductName(String productName) {
        driver.findElement(By.id("input-product")).sendKeys(productName);
    }
    public void enterProductCode(String productCode) {
        driver.findElement(By.id("input-model")).sendKeys(productCode);
    }

    public void selectReasonForReturn(String reason) {
        if (reason.equalsIgnoreCase("Dead On Arrival")) {
            waitUtils.waitForElementToBeClickable(By.cssSelector("input[type='radio'][name='return_reason_id'][value='1']")).click();
        } else if (reason.equalsIgnoreCase("Faulty, please supply details")) {
            waitUtils.waitForElementToBeClickable(By.cssSelector("input[type='radio'][name='return_reason_id'][value='2']")).click();
        } else if (reason.equalsIgnoreCase("Order Error")) {
            waitUtils.waitForElementToBeClickable(By.cssSelector("input[type='radio'][name='return_reason_id'][value='3']")).click();
        } else if (reason.equalsIgnoreCase("Received Wrong Item")) {
            waitUtils.waitForElementToBeClickable(By.cssSelector("input[type='radio'][name='return_reason_id'][value='4']")).click();
        } else if (reason.equalsIgnoreCase("Other, please supply details")) {
            waitUtils.waitForElementToBeClickable(By.cssSelector("input[type='radio'][name='return_reason_id'][value='5']")).click();
        }
    }
        public void selectProductOpened ( boolean isOpened){
            if (isOpened) {
                waitUtils.waitForElementToBeClickable(By.cssSelector("input[type='radio'][name='opened'][value='1']")).click();
            } else {
                waitUtils.waitForElementToBeClickable(By.cssSelector("input[type='radio'][name='opened'][value='0']")).click();
            }
        }
        public void enterFaultyDetails(String details) {
            driver.findElement(By.id("input-comment")).sendKeys(details);
        }
        public void clickSubmitButton() {
            waitUtils.waitForElementToBeClickable(By.cssSelector("input[type='submit'][value='Submit']")).click();
        }
        //secure page
        public String getSuccessMessage() {
            return waitUtils.waitForElementToBeVisible(By.cssSelector("#content p")).getText();
        }



}
