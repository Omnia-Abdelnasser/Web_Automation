package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.LoginPage;

@Listeners(ExtentListener.class)
public class TC_03_NewsletterTest extends BaseTest {

    private LoginPage loginPage;
    private AccountPage accountPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
    }

    @Test(description = "TC-03 - Subscribe to Newsletter")
    public void testSubscribeToNewsletter() {
        // 1. Login with valid credentials
        loginPage.enterMyAccount();
        loginPage.enterLogin();
        loginPage.enterEmail("Rana@gmail.com");
        loginPage.enterPassword("111111");
        loginPage.clickLoginButton();
        accountPage.waitForAccountPageToLoad();

        // 2. Navigate to Newsletter subscription page
        accountPage.navigateToNewsletter();

        // 3. Click Subscribe radio button
        accountPage.clickSubscribe();

        // 4. Click Continue/Save
        accountPage.clickContinue();

        // 5. Verify success message
        String successMsg = accountPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Success"),
                "Expected success message after subscribing to newsletter");
    }
}

