package ApiUiValidationTests;

import BaseTest.mockBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import java.util.List;
import java.util.Map;

public class ApiUiValidationTest extends mockBase {
    @Test
    public void testApiUiValidation() {
        SoftAssert softAssert = new SoftAssert();
        // Step 1: Fetch users from API
        Response apiResponse = RestAssured.get("https://reqres.in/api/users?page=2");

        // Validate API response status
        softAssert.assertEquals(apiResponse.getStatusCode(), 200, "API request failed!");

        // Extract user list from API response
        List<Map<String, Object>> apiUsers = apiResponse.jsonPath().getList("data");

        // Step 2: Load the same API URL in Selenium (JSON UI)
        driver.get("https://reqres.in/api/users?page=2");

        // Step 3: Extract JSON text from UI
        WebElement jsonBody = driver.findElement(By.tagName("pre"));
        String uiJsonText = jsonBody.getText();

        // Convert UI JSON string to JSON object
        JSONObject uiJson = new JSONObject(uiJsonText);
        JSONArray uiUsers = uiJson.getJSONArray("data");

        // Step 4: Compare API vs UI JSON
        softAssert.assertEquals(apiUsers.size(), uiUsers.length(), "User count mismatch!");

        for (int i = 0; i < apiUsers.size(); i++) {
            JSONObject uiUser = uiUsers.getJSONObject(i);
            Map<String, Object> apiUser = apiUsers.get(i);

            // Validate user details
            softAssert.assertEquals(uiUser.getInt("id"), (int) apiUser.get("id"), "ID mismatch for user " + i);
            softAssert.assertEquals(uiUser.getString("email"), apiUser.get("email"), "Email mismatch for user " + i);
            softAssert.assertEquals(uiUser.getString("first_name"), apiUser.get("first_name"), "First Name mismatch for user " + i);
            softAssert.assertEquals(uiUser.getString("last_name"), apiUser.get("last_name"), "Last Name mismatch for user " + i);
        }

        System.out.println("✅ API data matches UI JSON response!");
    }
}

