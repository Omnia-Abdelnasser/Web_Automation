package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CheckoutPage;
import pages.ProductPage;
import pages.CartPage;


@Listeners(ExtentListener.class)
public class auto_12_CheckoutTest extends BaseTest {

    private ProductPage productPage;
    private CheckoutPage checkoutPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setUp() {
        productPage = new ProductPage(driver);
        checkoutPage = new CheckoutPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test(description = "TC12 - Create an order with a successful payment (Guest Checkout)")
    public void testCreateOrderWithSuccessfulPayment() {
        // Find an in-stock simple product dynamically by searching all products
        driver.get(utils.Config.baseUrl + "index.php?route=product/search&search=");
        java.util.List<org.openqa.selenium.WebElement> productElements = driver.findElements(org.openqa.selenium.By.cssSelector(".product-layout h4 a"));
        java.util.List<String> productUrls = new java.util.ArrayList<>();
        for (org.openqa.selenium.WebElement el : productElements) {
            productUrls.add(el.getAttribute("href"));
        }

        boolean foundInStock = false;
        for (String url : productUrls) {
            driver.get(url);

            // If product has required options (like Apple Cinema 30" in the screenshot), skip it
            boolean hasRequiredOptions = !driver.findElements(org.openqa.selenium.By.cssSelector("#product .form-group.required")).isEmpty();
            if (hasRequiredOptions) {
                System.out.println("Product at " + url + " requires options. Skipping...");
                continue;
            }

            productPage.addToCart();

            cartPage.navigateToCart();
            if (cartPage.hasStockWarning() || !cartPage.isCartNotEmpty()) {
                System.out.println("Product at " + url + " is out of stock. Trying next...");
                cartPage.clearCart();
            } else {
                System.out.println("Found in-stock simple product at " + url);
                foundInStock = true;
                break;
            }
        }

        Assert.assertTrue(foundInStock, "Should find at least one in-stock simple product on the site to checkout");

        checkoutPage.navigateToCheckout();

        checkoutPage.selectGuestCheckout();

        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@test.com";
        checkoutPage.fillBillingDetails(
                "Test", "User",
                uniqueEmail,
                "1234567890",
                "123 Test Street",
                "Test City",
                "12345",
                "United Kingdom",
                "Greater London"
        );

        checkoutPage.clickContinueBilling();

        checkoutPage.selectFlatShippingAndContinue();

        checkoutPage.agreeAndContinuePayment();


        Assert.assertTrue(checkoutPage.isConfirmTableVisible(),
                "Order confirmation summary should be visible before confirming");
        checkoutPage.confirmOrder();

        Assert.assertTrue(checkoutPage.isOnSuccessPage(),
                "Should be redirected to the success page after placing order");
        Assert.assertTrue(checkoutPage.isOrderSuccessPageDisplayed(),
                "Success page should show 'Your order has been placed!'");
    }
}


