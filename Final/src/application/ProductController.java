package application;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 获取所有商品
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // 加载商品数据
    public void loadProducts() {
        try {
            productService.loadProducts();
        } catch (IOException e) {
            System.err.println("Error loading products: " + e.getMessage());
        }
    }

    // 保存商品数据
    public void saveProducts() {
        try {
            productService.saveProducts();
        } catch (IOException e) {
            System.err.println("Error saving products: " + e.getMessage());
        }
    }

    // 添加商品
    public void addProduct(Product product) {
        productService.addProduct(product);
        saveProducts();
    }

    // 删除商品
    public void deleteProduct(Product product) {
        productService.deleteProduct(product);
        saveProducts();
    }

    // 获取特定分类的商品
    public List<Product> getProductsByCategory(String category) {
        return productService.filterProducts(product -> product.getProductType().equalsIgnoreCase(category));
    }

    // 按关键字搜索商品
    public List<Product> searchProducts(String keyword) {
        return productService.filterProducts(product ->
                product.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                product.getDescription().toLowerCase().contains(keyword.toLowerCase()));
    }

    // 按条件筛选商品
    public List<Product> filterProducts(Predicate<Product> condition) {
        return productService.filterProducts(condition);
    }
}
