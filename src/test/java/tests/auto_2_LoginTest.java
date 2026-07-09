package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;

@Listeners(ExtentListener.class)
public class auto_2_LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);

    }
    @Test(priority = 1)
    public void loginWithValidCredentials() {
        loginPage.enterMyAccount();
        loginPage.enterLogin();
        loginPage.enterEmail("Rana@gmail.com");
        loginPage.enterPassword("111111");
        loginPage.clickLoginButton();
        loginPage.getSuccessMessage("https://awesomeqa.com/ui/index.php?route=account/account");
    }
    @Test(priority = 2)
    public void loginWithValidEmailAndInvalidPassword() {
        loginPage.enterMyAccount();
        loginPage.enterLogin();
        loginPage.enterEmail("Rana@gmail.com");
        loginPage.enterPassword("wrongPassword");
        loginPage.clickLoginButton();
        Assert.assertEquals(loginPage.getErrorMessage(), "Warning: No match for E-Mail Address and/or Password.");
    }

    @Test(priority = 3)
    public void loginWithInvalidEmailAndValidPassword() {
        loginPage.enterMyAccount();
        loginPage.enterLogin();
        loginPage.enterEmail("invalidEmail@gmail.com");
        loginPage.enterPassword("111111");
        loginPage.clickLoginButton();
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(
            errorMsg.contains("No match for E-Mail Address and/or Password") ||
            errorMsg.contains("exceeded allowed number of login attempts"),
            "Unexpected error message: " + errorMsg
        );
    }
    @Test(priority = 4)
    public void loginWithInvalidEmailAndInvalidPassword() {
        loginPage.enterMyAccount();
        loginPage.enterLogin();
        loginPage.enterEmail("invalid@gmail.com");
        loginPage.enterPassword("121212");
        loginPage.clickLoginButton();
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(
            errorMsg.contains("No match for E-Mail Address and/or Password") ||
            errorMsg.contains("exceeded allowed number of login attempts"),
            "Unexpected error message: " + errorMsg
        );
    }
}
