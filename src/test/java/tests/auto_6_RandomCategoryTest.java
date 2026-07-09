package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.Config;

@Listeners(ExtentListener.class)
public class auto_6_RandomCategoryTest extends BaseTest {
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        homePage = new HomePage(driver);
    }

    @Test
    public void testSelectMainAndSubCategoriesRandomly() {
        driver.get(Config.baseUrl);
        String selectedCategoryName = homePage.selectCategoryRandomly();

        String pageHeader = driver.findElement(By.cssSelector("#content h2")).getText().trim();
        String pageTitle = driver.getTitle().trim();

        boolean headerMatches = pageHeader.toLowerCase().contains(selectedCategoryName.toLowerCase())
                || selectedCategoryName.toLowerCase().contains(pageHeader.toLowerCase());
        boolean titleMatches = pageTitle.toLowerCase().contains(selectedCategoryName.toLowerCase())
                || selectedCategoryName.toLowerCase().contains(pageTitle.toLowerCase());

        Assert.assertTrue(headerMatches || titleMatches);
    }
}
