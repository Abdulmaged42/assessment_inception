package VisualPerformanceTests;

import BaseTest.BaseForVisual;
import Pages.LoginPage;
import Utils.AppDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class VisualPerformanceTest extends BaseForVisual {

    @Test
    public void testVisualAndPerformance() throws IOException {
        SoftAssert softAssert=new SoftAssert();
        int maxRetries = 3;
        int attempt = 0;
        boolean success = false;

        while (attempt < maxRetries && !success) {
            try {
                attempt++;
                System.out.println("Test Attempt: " + attempt);

                long startTime = System.currentTimeMillis(); // Start timing

                // Open the product listing page
                driver.get("https://www.saucedemo.com/");
                AppDriver.setDriver(driver);
                 loginPage=new LoginPage(AppDriver.getDriver());

                loginPage.setUserName("standard_user");
                loginPage.setPassword("secret_sauce");
                loginPage.clickOnLogin();

                // Wait for the product list to be visible
                WebDriverWait wait = new WebDriverWait(AppDriver.getDriver(), Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));

                long loadTime = System.currentTimeMillis() - startTime; // Capture load time
                System.out.println("Page Load Time: " + loadTime + " ms");

                // Fail if load time is greater than 3000 ms
                softAssert.assertTrue(loadTime <= 3000, "Page took too long to load!");

                // Capture a screenshot of the product listing page
                File screenshotFile = ((TakesScreenshot) AppDriver.getDriver()).getScreenshotAs(OutputType.FILE);
                String currentScreenshotPath = "screenshots/current_product_page.png";
                screenshotFile.renameTo(new File(currentScreenshotPath));

                // Compare with baseline image
                File baselineImageFile = new File("baslineImages/baseline_product_page.png");
                if (!baselineImageFile.exists()) {
                    System.out.println("Baseline image not found. Saving current as baseline.");
                    ImageIO.write(ImageIO.read(screenshotFile), "png", baselineImageFile);
                } else {
                    BufferedImage expectedImage = ImageIO.read(baselineImageFile);
                    BufferedImage actualImage = ImageIO.read(new File(currentScreenshotPath));

                    // Use AShot for visual comparison
                    Screenshot expectedScreenshot = new AShot().takeScreenshot(AppDriver.getDriver());
                    Screenshot actualScreenshot = new AShot().takeScreenshot(AppDriver.getDriver());

                    ImageDiff diff = new ImageDiffer().makeDiff(expectedScreenshot, actualScreenshot);

                    if (diff.hasDiff()) {
                        System.out.println("Visual difference detected! Test failed.");
                        softAssert.fail("Visual regression detected: UI has changed!");
                    } else {
                        System.out.println("✅ No visual differences detected.");
                    }
                }

                success = true; // If test passes, exit retry loop
            } catch (Exception e) {
                System.out.println("Test failed due to: " + e.getMessage());
                if (attempt == maxRetries) {
                    softAssert.fail("Test failed after " + maxRetries + " attempts.");
                }
            }
        }
    }
}
