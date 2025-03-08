package SauceDemoTests;

import BaseTest.BaseTest;
import Pages.CartPage;
import Pages.CheckOutPage;
import Pages.ProductsPage;
import com.github.javafaker.Faker;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static Utils.Util.captureScreenshot;

public class CheckoutTest extends BaseTest {
    @Test
    public void testCheckoutWorkflow() {
        SoftAssert softAssert = new SoftAssert();
        Faker faker = new Faker();
        loginPage.setUserName("standard_user");
        loginPage.setPassword("secret_sauce");
        ProductsPage productsPage = loginPage.clickOnLogin();

        productsPage.selectRandomProducts(2);

        CartPage cartPage = productsPage.openCart();
        CheckOutPage checkOutPage = cartPage.clickOnCheckOut();

        checkOutPage.enterCheckoutDetails(faker.name().firstName(), faker.name().lastName(), "");
        checkOutPage.clickOnContinue();

        String errorMessage = checkOutPage.getErrorMessage();
        softAssert.assertTrue(errorMessage.contains("Postal Code is required"), "Expected ZIP error not found!");

        checkOutPage.enterCheckoutDetails(faker.name().firstName(), faker.name().lastName(), faker.address().zipCode());
        checkOutPage.clickOnContinue();

        captureScreenshot("checkout_overview");

        double expectedTotalPrice = checkOutPage.calculateTotalPrice();
        double displayedTotalPrice = checkOutPage.getDisplayedTotalPrice();
        softAssert.assertEquals(displayedTotalPrice, expectedTotalPrice, "Total price mismatch!");

        checkOutPage.clickOnFinish();

        String confirmationMessage = checkOutPage.getConfirmationMessage();
        softAssert.assertTrue(confirmationMessage.contains("Thank you for your order"), "Order confirmation failed!");
    }
}
