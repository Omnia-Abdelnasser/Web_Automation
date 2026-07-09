package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class WishlistPage {
    WebDriver driver;
    WaitUtils waitUtils;

    public WishlistPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public boolean isProductInWishlist(String productName) {
        String xpathExpression = "//div[contains(@class, 'table-responsive')]//table/tbody/tr/td[contains(@class, 'text-left')]/a[contains(text(), '" + productName + "')]";
        return waitUtils.waitForElementToBeVisible(By.xpath(xpathExpression)).isDisplayed();
    }
}