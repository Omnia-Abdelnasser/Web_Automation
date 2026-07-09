package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;

@Listeners(ExtentListener.class)
public class TC_10_SearchNoResultTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        homePage = new HomePage(driver);
    }

    @Test(description = "TC-10 - Search for a Product That Does Not Exist")
    public void testSearchForProductThatDoesNotExist() {
        // 1. Open the browser (done in BaseTest)
        // 2. Type non-existent product in search field
        String searchQuery = "xyzabc123";
        homePage.enterSearchQuery(searchQuery);

        // 3. Click search button
        homePage.clickSearchButton();

        // 4. Verify "no product" message is displayed
        Assert.assertTrue(homePage.isNoProductMessageDisplayed(),
                "Page should show 'There is no product that matches the search criteria.'");
    }
}

