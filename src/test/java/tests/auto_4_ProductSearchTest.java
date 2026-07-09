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
public class auto_4_ProductSearchTest extends BaseTest {
    private RegisterPage registerPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        registerPage = new RegisterPage(driver);
        homePage = new HomePage(driver);
    }

    @Test
    public void testLoggedUserSearchProduct() {
        String email = "testuser_" + System.currentTimeMillis() + "@example.com";

        driver.get(Config.baseUrl + "index.php?route=account/register");
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

        String searchQuery = "MacBook";
        homePage.enterSearchQuery(searchQuery);
        homePage.clickSearchButton();

        Assert.assertTrue(homePage.isSearchResultProductVisible(searchQuery));
    }

}


