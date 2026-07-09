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
public class TC_11_SortByCategoryTest extends BaseTest {

    private CategoryPage categoryPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        categoryPage = new CategoryPage(driver);
        homePage = new HomePage(driver);
    }

    @Test(description = "TC-11 - Sort Products Using the Sort By Dropdown")
    public void testSortProductsUsingTheSortByDropdown() {
        // 1. Hover over 'Components' in the navigation menu
        homePage.clickToCompontentsMenu();

        // 2. Click 'Monitors' from the dropdown
        categoryPage.navigateToMonitors();

        // 3. Click the 'Sort By' dropdown and select 'Price (Low > High)'
        String sortOption = "Price (Low > High)";
        categoryPage.selectSortBy(sortOption);

        // 4. Verify the selected option matches
        String selectedOption = categoryPage.getSelectedSortByOption();
        Assert.assertEquals(selectedOption, sortOption,
                "Products should reorder correctly from lowest to highest price");
    }
}

