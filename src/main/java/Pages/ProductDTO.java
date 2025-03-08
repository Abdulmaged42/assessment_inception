package Pages;

import org.openqa.selenium.WebElement;

public class ProductDTO {
    private String name;
    private double price;
    private WebElement addToCartButton;

    public ProductDTO(String name, double price, WebElement addToCartButton) {
        this.name = name;
        this.price = price;
        this.addToCartButton = addToCartButton;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public WebElement getAddToCartButton() {
        return addToCartButton;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProductDTO) {
            ProductDTO other = (ProductDTO) obj;
            return this.name.equals(other.name) && this.price == other.price;
        }
        return false;
    }
}
