package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactUsPage;
import pages.HomePage;

@Listeners(ExtentListener.class)
public class TC_09_ContactUsTest extends BaseTest {
    
    private HomePage homePage;
    private ContactUsPage contactUsPage;

    @BeforeMethod
    public void setUp() {
        homePage = new HomePage(driver);
        contactUsPage = new ContactUsPage(driver);
    }
    @Test(description = "TC-09 - Submit the Contact Us Form")
    public void testContactUsForm() {
        homePage.clickContactUs();
        String uniqueEmail = "testUser_" + System.currentTimeMillis() + "@example.com";
        contactUsPage.fillContactForm("John Doe", uniqueEmail, "This is a test enquiry.");
        contactUsPage.clickSubmitButton();
        String successMessage = contactUsPage.getSuccessMessage();
        Assert.assertEquals(successMessage, "Your enquiry has been successfully sent to the store owner!");
    }
}

