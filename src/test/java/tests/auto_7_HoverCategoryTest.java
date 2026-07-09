package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.Config;

@Listeners(ExtentListener.class)
public class auto_7_HoverCategoryTest extends BaseTest {

    @Test(description = "TC-07 - Hover Main and Sub Categories Randomly")
    public void testHoverMainAndSubCategoriesRandomly() {
        driver.get(Config.baseUrl);

        // 1. Locate Desktops main category link
        WebElement desktopsMenu = driver.findElement(By.linkText("Desktops"));

        // 2. Hover over the desktops menu using Actions
        Actions actions = new Actions(driver);
        actions.moveToElement(desktopsMenu).perform();

        // 3. Click the Mac sub-category link that appears
        WebElement macSubMenu = driver.findElement(By.partialLinkText("Mac"));
        macSubMenu.click();

        // 4. Verify we navigated to the Mac sub-category page
        String pageHeader = driver.findElement(By.cssSelector("#content h2")).getText().trim();
        Assert.assertTrue(pageHeader.contains("Mac"), "Should navigate to Mac sub-category page");
    }
}
