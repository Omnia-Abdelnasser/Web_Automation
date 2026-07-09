package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;
import pages.WishlistPage;
import utils.Config;

@Listeners(ExtentListener.class)
public class auto_9_WishlistTest extends BaseTest {
    private RegisterPage registerPage;
    private HomePage homePage;
    private WishlistPage wishlistPage;

    @BeforeMethod
    public void setUp() {
        registerPage = new RegisterPage(driver);
        homePage = new HomePage(driver);
        wishlistPage = new WishlistPage(driver);
    }

    @Test
    public void testAddProductToWishlist() {
        driver.get(Config.baseUrl + "index.php?route=account/register");
        String email = "testuser_wish_" + System.currentTimeMillis() + "@example.com";

        registerPage.enterFirstName("Wish");
        registerPage.enterLastName("Tester");
        registerPage.enterEmail(email);
        registerPage.enterTelephone("0111122233");
        registerPage.enterPassword("WishPass123");
        registerPage.enterConfirmPassword("WishPass123");
        registerPage.radioButton();
        registerPage.checkBox();
        registerPage.clickContinueButton();

        Assert.assertEquals(registerPage.getSuccessMessage(), "Your Account Has Been Created!");

        driver.get(Config.baseUrl);
        String expectedProductTitle = homePage.getFirstProductTitle();
        homePage.clickFirstProductWishlist();
        homePage.waitForSuccessAlert();

        driver.get(Config.baseUrl + "index.php?route=account/wishlist");
        Assert.assertTrue(wishlistPage.isProductInWishlist(expectedProductTitle));
    }
}

