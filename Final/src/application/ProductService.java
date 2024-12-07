package application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProductService {

    private List<Product> allProducts = new ArrayList<>();
    private final String filePath = System.getProperty("user.dir") + "/Config/products.txt";

    public List<Product> loadProducts() throws IOException {
        allProducts.clear();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Product file does not exist, returning empty list.");
            return allProducts;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
                if (fields.length >= 8) {
                    try {
                        String productID = fields[0].split(":")[1].trim();
                        String title = fields[1].split(":")[1].trim();
                        String description = fields[2].split(":")[1].trim();
                        String price = fields[3].split(":")[1].trim();
                        String sellerID = fields[4].split(":")[1].trim();
                        String status = fields[5].split(":")[1].trim();
                        String imagePath = fields[6].split(":")[1].trim();
                        String productType = fields[7].split(":")[1].trim();

                        Product product = new Product(productID, title, description, price, sellerID, status, imagePath, productType);
                        allProducts.add(product);
                    } catch (Exception e) {
                        System.err.println("Error parsing line: " + line);
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("Malformed product line: " + line);
                }
            }
        }
        return allProducts;
    }

    public void saveProducts() throws IOException {
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Product product : allProducts) {
                String formattedProduct = String.format(
                    "productID:%s | title:%s | description:%s | price:%.2f | sellerID:%s | status:%s | imagePath:%s | productType:%s",
                    product.getProductID(),
                    product.getTitle(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getSellerIDAsString(),
                    product.getStatus(),
                    product.getImagePath(),
                    product.getProductType()
                );

                writer.write(formattedProduct);
                writer.newLine();
            }
        }
    }

    public void addProduct(Product product) {
        allProducts.add(product);
    }

    public void deleteProduct(Product product) {
        allProducts.remove(product);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(allProducts); 
    }

    public List<Product> filterProducts(Predicate<Product> condition) {
        return allProducts.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }
}
