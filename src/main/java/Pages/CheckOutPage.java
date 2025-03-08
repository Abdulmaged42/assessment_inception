package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static Utils.Util.clickEl;
import static Utils.Util.sendKeysEl;

public class CheckOutPage {
    private WebDriver driver;

    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By zipField = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By errorMessage = By.cssSelector("[data-test='error']");
    private By itemPrices = By.className("inventory_item_price");
    private By taxPrice = By.className("summary_tax_label");
    private By totalPrice = By.className("summary_total_label");
    private By finishButton = By.id("finish");
    private By confirmationMessage = By.cssSelector(".complete-header");

    public CheckOutPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void enterCheckoutDetails(String firstName, String lastName, String zip) {
        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(zipField).clear();
        driver.findElement(zipField).sendKeys(zip);
    }
    public void clickOnContinue() {
        clickEl(continueButton);
    }

    public void clickOnFinish() {
        clickEl(finishButton);
    }
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public double calculateTotalPrice() {
        List<WebElement> priceElements = driver.findElements(itemPrices);
        double subtotal = priceElements.stream()
                .mapToDouble(e -> Double.parseDouble(e.getText().replace("$", "")))
                .sum();

        double tax = Double.parseDouble(driver.findElement(taxPrice).getText().replace("Tax: $", ""));
        return subtotal + tax;
    }
    public double getDisplayedTotalPrice() {
        return Double.parseDouble(driver.findElement(totalPrice).getText().replace("Total: $", ""));
    }

    public String getConfirmationMessage() {
        return driver.findElement(confirmationMessage).getText();
    }
}
