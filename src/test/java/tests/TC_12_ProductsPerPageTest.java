package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CategoryPage;
import pages.HomePage;

@Listeners(ExtentListener.class)
public class TC_12_ProductsPerPageTest extends BaseTest {

    private CategoryPage categoryPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        categoryPage = new CategoryPage(driver);
        homePage = new HomePage(driver);
    }

    @Test(description = "TC-12 - Change Number of Products Per Page")
    public void testChangeNumberOfProductsPerPage() {
        // 1. Hover over 'Components' in the navigation menu
        homePage.clickToCompontentsMenu();

        // 2. Click 'Monitors' from the dropdown
        categoryPage.navigateToMonitors();

        // 3. Locate the 'Show' dropdown and select '25'
        categoryPage.selectShowPerPage("25");

        // 4. Verify the number of displayed products is <= 25
        int displayedProductsCount = categoryPage.getDisplayedProductsCount();
        Assert.assertTrue(displayedProductsCount <= 25,
                "Number of displayed products should be less than or equal to 25. Got: " + displayedProductsCount);
    }
}

