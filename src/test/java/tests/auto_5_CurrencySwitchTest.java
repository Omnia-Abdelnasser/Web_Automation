package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;
import utils.Config;

@Listeners(ExtentListener.class)
public class auto_5_CurrencySwitchTest extends BaseTest {
    private RegisterPage registerPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        registerPage = new RegisterPage(driver);
        homePage = new HomePage(driver);
    }

    @Test
    public void testLoggedUserSwitchCurrency() {
        driver.get(Config.baseUrl + "index.php?route=account/register");
        String email = "testuser_" + System.currentTimeMillis() + "@example.com";

        registerPage.enterFirstName("Automation");
        registerPage.enterLastName("Tester");
        registerPage.enterEmail(email);
        registerPage.enterTelephone("1234567890");
        registerPage.enterPassword("SecurePass123");
        registerPage.enterConfirmPassword("SecurePass123");
        registerPage.radioButton();
        registerPage.checkBox();
        registerPage.clickContinueButton();

        Assert.assertEquals(registerPage.getSuccessMessage(), "Your Account Has Been Created!");

        driver.get(Config.baseUrl);
        homePage.clickCurrencyDropdown();
        homePage.selectEuroOption();
        Assert.assertTrue(homePage.getCurrencySymbolText().contains("\u20AC") || homePage.getCurrencySymbolText().contains("EUR") || homePage.getCurrencySymbolText().contains("€"), 
                "Currency symbol should be Euro. Got: " + homePage.getCurrencySymbolText());

        homePage.clickCurrencyDropdown();
        homePage.selectDollarOption();
        Assert.assertTrue(homePage.getCurrencySymbolText().contains("$") || homePage.getCurrencySymbolText().contains("USD"),
                "Currency symbol should be Dollar. Got: " + homePage.getCurrencySymbolText());
    }
}
