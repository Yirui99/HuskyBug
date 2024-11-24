package application;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MarketPlaceController {
    private DataLoader dataLoader = new FileDataLoader();

    @FXML
    private ListView<Product> productListView;

    @FXML
    private Label productDetailsLabel;

    @FXML
    private ImageView productImageView;

    @FXML
    private TextField searchField;

    private List<Product> allProducts;

    @FXML
    public void initialize() {
        String userFilePath = System.getProperty("user.dir") + "\\HuskyBug\\src\\application\\users.txt";
        String productFilePath = System.getProperty("user.dir") + "\\HuskyBug\\src\\application\\products.txt";

        try {
            dataLoader.loadUsers(userFilePath);
            dataLoader.loadProducts(productFilePath);

            allProducts = dataLoader.getAllProducts();
            productListView.getItems().addAll(allProducts);

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

    @FXML
    private void onSearch() {
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
        }
    }

    private void showProductDetails(Product product) {
        productDetailsLabel.setText("Title: " + product.getTitle() +
                "\nDescription: " + product.getDescription() +
                "\nPrice: $" + product.getPrice() +
                "\nSeller: " + product.getSeller().getUsername() +
                "\nStatus: " + product.getStatus());
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
}
