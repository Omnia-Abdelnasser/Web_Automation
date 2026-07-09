package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ProductPage;
import pages.ProductReviewPage;

@Listeners(ExtentListener.class)
public class TC_05_ProductReviewTest extends BaseTest {

    private ProductPage productPage;
    private ProductReviewPage productReviewPage;

    private static final int MACBOOK_ID = 43; // MacBook

    @BeforeMethod
    public void setUp() {
        productPage = new ProductPage(driver);
        productReviewPage = new ProductReviewPage(driver);
    }

    @Test(description = "TC-05 - Submit a Product Review and Rating")
    public void testSubmitProductReviewAndRating() {
        // 1. Navigate to the MacBook product page
        productPage.navigateToProduct(MACBOOK_ID);

        // 2. Click the Reviews tab
        productReviewPage.clickReviewsTab();

        // 3. Fill in the review form
        productReviewPage.enterReviewerName("Test Reviewer");
        productReviewPage.enterReviewText(
            "This is an automated test review for the MacBook. " +
            "The product quality is excellent and meets all expectations.");

        // 4. Select 5-star rating
        productReviewPage.selectFiveStarRating();

        // 5. Click Submit
        productReviewPage.clickSubmitReview();

        // 6. Verify success message
        String successMsg = productReviewPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Thank you for your review"),
                "Expected thank you message after submitting review. Got: " + successMsg);
    }
}

