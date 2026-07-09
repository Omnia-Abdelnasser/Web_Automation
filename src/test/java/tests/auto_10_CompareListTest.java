package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ComparePage;
import pages.HomePage;
import utils.Config;

@Listeners(ExtentListener.class)
public class auto_10_CompareListTest extends BaseTest {
    private HomePage homePage;
    private ComparePage comparePage;

    @BeforeMethod
    public void setUp() {
        homePage = new HomePage(driver);
        comparePage = new ComparePage(driver);
    }

    @Test
    public void testAddProductToCompareList() {
        driver.get(Config.baseUrl);
        String expectedProductTitle = homePage.getFirstProductTitle();
        homePage.clickFirstProductCompare();
        homePage.waitForSuccessAlert();

        driver.get(Config.baseUrl + "index.php?route=product/compare");
        Assert.assertTrue(comparePage.isProductInCompareList(expectedProductTitle));
    }
}

