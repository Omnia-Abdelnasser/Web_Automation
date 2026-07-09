package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductPage;


@Listeners(ExtentListener.class)
public class auto_11_AddToCartTest extends BaseTest {

    private ProductPage productPage;
    private CartPage cartPage;

    private static final int MACBOOK_ID = 43; // MacBook - $602.00

    @BeforeMethod
    public void setUp() {
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test(description = "TC11 - Add item to the shopping cart and verify it appears in the cart")
    public void testAddItemToShoppingCart() {

        productPage.navigateToProduct(MACBOOK_ID);
        String productName = productPage.getProductName();
        Assert.assertTrue(productName.contains("MacBook"),
                "Product name should be MacBook");


        productPage.addToCart();


        Assert.assertTrue(productPage.isSuccessAlertDisplayed(),
                "Success alert should appear after adding product to cart");

        cartPage.navigateToCart();

        Assert.assertTrue(cartPage.isCartNotEmpty(),
                "Cart should contain the added product");

        Assert.assertTrue(cartPage.isProductInCart("MacBook"),
                "MacBook should be listed in the shopping cart");
    }
}


