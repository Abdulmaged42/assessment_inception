package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static Pages.LoginPage.detectSlowLoadingBehavior;
import static Utils.Util.*;

public class ProductsPage {
    private WebDriver driver;
    private By itemLocator = By.xpath("//button[@id='add-to-cart-sauce-labs-bike-light']");
    private By cartLocator = By.xpath("//div[@id='shopping_cart_container']");
    private By title = By.xpath("//span[contains(text(),'Products')]");
    private By sortDropdown = By.className("product_sort_container");
    private By productNames = By.className("inventory_item_name");
    private By productPrices = By.className("inventory_item_price");
    private By addToCartButtons = By.xpath("//button[contains(text(),'Add to cart')]");
    private By cartButton = By.className("shopping_cart_link");

    public ProductsPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public boolean checkTitle() {
        return driver.findElement(title).isDisplayed();
    }

    public void addToCart() {
        clickEl(itemLocator);
    }

    public CartPage openCart() {
        clickEl(cartLocator);
        return new CartPage(driver);
    }

    public boolean getBrokenImages() {
        List<WebElement> images = driver.findElements(By.tagName("img"));
        System.out.println("Total images found: " + images.size());
        boolean broken = false;
        int brokenImagesCount = 0;

        for (WebElement img : images) {
            String imageUrl = img.getAttribute("src");
            if (imageUrl == null || imageUrl.isEmpty()) {
                System.out.println("Image with missing src attribute.");
                brokenImagesCount++;
                continue;
            }

            // Check if the image URL is accessible
            if (!isImageValid(imageUrl)) {
                System.out.println("Broken image found: " + imageUrl);
                brokenImagesCount++;
            }
        }
        if (brokenImagesCount > 0) {
            broken = true;
        } else {
            return broken;
        }
        return broken;
    }

    public boolean validateLogin(String state) {
        boolean actualResult = false;
        if (state.contains("success")) {
            actualResult = checkTitle();
        } else if (state.contains("Validate if images are broken after login")) {
            actualResult = getBrokenImages();
        } else {
            actualResult = detectSlowLoadingBehavior();
        }
        return actualResult;
    }

    public List<ProductDTO> selectRandomProducts(int count) {
        List<WebElement> nameElements = driver.findElements(productNames);
        List<WebElement> priceElements = driver.findElements(productPrices);
        List<WebElement> buttonElements = driver.findElements(addToCartButtons);

        List<ProductDTO> allProductDTOS = new ArrayList<>();
        for (int i = 0; i < nameElements.size(); i++) {
            allProductDTOS.add(new ProductDTO(nameElements.get(i).getText(),
                    Double.parseDouble(priceElements.get(i).getText().replace("$", "")),
                    buttonElements.get(i)));
        }

        // Select random products
        Collections.shuffle(allProductDTOS);
        List<ProductDTO> selectedProductDTOS = allProductDTOS.subList(0, count);

        // Click "Add to Cart" for selected products
        for (ProductDTO productDTO : selectedProductDTOS) {
            productDTO.getAddToCartButton().click();
        }

        return selectedProductDTOS;
    }

    public void sortBy(String sortType) {
        Select dropdown = new Select(driver.findElement(sortDropdown));
        if (sortType.equalsIgnoreCase("lohi")) {
            dropdown.selectByValue("lohi");
        }
    }

    public List<Double> getProductPrices() {
        List<WebElement> priceElements = driver.findElements(productPrices);
        return priceElements.stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }
}
