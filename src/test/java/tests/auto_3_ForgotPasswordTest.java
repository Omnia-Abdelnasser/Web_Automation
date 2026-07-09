package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ForgottenPasswordPage;
import pages.LoginPage;
import pages.RegisterPage;
import utils.Config;

@Listeners(ExtentListener.class)
public class auto_3_ForgotPasswordTest extends BaseTest {
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private ForgottenPasswordPage forgottenPasswordPage;

    @BeforeMethod
    public void setUp() {
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        forgottenPasswordPage = new ForgottenPasswordPage(driver);
    }

    @Test
    public void testForgotPassword() {
        String testEmail = "testuser_reset_" + System.currentTimeMillis() + "@example.com";

        driver.get(Config.baseUrl + "index.php?route=account/register");
        registerPage.enterFirstName("Reset");
        registerPage.enterLastName("Tester");
        registerPage.enterEmail(testEmail);
        registerPage.enterTelephone("0987654321");
        registerPage.enterPassword("Password123");
        registerPage.enterConfirmPassword("Password123");
        registerPage.radioButton();
        registerPage.checkBox();
        registerPage.clickContinueButton();

        Assert.assertEquals(registerPage.getSuccessMessage(), "Your Account Has Been Created!");

        driver.get(Config.baseUrl + "index.php?route=account/logout");

        loginPage.enterMyAccount();
        loginPage.enterLogin();
        loginPage.clickForgottenPassword();

        forgottenPasswordPage.enterEmail(testEmail);
        forgottenPasswordPage.clickContinue();

        String alertText = loginPage.getAlertText();
        Assert.assertTrue(alertText.contains("An email with a confirmation link"));
    }
}
