package application;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


    public void loadProducts() {
        try {
            productService.loadProducts();
        } catch (IOException e) {
            System.err.println("Error loading products: " + e.getMessage());
        }
    }


    public void saveProducts() {
        try {
            productService.saveProducts();
        } catch (IOException e) {
            System.err.println("Error saving products: " + e.getMessage());
        }
    }


    public void addProduct(Product product) {
        productService.addProduct(product);
        saveProducts();
    }


    public void deleteProduct(Product product) {
        productService.deleteProduct(product);
        saveProducts();
    }


    public List<Product> getProductsByCategory(String category) {
        return productService.filterProducts(product -> product.getProductType().equalsIgnoreCase(category));
    }


    public List<Product> searchProducts(String keyword) {
        return productService.filterProducts(product ->
                product.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                product.getDescription().toLowerCase().contains(keyword.toLowerCase()));
    }


    public List<Product> filterProducts(Predicate<Product> condition) {
        return productService.filterProducts(condition);
    }
}
