package tests;
import base.BaseTest;
import org.testng.Assert;
import pages.RegisterPage;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import listeners.ExtentListener;

@Listeners(ExtentListener.class)
public class auto_1_RegisterTest extends BaseTest {
    private RegisterPage registerPage;


    @BeforeMethod
    public void setUp() {
        registerPage = new RegisterPage(driver);
    }

    @Test(priority = 1)
    public void registerWithValidData() {

        registerPage.enterMyAccount();
        registerPage.enterRegister();
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        String uniqueEmail = "omnia" + System.currentTimeMillis() + "@example.com";
        registerPage.enterEmail(uniqueEmail);
        registerPage.enterTelephone("1234567890");
        registerPage.enterPassword("password123");
        registerPage.enterConfirmPassword("password123");
        registerPage.radioButton();
        registerPage.checkBox();
        registerPage.clickContinueButton();
        String successMessage = registerPage.getSuccessMessage();
        Assert.assertEquals(successMessage, "Your Account Has Been Created!");
    }
    @Test(priority = 2)
    public void registerWithInvalidData() {
        registerPage.enterMyAccount();
        registerPage.enterRegister();
        registerPage.enterFirstName("");
        registerPage.enterLastName("");
        registerPage.enterEmail("invalid-email@gmail.com");
        registerPage.enterTelephone("1234567890");
        registerPage.enterPassword("password123");
        registerPage.enterConfirmPassword("password123");
        registerPage.radioButton();
        registerPage.checkBox();
        registerPage.clickContinueButton();
        String errorMessage = registerPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("First Name must be between 1 and 32 characters!"));
    }
    @Test(priority = 3)
    public void registerWithExistingEmail() {
        registerPage.enterMyAccount();
        registerPage.enterRegister();
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("omnia.doe@example.com");
        registerPage.enterTelephone("1234567890");
        registerPage.enterPassword("password123");
        registerPage.enterConfirmPassword("password123");
        registerPage.radioButton();
        registerPage.checkBox();
        registerPage.clickContinueButton();
        Assert.assertEquals(registerPage.getExistingEmailErrorMessage(), "Warning: E-Mail Address is already registered!");
    }
    @Test(priority = 4)
    public void registerWithMismatchedPasswords() {
        registerPage.enterMyAccount();
        registerPage.enterRegister();
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("omniya" + System.currentTimeMillis() + "@example.com");
        registerPage.enterTelephone("1234567890");
        registerPage.enterPassword("password123");
        registerPage.enterConfirmPassword("password456");
        registerPage.radioButton();
        registerPage.checkBox();
        registerPage.clickContinueButton();
        String errorMessage = registerPage.getMismatchedPasswordErrorMessage();
        Assert.assertTrue(errorMessage.contains("Password confirmation does not match password!"));
    }
    @Test(priority = 5)
    public void registerWithEmptyFields() {
        registerPage.enterMyAccount();
        registerPage.enterRegister();
        registerPage.enterFirstName("");
        registerPage.enterLastName("");
        registerPage.enterEmail("");
        registerPage.enterTelephone("");
        registerPage.enterPassword("");
        registerPage.enterConfirmPassword("");
        registerPage.radioButton();
        registerPage.checkBox();
        registerPage.clickContinueButton();
        String errorMessage = registerPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("First Name must be between 1 and 32 characters!"));
    }
    @Test(priority = 6)
    public void registerWithNameStartWithNumber() {
        registerPage.enterMyAccount();
        registerPage.enterRegister();
        registerPage.enterFirstName("A".repeat(33)); // 33 chars > 32 max
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("omnia" + System.currentTimeMillis() + "@example.com");
        registerPage.enterTelephone("1234567890");
        registerPage.enterPassword("password123");
        registerPage.enterConfirmPassword("password123");
        registerPage.radioButton();
        registerPage.checkBox();
        registerPage.clickContinueButton();
        String errorMessage = registerPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("First Name must be between 1 and 32 characters!"));
    }
    @Test(priority = 7)
    public void registerWithoutAcceptingTerms() {
        registerPage.enterMyAccount();
        registerPage.enterRegister();
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("john.doe@example.com");
        registerPage.enterTelephone("1234567890");
        registerPage.enterPassword("password123");
        registerPage.enterConfirmPassword("password123");
        registerPage.radioButton();
        registerPage.clickContinueButton();
        Assert.assertEquals(registerPage.getNotAgreedTermsErrorMessage(), "Warning: You must agree to the Privacy Policy!");

    }
}
