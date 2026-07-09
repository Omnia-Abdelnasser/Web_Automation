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
public class TC_02_ChangePasswordTest extends BaseTest {

    private LoginPage loginPage;
    private AccountPage accountPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
    }

    @Test(description = "TC-02 - Change Account Password")
    public void testChangeAccountPassword() {
        // 1. Login with valid credentials
        loginPage.enterMyAccount();
        loginPage.enterLogin();
        loginPage.enterEmail("Rana@gmail.com");
        loginPage.enterPassword("111111");
        loginPage.clickLoginButton();
        accountPage.waitForAccountPageToLoad();

        // 2. Navigate to Change Password page
        accountPage.navigateToChangePassword();

        // 3. Enter the same password (to keep it unchanged for future test runs)
        accountPage.enterPassword("111111");
        accountPage.enterConfirmPassword("111111");

        // 4. Click Continue/Save
        accountPage.clickContinue();

        // 5. Verify success message
        String successMsg = accountPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Success"),
                "Expected success message after changing password");
    }
}

