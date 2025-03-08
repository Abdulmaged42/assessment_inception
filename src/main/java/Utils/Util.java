package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Util {
    private static WebDriver driver;

    public static void waitForEl(By byEl) {
        new WebDriverWait(AppDriver.getDriver(), Duration.of(30, ChronoUnit.SECONDS)).until(ExpectedConditions.presenceOfElementLocated(byEl));
    }

    public static void clickEl(By byEl) {
        waitForEl(byEl);
        new WebDriverWait(AppDriver.getDriver(), Duration.of(30, ChronoUnit.SECONDS)).until(ExpectedConditions.presenceOfElementLocated(byEl)).click();
    }

    public static void sendKeysEl(By byEl, String text) {
        waitForEl(byEl);
        AppDriver.getDriver().findElement(byEl).sendKeys(text);
    }

    public static void captureScreenshot(String username) {
        // Convert WebDriver instance to TakesScreenshot
        TakesScreenshot ts = (TakesScreenshot) AppDriver.getDriver();

        // Capture screenshot as a File
        File srcFile = ts.getScreenshotAs(OutputType.FILE);

        // Define destination path with timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = "screenshots/" + username + "_" + timestamp + ".png";

        try {
            // Save the file to the specified location
            FileHandler.copy(srcFile, new File(filePath));
            System.out.println("Screenshot saved at: " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    public static boolean isImageValid(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            connection.disconnect();

            return (responseCode == 200);
        } catch (Exception e) {
            System.out.println("Error checking image: " + imageUrl + " | Exception: " + e.getMessage());
            return false;
        }
    }

}
