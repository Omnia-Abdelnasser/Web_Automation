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
public class TC_17_CouponCodeTest extends BaseTest {

    private ProductPage productPage;
    private CartPage cartPage;

    private static final int MACBOOK_ID = 43; // MacBook

    @BeforeMethod
    public void setUp() {
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test(description = "TC-17 - Apply a Coupon Code in the Shopping Cart")
    public void testApplyCouponCodeInShoppingCart() {
        // 1. Add MacBook to the cart
        productPage.navigateToProduct(MACBOOK_ID);
        productPage.addToCart();
        Assert.assertTrue(productPage.isSuccessAlertDisplayed(),
                "Product should be added to cart successfully");

        // 2. Navigate to the Shopping Cart page
        cartPage.navigateToCart();
        Assert.assertTrue(cartPage.isCartNotEmpty(), "Cart should not be empty");

        // 3. Expand the 'Use Coupon Code' section
        cartPage.expandCouponSection();

        // 4. Enter an invalid coupon code
        cartPage.enterCouponCode("TEST");

        // 5. Click 'Apply Coupon'
        cartPage.clickApplyCoupon();

        // 6. Verify warning message appears (invalid coupon)
        String warningMsg = cartPage.getCouponWarningMessage();
        Assert.assertNotNull(warningMsg, "A warning message should appear for an invalid coupon code");
        Assert.assertFalse(warningMsg.isEmpty(), "Warning message should not be empty");
    }
}

