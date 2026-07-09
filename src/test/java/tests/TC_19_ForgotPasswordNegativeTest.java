package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ForgottenPasswordPage;
import pages.LoginPage;

@Listeners(ExtentListener.class)
public class TC_19_ForgotPasswordNegativeTest extends BaseTest {

    private LoginPage loginPage;
    private ForgottenPasswordPage forgottenPasswordPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        forgottenPasswordPage = new ForgottenPasswordPage(driver);
    }

    @Test(description = "TC-19 - Use Forgotten Password with Unregistered Email")
    public void testForgottenPasswordWithUnregisteredEmail() {
        // 1. Navigate to Login page
        loginPage.enterMyAccount();
        loginPage.enterLogin();

        // 2. Click 'Forgotten Password' link
        loginPage.clickForgottenPassword();

        // 3. Enter an email that is NOT registered
        forgottenPasswordPage.enterEmail("notfound99@test.com");

        // 4. Click 'Continue'
        forgottenPasswordPage.clickContinue();

        // 5. Verify warning message
        String warningMsg = loginPage.getErrorMessage();
        Assert.assertTrue(warningMsg.toLowerCase().contains("was not found in our records") || warningMsg.toLowerCase().contains("not associated with any customer"),
                "Expected warning message for unregistered email. Got: " + warningMsg);
    }
}

