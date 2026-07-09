package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CategoryPage;

@Listeners(ExtentListener.class)
public class TC_13_GridListViewTest extends BaseTest {

    private CategoryPage categoryPage;

    @BeforeMethod
    public void setUp() {
        categoryPage = new CategoryPage(driver);
    }

    @Test(description = "TC-13 - Switch Between Grid View and List View")
    public void testSwitchBetweenGridViewAndListView() {
        // 1. Navigate to the Monitors category page
        categoryPage.navigateToMonitors();

        // 2. Click the 'List' view icon and verify layout changes
        categoryPage.clickListView();
        Assert.assertTrue(categoryPage.isListViewActive(),
                "Layout should change to list format after clicking List view");

        // 3. Click the 'Grid' view icon and verify layout returns
        categoryPage.clickGridView();
        Assert.assertTrue(categoryPage.isGridViewActive(),
                "Layout should return to grid format after clicking Grid view");
    }
}

