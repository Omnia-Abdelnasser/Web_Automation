package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

import java.util.List;

public class CategoryPage extends WaitUtils {

    private final By listViewButton = By.id("list-view");
    private final By gridViewButton = By.id("grid-view");
    private final By productLayouts = By.cssSelector(".product-layout");
    private final By sortByDropdown = By.id("input-sort");
    private final By sortByOptions = By.cssSelector("#input-sort option");
    private final By showDropdown = By.id("input-limit");
    private final By showOptions = By.cssSelector("#input-limit option");

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public CategoryPage selectSortBy(String sortOption) {
        waitForElementToBeClickable(sortByDropdown).click();
        List<WebElement> options = driver.findElements(sortByOptions);
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(sortOption)) {
                option.click();
                break;
            }
        }
        return this;
    }
    public String getSelectedSortByOption() {
        return driver.findElement(By.cssSelector("#input-sort option:checked")).getText();
    }


    public CategoryPage navigateToMonitors() {
        driver.get("https://awesomeqa.com/ui/index.php?route=product/category&path=25_28");
        return this;
    }

    public CategoryPage selectShowPerPage(String showOption) {
        waitForElementToBeClickable(showDropdown).click();
        List<WebElement> options = driver.findElements(showOptions);
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(showOption)) {
                option.click();
                break;
            }
        }
        return this;
    }
     public int getDisplayedProductsCount() {
        List<WebElement> products = driver.findElements(productLayouts);
        return products.size();
    }

    public CategoryPage clickListView() {
        waitForElementToBeClickable(listViewButton).click();
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }

    public CategoryPage clickGridView() {
        waitForElementToBeClickable(gridViewButton).click();
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }

    public boolean isListViewActive() {
        List<WebElement> elements = driver.findElements(productLayouts);
        if (elements.isEmpty()) return false;
        for (WebElement el : elements) {
            String className = el.getAttribute("class");
            if (!className.contains("product-list")) {
                return false;
            }
        }
        return true;
    }

    public boolean isGridViewActive() {
        List<WebElement> elements = driver.findElements(productLayouts);
        if (elements.isEmpty()) return false;
        for (WebElement el : elements) {
            String className = el.getAttribute("class");
            if (!className.contains("product-grid")) {
                return false;
            }
        }
        return true;
    }

}
