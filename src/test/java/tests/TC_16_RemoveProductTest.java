package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductPage;

@Listeners(ExtentListener.class)
public class TC_16_RemoveProductTest extends BaseTest {
    private LoginPage loginPage;
    private ProductPage productPage;
    private CartPage cartPage;

    private static final int PRODUCT_ID = 28; // HTC Touch HD

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test(description = "TC-16 - Remove a Product from the Shopping Cart")
    public void testRemoveProductFromShoppingCart() {
        // 1. Login with valid credentials
        loginPage.enterMyAccount();
        loginPage.enterLogin();
        loginPage.enterEmail("Rana@gmail.com");
        loginPage.enterPassword("111111");
        loginPage.clickLoginButton();
        new pages.AccountPage(driver).waitForAccountPageToLoad();

        // 2. Clear cart for a clean slate
        cartPage.navigateToCart();
        cartPage.clearCart();

        // 3. Add product to cart
        productPage.navigateToProduct(PRODUCT_ID);
        productPage.addToCart();
        Assert.assertTrue(productPage.isSuccessAlertDisplayed(), "Product should be added to cart successfully");

        // 4. Go to Shopping Cart page
        cartPage.navigateToCart();
        Assert.assertTrue(cartPage.isCartNotEmpty(), "Cart should not be empty");

        // 5. Click the Remove button next to the item
        cartPage.removeProduct(0);

        // 6. Verify cart is empty
        Assert.assertTrue(cartPage.isCartEmpty(), "Shopping cart should be empty");
    }
}

