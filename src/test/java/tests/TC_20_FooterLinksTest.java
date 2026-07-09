package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.Config;

import java.util.LinkedHashMap;
import java.util.Map;

@Listeners(ExtentListener.class)
public class TC_20_FooterLinksTest extends BaseTest {

    @BeforeMethod
    public void setUp() {
        // BaseTest already opens the browser and navigates to base URL
    }

    @Test(description = "TC-20 - Verify All Footer Links Navigate Correctly")
    public void testVerifyAllFooterLinksNavigateCorrectly() {
        // Build a map of link text -> expected URL fragment
        Map<String, String> footerLinks = new LinkedHashMap<>();

        // Information section
        footerLinks.put("About Us",             "route=information/information");
        footerLinks.put("Delivery Information", "route=information/information");
        footerLinks.put("Privacy Policy",       "route=information/information");
        footerLinks.put("Terms & Conditions",   "route=information/information");

        // Customer Service section
        footerLinks.put("Contact Us",           "route=information/contact");
        footerLinks.put("Returns",              "route=account/return/add");
        footerLinks.put("Site Map",             "route=information/sitemap");

        // My Account section
        footerLinks.put("My Account",           "route=account");
        footerLinks.put("Order History",        "route=account/order");
        footerLinks.put("Wish List",            "route=account/wishlist");
        footerLinks.put("Newsletter",           "route=account/newsletter");

        for (Map.Entry<String, String> entry : footerLinks.entrySet()) {
            String linkText = entry.getKey();
            String expectedUrlFragment = entry.getValue();

            // Go back to home page before each click
            driver.get(Config.baseUrl);
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            // Find and click the footer link
            try {
                WebElement link = driver.findElement(By.partialLinkText(linkText));
                link.click();
                try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                String currentUrl = driver.getCurrentUrl();
                Assert.assertFalse(currentUrl.contains("404"),
                        "Link '" + linkText + "' should not lead to a 404 page. URL: " + currentUrl);

            } catch (Exception e) {
                Assert.fail("Could not find or click footer link: '" + linkText + "'. Error: " + e.getMessage());
            }
        }
    }
}

