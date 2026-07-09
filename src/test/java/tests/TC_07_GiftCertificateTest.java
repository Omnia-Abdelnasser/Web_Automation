package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.GiftCertificatePage;
import pages.LoginPage;

@Listeners(ExtentListener.class)
public class TC_07_GiftCertificateTest extends BaseTest {

    private LoginPage loginPage;
    private GiftCertificatePage giftCertificatePage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        giftCertificatePage = new GiftCertificatePage(driver);
    }

    @Test(description = "TC-07 - Purchase a Gift Certificate")
    public void testPurchaseGiftCertificate() {
        // 1. Login with valid credentials
        loginPage.enterMyAccount();
        loginPage.enterLogin();
        loginPage.enterEmail("Rana@gmail.com");
        loginPage.enterPassword("111111");
        loginPage.clickLoginButton();
        new pages.AccountPage(driver).waitForAccountPageToLoad();

        // 2. Navigate to Gift Certificate page
        giftCertificatePage.navigateToGiftCertificate();

        // 3. Verify we are on the gift certificate page
        Assert.assertTrue(giftCertificatePage.isOnGiftCertificatePage(),
                "Should be on the Gift Certificate page");

        // 4. Fill in the gift certificate form
        giftCertificatePage.enterRecipientName("John Doe");
        giftCertificatePage.enterRecipientEmail("johndoe@example.com");
        giftCertificatePage.selectFirstVoucherTheme();
        giftCertificatePage.enterAmount("25.00");
        giftCertificatePage.enterMessage("Happy Birthday! Here is a gift for you.");

        // 5. Agree to terms and submit
        giftCertificatePage.clickAgree();
        giftCertificatePage.clickContinue();

        // 6. Verify success message
        String successMsg = giftCertificatePage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Success"),
                "Expected success message after purchasing gift certificate. Got: " + successMsg);
    }
}

