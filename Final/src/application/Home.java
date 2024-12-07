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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Home {
    private DataLoaderContext dataLoaderContext = new DataLoaderContext();
    private List<Product> allProducts = new ArrayList<>();
    private List<Product> currentFilteredProducts = new ArrayList<>();

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
            currentFilteredProducts = new ArrayList<>(allProducts);

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

            productListView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) { 
                    Product selectedProduct = productListView.getSelectionModel().getSelectedItem();
                    if (selectedProduct != null) {
                        openProductDetails(selectedProduct);
                    }
                }
            });

            searchField.textProperty().addListener((obs, oldText, newText) -> onSearch(newText));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openProductDetails(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductDetails.fxml"));
            Parent root = loader.load();
            ProductDetailsController controller = loader.getController();
            controller.setProduct(product);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Product Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void loadAllProducts() {
        loadProductsByType(null);
    }

    @FXML
    private void loadComputers() {
        loadProductsByType("Computers");
    }

    @FXML
    private void loadCellphones() {
        loadProductsByType("Cellphones");
    }

    @FXML
    private void loadGroceries() {
        loadProductsByType("Groceries");
    }

    @FXML
    private void loadClothing() {
        loadProductsByType("Clothing");
    }

    @FXML
    private void loadBeauty() {
        loadProductsByType("Beauty");
    }

    @FXML
    private void loadOthers() {
        loadProductsByType("Others");
    }

    @FXML
    private void addItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddItem.fxml"));
            Parent root = loader.load();
            AddItem addItemController = loader.getController();

            addItemController.setOnProductAdded(product -> {
                allProducts.add(product); 
                loadAllProducts();       
            });

            Stage addItemStage = new Stage();
            addItemStage.initModality(Modality.APPLICATION_MODAL);
            addItemStage.initStyle(StageStyle.DECORATED);
            addItemStage.setTitle("Add New Item");
            addItemStage.setScene(new Scene(root));
            addItemStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadProductsByType(String productType) {
        productListView.getItems().clear();

        if (productType == null) {
            currentFilteredProducts = new ArrayList<>(allProducts);
        } else {
            currentFilteredProducts = allProducts.stream()
                    .filter(product -> product.getProductType().equalsIgnoreCase(productType))
                    .collect(Collectors.toList());
        }

        productListView.getItems().addAll(currentFilteredProducts);

        if (currentFilteredProducts.isEmpty()) {
            productDetailsLabel.setText("No products available in this category.");
            productImageView.setImage(null);
        } else {
            productListView.getSelectionModel().selectFirst();
            showProductDetails(productListView.getSelectionModel().getSelectedItem());
        }
    }

    private void loadData() throws IOException {
        String basePath = System.getProperty("user.dir") + "/src/application";
        dataLoaderContext.setDataLoader(new FileDataLoader());
        dataLoaderContext.getDataLoader().loadUsers(basePath);
        dataLoaderContext.getDataLoader().loadProducts(basePath);
    }

    private boolean useFileStrategy() {
        return true;
    }

    private void showProductDetails(Product product) {
        productDetailsLabel.setText("Title: " + product.getTitle() +
                "\nPrice: $" + product.getPrice() +
                "\nStatus: " + product.getStatus());
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
        List<Product> filteredProducts = currentFilteredProducts.stream()
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

}
