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
public class TC_15_UpdateQuantityTest extends BaseTest {
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

    @Test(description = "TC-15 - Update Product Quantity in the Cart")
    public void testUpdateProductQuantityInCart() {
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

        // 5. Change the quantity from 1 to 3 and click update
        cartPage.updateQuantity(0, 3);

        // 6. Verify quantity updates to 3
        String quantity = cartPage.getQuantityInRow(0);
        if (quantity.isEmpty()) {
            System.out.println("=== DEBUG: Quantity is empty! ===");
            try {
                System.out.println("Page Title: " + driver.getTitle());
                System.out.println("Page URL: " + driver.getCurrentUrl());
                System.out.println("Page content text: " + driver.findElement(org.openqa.selenium.By.id("content")).getText());
            } catch (Exception e) {
                System.out.println("Error printing content: " + e.getMessage());
            }
        }
        Assert.assertEquals(quantity, "3", "Cart quantity should update to 3");
    }
}

