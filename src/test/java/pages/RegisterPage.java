
package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;
public class RegisterPage {
    WebDriver driver;
    WaitUtils waitUtils;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }
    public void enterMyAccount(){
        waitUtils.waitForElementToBeClickable(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[1]")).click();
    }

    public void enterRegister(){
        waitUtils.waitForElementToBeClickable(By.linkText("Register")).click();
    }

    public void enterFirstName(String FirstName){
        driver.findElement(By.id("input-firstname")).sendKeys(FirstName);
    }
    public void enterLastName(String LastName){
        driver.findElement(By.id("input-lastname")).sendKeys(LastName);
    }
    public void enterEmail(String Email){
        driver.findElement(By.id("input-email")).sendKeys(Email);
    }
    public void enterTelephone(String Telephone){
        driver.findElement(By.id("input-telephone")).sendKeys(Telephone);
    }
    public void enterPassword(String Password){
        driver.findElement(By.id("input-password")).sendKeys(Password);
    }
    public void enterConfirmPassword(String ConfirmPassword){
        driver.findElement(By.id("input-confirm")).sendKeys(ConfirmPassword);
    }
    public void radioButton(){
        waitUtils.waitForElementToBeClickable(By.xpath("//*[@id=\"content\"]/form/fieldset[3]/div/div/label[2]")).click();
    }
    public void checkBox(){
        waitUtils.waitForElementToBeClickable(By.cssSelector("input[type='checkbox'][name='agree']")).click();
    }
    public void clickContinueButton(){
        waitUtils.waitForElementToBeClickable(By.cssSelector("input[type='submit']"))
                .click();
    }

    // secure page
    public String getSuccessMessage() {
        return waitUtils.waitForElementToBeVisible(By.cssSelector("#content h1")).getText();
    }

    public String getErrorMessage() {
        return waitUtils.waitForElementToBeVisible(By.cssSelector(".text-danger")).getText();
    }
    public String getExistingEmailErrorMessage() {
        return waitUtils.waitForElementToBeVisible(By.cssSelector(".alert.alert-danger.alert-dismissible")).getText();
    }
    public String getMismatchedPasswordErrorMessage() {
        return waitUtils.waitForElementToBeVisible(By.cssSelector(".text-danger")).getText();
    }
    public String getNotAgreedTermsErrorMessage() {
        return waitUtils.waitForElementToBeVisible(By.cssSelector(".alert.alert-danger.alert-dismissible")).getText();
    }

}
