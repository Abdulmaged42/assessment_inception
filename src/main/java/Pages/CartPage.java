package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static Utils.Util.clickEl;

public class CartPage {
    private WebDriver driver;
    private By checkOutLocator = By.id("checkout");

    private By cartItemNames = By.className("inventory_item_name");
    private By cartItemPrices = By.className("inventory_item_price");
    private By removeButtons = By.xpath("//button[contains(text(),'Remove')]");
    public CartPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public CheckOutPage clickOnCheckOut() {
        clickEl(checkOutLocator);
        return new CheckOutPage(driver);
    }
    public List<ProductDTO> getCartProducts() {
        List<WebElement> nameElements = driver.findElements(cartItemNames);
        List<WebElement> priceElements = driver.findElements(cartItemPrices);

        return nameElements.stream().map(e -> new ProductDTO(
                e.getText(),
                Double.parseDouble(priceElements.get(nameElements.indexOf(e)).getText().replace("$", "")),
                null
        )).collect(Collectors.toList());
    }

    public void removeProduct(ProductDTO productDTO) {
        List<WebElement> nameElements = driver.findElements(cartItemNames);
        List<WebElement> removeButtonsList = driver.findElements(removeButtons);

        for (int i = 0; i < nameElements.size(); i++) {
            if (nameElements.get(i).getText().equals(productDTO.getName())) {
                removeButtonsList.get(i).click();
                break;
            }
        }
    }
}
