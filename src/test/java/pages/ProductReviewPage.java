package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

public class ProductReviewPage extends WaitUtils {

    private final By reviewsTab      = By.cssSelector("a[href='#tab-review']");
    private final By reviewNameField = By.id("input-name");
    private final By reviewTextField = By.id("input-review");
    private final By ratingFiveStar  = By.cssSelector("input[name='rating'][value='5']");
    private final By submitButton    = By.id("button-review");
    private final By successAlert    = By.cssSelector(".alert-success");
    private final By reviewPanel     = By.id("tab-review");

    public ProductReviewPage(WebDriver driver) {
        super(driver);
    }

    public ProductReviewPage clickReviewsTab() {
        waitForElementToBeClickable(reviewsTab).click();
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }

    public ProductReviewPage enterReviewerName(String name) {
        WebElement el = waitForElementToBeVisible(reviewNameField);
        el.clear();
        el.sendKeys(name);
        return this;
    }

    public ProductReviewPage enterReviewText(String review) {
        WebElement el = waitForElementToBeVisible(reviewTextField);
        el.clear();
        el.sendKeys(review);
        return this;
    }

    public ProductReviewPage selectFiveStarRating() {
        WebElement radio = waitForElementToBeVisible(ratingFiveStar);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
        return this;
    }

    public ProductReviewPage clickSubmitReview() {
        waitForElementToBeClickable(submitButton).click();
        return this;
    }

    public String getSuccessMessage() {
        return waitForElementToBeVisible(successAlert).getText();
    }
}
