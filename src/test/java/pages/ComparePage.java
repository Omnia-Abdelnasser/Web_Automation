package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class ComparePage {
    WebDriver driver;
    WaitUtils waitUtils;

    public ComparePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public boolean isProductInCompareList(String productName) {
        String xpathExpression = "//table[contains(@class, 'table-bordered')]//tr/td/a/strong[contains(text(), '" + productName + "')]";
        return waitUtils.waitForElementToBeVisible(By.xpath(xpathExpression)).isDisplayed();
    }
}