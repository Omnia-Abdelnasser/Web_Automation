package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ReturnProductPage;

@Listeners(ExtentListener.class)
public class TC_08_ReturnProductTest extends BaseTest {

    private ReturnProductPage returnProductPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        homePage = new HomePage(driver);
        returnProductPage = new ReturnProductPage(driver);
    }
@Test(description = "TC-08 - Submit a Product Return Request")
    public void testReturnProduct() {
        homePage.clickReturnProduct();
        returnProductPage.enterFirstName("John");
        returnProductPage.enterLastName("Doe");
        String uniqueEmail = "john.doe" + System.currentTimeMillis() + "@example.com";
        returnProductPage.enterEmail(uniqueEmail);
        returnProductPage.enterTelephone("1234567890");
        returnProductPage.enterOrderId("123456");
        returnProductPage.enterOrderDate("2023-01-01");
        returnProductPage.enterProductName("Sample Product");
        returnProductPage.enterProductCode("SP123");
        //select reason for return from dropdown
        returnProductPage.selectReasonForReturn("Dead On Arrival");
        returnProductPage.selectProductOpened(true);
        returnProductPage.enterFaultyDetails("Product was dead on arrival.");
        returnProductPage.clickSubmitButton();
        String successMessage = returnProductPage.getSuccessMessage();
        Assert.assertEquals(successMessage, "Thank you for submitting your return request. Your request has been sent to the relevant department for processing.");

    }
}
