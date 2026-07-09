package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.LoginPage;

@Listeners(ExtentListener.class)
public class auto_8_LoggedUserCategoryTest extends BaseTest {

    private LoginPage loginPage;
    private AccountPage accountPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
    }

    @Test(description = "TC-08 - Logged User Select Different Categories")
    public void testLoggedUserSelectDifferentCategories() {
        // 1. Login with valid credentials
        loginPage.enterMyAccount();
        loginPage.enterLogin();
        loginPage.enterEmail("Rana@gmail.com");
        loginPage.enterPassword("111111");
        loginPage.clickLoginButton();
        accountPage.waitForAccountPageToLoad();

        // 2. Click the Tablets category link from top menu
        driver.findElement(By.linkText("Tablets")).click();

        // 3. Verify we navigate to Tablets category page
        String pageHeader = driver.findElement(By.cssSelector("#content h2")).getText().trim();
        Assert.assertEquals(pageHeader, "Tablets", 
                "Should navigate to Tablets page");
    }
}
