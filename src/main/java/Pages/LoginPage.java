package Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static Utils.Util.clickEl;
import static Utils.Util.sendKeysEl;

public class LoginPage {
    private static WebDriver driver;

    private By userNameLocator = By.id("user-name");
    private By passwordLocator = By.id("password");
    private By loginButtonLocator = By.id("login-button");
    private static By errorMessage = By.xpath("//*[@data-test='error']");

    public LoginPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public static String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public void setUserName(String userName) {
        sendKeysEl(userNameLocator, userName);
    }

    public void setPassword(String password) {
        sendKeysEl(passwordLocator, password);
    }

    public ProductsPage clickOnLogin() {
        clickEl(loginButtonLocator);
        return new ProductsPage(driver);
    }

    public static boolean detectSlowLoadingBehavior() {
        boolean actualResult = false;
        long startTime = System.currentTimeMillis();
        int maxLoadTime = 3000;

        // Wait for a critical element to be visible (e.g., inventory container)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement inventoryContainer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("inventory_container"))
        );

        // Record end time after element appears
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;

        System.out.println("Page load time: " + loadTime + " ms");

        // Assert that the page loads within the acceptable time limit
        if (loadTime <= maxLoadTime) {
            actualResult = true;
        } else {
            return actualResult;
        }
        return actualResult;
    }
}
