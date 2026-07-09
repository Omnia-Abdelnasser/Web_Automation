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
public class TC_01_UpdateAccountTest extends BaseTest {

    private LoginPage loginPage;
    private AccountPage accountPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
    }

    @Test(description = "TC-01 - Update Personal Account Information")
    public void testUpdatePersonalAccountInformation() {
        // 1. Login with valid credentials
        loginPage.enterMyAccount();
        loginPage.enterLogin();
        loginPage.enterEmail("Rana@gmail.com");
        loginPage.enterPassword("111111");
        loginPage.clickLoginButton();
        accountPage.waitForAccountPageToLoad();

        // 2. Navigate to Edit Account page
        accountPage.navigateToEditAccount();

        // 3. Update First Name with timestamp to ensure change
        String updatedFirstName = "Rana" + System.currentTimeMillis() % 1000;
        accountPage.clearAndEnterFirstName(updatedFirstName);
        accountPage.clearAndEnterLastName("Test");
        accountPage.clearAndEnterTelephone("1234567890");

        // 4. Click Continue/Save
        accountPage.clickContinue();

        // 5. Verify success message
        String successMsg = accountPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Success"),
                "Expected success message after updating account information");
    }
}

