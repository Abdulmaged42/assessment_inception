package Utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static Utils.Util.captureScreenshot;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // Get WebDriver from BaseTest
        Object testClass = result.getInstance();
        WebDriver driver = AppDriver.getDriver();

        if (driver != null) {
            captureScreenshot(result.getName()); // Capture screenshot
        }
    }
}
