package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ProductDetailsController {

    @FXML
    private ImageView productImageView;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField stockField;

    @FXML
    private TextField sellerField;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Button buyButton;

    private Product product;

    @FXML
    private void handleBuyAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Payment.fxml"));
            Parent root = loader.load();
            PaymentController controller = loader.getController();
            controller.setProduct(product);
            Stage stage = (Stage) buyButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Payment");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setProduct(Product product) {
        this.product = product; 
        nameField.setText(product.getTitle());
        descriptionField.setText(product.getDescription());
        priceField.setText(String.valueOf(product.getPrice()));
        stockField.setText(product.getStatus());
        sellerField.setText(product.getSeller().getUsername());
        nameField.setEditable(false);
        descriptionField.setEditable(false);
        priceField.setEditable(false);
        stockField.setEditable(false);
        sellerField.setEditable(false);

        if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
            productImageView.setImage(new Image(product.getImagePath()));
        } else {
            productImageView.setImage(null);
        }
    }
}
