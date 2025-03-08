package SauceDemoTests;

import BaseTest.BaseTest;
import Pages.CartPage;
import Pages.ProductDTO;
import Pages.ProductsPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ProductDTOFilterTest extends BaseTest {
    @Test
    public void testProductSortingAndCartVerification() throws IOException {
        SoftAssert softAssert=new SoftAssert();
        // Login
        loginPage.setUserName("standard_user");
        loginPage.setPassword("secret_sauce");
        ProductsPage productsPage = loginPage.clickOnLogin();

        // Sort products by "Price (low to high)"
        productsPage.sortBy("lohi");

        // Validate sorting
        List<Double> displayedPrices = productsPage.getProductPrices();
        List<Double> sortedPrices = new ArrayList<>(displayedPrices);
        Collections.sort(sortedPrices);
        softAssert.assertEquals(displayedPrices, sortedPrices, "Products are not sorted correctly!");

        // Select 3 random products
        List<ProductDTO> selectedProductDTOS = productsPage.selectRandomProducts(3);

        // Store product names & prices
        List<Map<String, Object>> cartData = new ArrayList<>();
        for (ProductDTO productDTO : selectedProductDTOS) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", productDTO.getName());
            item.put("price", productDTO.getPrice());
            cartData.add(item);
        }

        // Navigate to cart
        CartPage cartPage = productsPage.openCart();
        List<ProductDTO> cartProductDTOS = cartPage.getCartProducts();

        // Validate that cart items match selected products
        softAssert.assertEquals(cartProductDTOS, selectedProductDTOS, "Cart items do not match selected products!");

        // Remove the most expensive product
        ProductDTO mostExpensiveProductDTO = Collections.max(selectedProductDTOS, Comparator.comparing(ProductDTO::getPrice));
        cartPage.removeProduct(mostExpensiveProductDTO);
        selectedProductDTOS.remove(mostExpensiveProductDTO);

        // Validate cart update
        List<ProductDTO> updatedCartProductDTOS = cartPage.getCartProducts();
        softAssert.assertEquals(updatedCartProductDTOS, selectedProductDTOS, "Cart update after removal failed!");

        // Log results in JSON
        logResults(cartData, mostExpensiveProductDTO);
        softAssert.assertAll();
    }

    // Method to log results into JSON file
    private void logResults(List<Map<String, Object>> cartData, ProductDTO removedProductDTO) throws IOException {
        Map<String, Object> logData = new HashMap<>();
        logData.put("selected_products", cartData);
        logData.put("removed_product", removedProductDTO.getName());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("test-logs/product_test_log.json"), logData);

        System.out.println("Test results saved in JSON format.");
    }
}
