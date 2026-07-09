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
public class TC_18_EstimateShippingTest extends BaseTest {

    private ProductPage productPage;
    private CartPage cartPage;

    private static final int IPHONE_ID = 40; // iPhone

    @BeforeMethod
    public void setUp() {
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test(description = "TC-18 - Estimate Shipping Cost from the Cart")
    public void testEstimateShippingCostFromCart() {
        // 1. Add iPhone to the cart
        productPage.navigateToProduct(IPHONE_ID);
        productPage.addToCart();
        Assert.assertTrue(productPage.isSuccessAlertDisplayed(),
                "Product should be added to cart successfully");

        // 2. Navigate to the Shopping Cart page
        cartPage.navigateToCart();
        Assert.assertTrue(cartPage.isCartNotEmpty(), "Cart should not be empty");

        // 3. Expand the 'Estimate Shipping and Taxes' section
        cartPage.expandShippingEstimateSection();

        // 4. Select Country: United States
        cartPage.selectShippingCountry("United States");

        // 5. Select Region: New York
        cartPage.selectShippingRegion("New York");

        // 6. Enter Post Code: 10001
        cartPage.enterShippingPostCode("10001");

        // 7. Click 'Get Quotes'
        cartPage.clickGetQuotes();

        // 8. Verify shipping methods/results are displayed
        Assert.assertTrue(cartPage.isShippingResultsDisplayed(),
                "Available shipping methods and their costs should be displayed");

        // 9. Select shipping method
        cartPage.selectFirstShippingMethod();

        // 10. Click 'Apply Shipping'
        cartPage.clickApplyShipping();

        // 11. Verify shipping estimate is applied successfully
        Assert.assertTrue(cartPage.isSuccessAlertDisplayed(),
                "Success alert should be displayed after applying shipping");
    }
}

