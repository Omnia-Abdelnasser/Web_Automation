package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;
import java.util.Random;

public class HomePage {
    WebDriver driver;
    WaitUtils waitUtils;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void enterSearchQuery(String productName) {
        driver.findElement(By.name("search")).clear();
        driver.findElement(By.name("search")).sendKeys(productName);
    }
        public void clickToCompontentsMenu() {
        waitUtils.waitForElementToBeClickable(By.linkText("Components")).click();
    }

    public void clickSearchButton() {
        waitUtils.waitForElementToBeClickable(By.cssSelector("#search button")).click();
    }

    public void clickCurrencyDropdown() {
        waitUtils.waitForElementToBeClickable(By.cssSelector("form#form-currency button.dropdown-toggle")).click();
    }

    public void selectEuroOption() {
        waitUtils.waitForElementToBeClickable(By.name("EUR")).click();
    }

    public void selectDollarOption() {
        waitUtils.waitForElementToBeClickable(By.name("USD")).click();
    }

    public String getCurrencySymbolText() {
        return waitUtils.waitForElementToBeVisible(By.cssSelector("form#form-currency strong")).getText();
    }

    public String getFirstProductTitle() {
        return waitUtils.waitForElementToBeVisible(By.xpath("(//div[contains(@class, 'product-layout')]//h4/a)[1]")).getText().trim();
    }

    public void clickReturnProduct() {
        waitUtils.waitForElementToBeClickable(By.linkText("Returns")).click();
    }

    public void clickContactUs() {
        waitUtils.waitForElementToBeClickable(By.linkText("Contact Us")).click();
    }

    public void clickFirstProductWishlist() {
        waitUtils.waitForElementToBeClickable(By.xpath("(//button[contains(@onclick, 'wishlist.add')])[1]")).click();
    }

    public void clickFirstProductCompare() {
        waitUtils.waitForElementToBeClickable(By.xpath("(//button[contains(@onclick, 'compare.add')])[1]")).click();
    }

    public void waitForSuccessAlert() {
        waitUtils.waitForElementToBeVisible(By.cssSelector(".alert-success"));
    }

    public boolean isSearchResultProductVisible(String productName) {
        String xpathExpression = "//div[contains(@class, 'product-layout')]//h4/a[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + productName.toLowerCase() + "')]";
        return waitUtils.waitForElementToBeVisible(By.xpath(xpathExpression)).isDisplayed();
    }
    public boolean isNoProductMessageDisplayed() {
        return waitUtils.waitForElementToBeVisible(
                        By.cssSelector("#content p"))
                .isDisplayed();
    }


    public String selectCategoryRandomly() {
        Random rand = new Random();
        int randomCategoryIndex = rand.nextInt(8) + 1;
        By liXpath = By.xpath("(//ul[contains(@class, 'navbar-nav')]/li)[" + randomCategoryIndex + "]");
        
        org.openqa.selenium.WebElement li = waitUtils.waitForElementToBeVisible(liXpath);
        org.openqa.selenium.WebElement link = li.findElement(By.xpath("./a"));
        String categoryName = link.getText().trim();

        String liClass = li.getAttribute("class");
        if (liClass != null && liClass.contains("dropdown")) {
            // Click to expand dropdown
            link.click();
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            
            // Click "Show All" link
            org.openqa.selenium.WebElement seeAll = li.findElement(By.className("see-all"));
            seeAll.click();
        } else {
            // Direct category page link
            link.click();
        }
        
        return categoryName;
    }

}