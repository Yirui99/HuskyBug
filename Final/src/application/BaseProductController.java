package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;

public abstract class BaseProductController {

    protected final ProductController productController;

    @FXML
    protected ListView<Product> productListView;

    @FXML
    protected Label productDetailsLabel;

    @FXML
    protected ImageView productImageView;

    public BaseProductController() {
        ProductService productService = new ProductService();
        this.productController = new ProductController(productService);
    }

    public abstract String getProductType();

    @FXML
    public void initialize() {
        productController.loadProducts();
        loadProductsByType(getProductType());

        productListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showProductDetails(newSelection);
            }
        });
    }

    protected void loadProductsByType(String productType) {
        List<Product> products = productController.getProductsByCategory(productType);
        productListView.getItems().setAll(products);

        if (products.isEmpty()) {
            productDetailsLabel.setText("No products available in this category.");
            productImageView.setImage(null);
        } else {
            productListView.getSelectionModel().selectFirst();
            showProductDetails(productListView.getSelectionModel().getSelectedItem());
        }
    }

    protected void showProductDetails(Product product) {
        productDetailsLabel.setText("Title: " + product.getTitle() +
            "\nDescription: " + product.getDescription() +
            "\nPrice: $" + product.getPrice() +
            "\nSeller: " + (product.getSeller() != null ? product.getSeller().getUsername() : "Unknown") +
            "\nStatus: " + product.getStatus() +
            "\nProductType: " + product.getProductType());
        if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
            productImageView.setImage(new Image("file:" + product.getImagePath()));
        } else {
            productImageView.setImage(null);
        }
    }

    @FXML
    protected void onExit() {
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
