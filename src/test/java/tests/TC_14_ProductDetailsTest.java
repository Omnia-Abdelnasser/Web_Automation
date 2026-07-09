package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ProductPage;

@Listeners(ExtentListener.class)
public class TC_14_ProductDetailsTest extends BaseTest {
    private ProductPage productPage;

    private static final int MACBOOK_ID = 43; // MacBook

    @BeforeMethod
    public void setUp() {
        productPage = new ProductPage(driver);
    }

    @Test(description = "TC14 - Verify Product Details Page Content")
    public void testVerifyProductDetailsPageContent() {
        productPage.navigateToProduct(MACBOOK_ID);

        // Verify all required elements are displayed
        Assert.assertTrue(productPage.isProductNameDisplayed(), "Product name should be displayed");
        Assert.assertTrue(productPage.isProductPriceDisplayed(), "Product price should be displayed");
        Assert.assertTrue(productPage.isDescriptionTabDisplayed(), "Description tab should be displayed");
        Assert.assertTrue(productPage.isProductImageDisplayed(), "Product image should be displayed");
        Assert.assertTrue(productPage.isAddToCartButtonDisplayed(), "Add to Cart button should be displayed");
        Assert.assertTrue(productPage.isWishlistButtonDisplayed(), "Wish List button should be displayed");
        Assert.assertTrue(productPage.isCompareButtonDisplayed(), "Compare button should be displayed");
        Assert.assertTrue(productPage.isReviewsTabDisplayed(), "Reviews tab should be displayed");

        Assert.assertEquals(productPage.getProductName(), "MacBook", "Product name should match MacBook");
    }
}
