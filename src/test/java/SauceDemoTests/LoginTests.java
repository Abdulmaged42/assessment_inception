package SauceDemoTests;

import BaseTest.BaseTest;
import Pages.CartPage;
import Pages.CheckOutPage;
import Pages.ProductsPage;
import Utils.TestData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static Utils.Util.captureScreenshot;

public class LoginTests extends BaseTest {
    public String randomFirstName = Faker.instance().name().firstName();
    public String randomLastName = Faker.instance().name().firstName();
    public String randomCode = Faker.instance().address().zipCode();
    public String expectedCompleteMessage = "THANK YOU FOR YOUR ORDER";

    @DataProvider(name = "jsonDataProvider")
    public Object[][] getJsonData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<TestData> testDataList = objectMapper.readValue(
                new File("src/main/resources/Test_Data.json"),
                new TypeReference<List<TestData>>() {
                }
        );

        // Convert List<TestData> into Object[][]
        return testDataList.stream()
                .map(data -> new Object[]{data.Username, data.Password, data.ExpectedResult})
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "jsonDataProvider")
    public void loginTests(String username, String password, String expectedResult) {
        SoftAssert softAssert=new SoftAssert();
        loginPage.setUserName(username);
        loginPage.setPassword(password);
        ProductsPage productsPage = loginPage.clickOnLogin();
        if (expectedResult.contains("Error Message")) {
            String actualErrorMessage = loginPage.getErrorMessage();
            captureScreenshot(username);
            softAssert.assertTrue(actualErrorMessage.contains(expectedResult), "Expected: " + expectedResult + " but got: " + actualErrorMessage);

        } else {
           boolean actualtResult= productsPage.validateLogin(expectedResult);
           softAssert.assertTrue(actualtResult);
        }
        softAssert.assertAll();

    }

}
