package tests;

import base.BaseTest;
import listeners.ExtentListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.LoginPage;

@Listeners(ExtentListener.class)
public class TC_04_AddressBookTest extends BaseTest {

    private LoginPage loginPage;
    private AccountPage accountPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
    }

    @Test(description = "TC-04 - Add a New Address to Address Book")
    public void testAddNewAddressToAddressBook() {
        // 1. Login with valid credentials
        loginPage.enterMyAccount();
        loginPage.enterLogin();
        loginPage.enterEmail("Rana@gmail.com");
        loginPage.enterPassword("111111");
        loginPage.clickLoginButton();
        accountPage.waitForAccountPageToLoad();

        // 2. Navigate to Add New Address page
        accountPage.navigateToAddNewAddress();

        // 3. Fill in the address form
        accountPage.enterAddressFirstName("Rana");
        accountPage.enterAddressLastName("Test");
        accountPage.enterAddressAddress1("123 Test Street");
        accountPage.enterAddressCity("Cairo");
        accountPage.enterAddressPostCode("12345");
        accountPage.selectAddressCountry("Egypt");
        accountPage.selectAddressZone("Al Qahirah");

        // 4. Click Continue/Save
        accountPage.clickContinue();

        // 5. Verify success message
        String successMsg = accountPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("success") || successMsg.contains("Success"),
                "Expected success message after adding new address");
    }
}

