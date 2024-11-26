package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Home {
    private DataLoaderContext dataLoaderContext = new DataLoaderContext();
    private List<Product> allProducts = new ArrayList<>();

    @FXML
    private ListView<Product> productListView;

    @FXML
    private Label productDetailsLabel;

    @FXML
    private ImageView productImageView;

    @FXML
    private TextField searchField;

    @FXML
    public void initialize() {
        try {
            loadData();
            allProducts = dataLoaderContext.getDataLoader().getAllProducts();

            if (allProducts.isEmpty()) {
                productDetailsLabel.setText("No products available.");
                productImageView.setImage(null);
            } else {
                productListView.getItems().addAll(allProducts);
                productListView.getSelectionModel().selectFirst();
                showProductDetails(allProducts.get(0));
            }

            productListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    showProductDetails(newSelection);
                } else {
                    productImageView.setImage(null);
                }
            });

            // Add listener for search field to filter products as user types
            searchField.textProperty().addListener((obs, oldText, newText) -> onSearch(newText));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadProductsByType(String productType) {
        // 清空列表
        productListView.getItems().clear();

        // 遍历产品列表并添加匹配类型的产品
        for (Product product : allProducts) {
            if (product.getProductType().equalsIgnoreCase(productType)) {
                productListView.getItems().add(product);
            }
        }

        // 检查是否有匹配的产品
        if (productListView.getItems().isEmpty()) {
            productDetailsLabel.setText("No products available in this category.");
            productImageView.setImage(null);
        } else {
            productListView.getSelectionModel().selectFirst();
            showProductDetails(productListView.getSelectionModel().getSelectedItem());
        }
    }

    private void loadData() throws IOException {
        // 指定目录路径
        String basePath = System.getProperty("user.dir") + "/src/application";

        // 加载数据
        dataLoaderContext.setDataLoader(new FileDataLoader());
        dataLoaderContext.getDataLoader().loadUsers(basePath);
        dataLoaderContext.getDataLoader().loadProducts(basePath);
    }

    private boolean useFileStrategy() {
        return true; // 
    }

    private void showProductDetails(Product product) {
        productDetailsLabel.setText("Title: " + product.getTitle() +
            "\nDescription: " + product.getDescription() +
            "\nPrice: $" + product.getPrice() +
            "\nSeller: " + product.getSeller().getUsername() +
            "\nStatus: " + product.getStatus() +
            "\nProductType: " + product.getProductType());
        if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
            try {
                String absolutePath = Paths.get(System.getProperty("user.dir"), "src", product.getImagePath()).toString();
                File imageFile = new File(absolutePath);
                if (imageFile.exists()) {
                    productImageView.setImage(new Image(imageFile.toURI().toString()));
                } else {
                    productImageView.setImage(null);
                    System.out.println("Image file not found at: " + absolutePath);
                }
            } catch (Exception e) {
                productImageView.setImage(null);
                System.out.println("Failed to load image for product: " + product.getTitle() + "\n" + e.getMessage());
            }
        } else {
            productImageView.setImage(null);
        }
    }

    private void onSearch(String query) {
        List<Product> filteredProducts = allProducts.stream()
            .filter(product -> product.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    product.getDescription().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());

        productListView.getItems().clear();
        productListView.getItems().addAll(filteredProducts);

        if (filteredProducts.isEmpty()) {
            productDetailsLabel.setText("No products found.");
            productImageView.setImage(null);
        } else {
            productListView.getSelectionModel().selectFirst();
            showProductDetails(filteredProducts.get(0));
        }
    }

    @FXML
    private void onExit() {
        Alert exitConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        exitConfirmation.setTitle("Exit Confirmation");
        exitConfirmation.setHeaderText(null);

        exitConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Stage stage = (Stage) productListView.getScene().getWindow();
                stage.close();
            }
        });
    }
   
}
