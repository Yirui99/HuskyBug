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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MarketplaceController {
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
                }
            });

            searchField.setOnAction(event -> onSearch());
        } catch (IOException e) {
            e.printStackTrace();
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
            productImageView.setImage(new Image("file:" + product.getImagePath()));
        } else {
            productImageView.setImage(null);
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

    @FXML
    private void onSearch() {
        if (allProducts.isEmpty()) {
            productDetailsLabel.setText("No products available.");
            return;
        }

        String query = searchField.getText().toLowerCase();
        List<Product> filteredProducts = allProducts.stream()
        .filter(product -> product.getTitle().toLowerCase().contains(query) ||
            product.getDescription().toLowerCase().contains(query))
        .collect(Collectors.toList());

        productListView.getItems().clear();
        productListView.getItems().addAll(filteredProducts);

        if (filteredProducts.isEmpty()) {
            productDetailsLabel.setText("No products found.");
            productImageView.setImage(null);
        } else {
            showProductDetails(filteredProducts.get(0));
        }
    }
}
