package application;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class AddItem {

    private DataLoaderContext dataLoaderContext = new DataLoaderContext();
    
    private List<Product> allProducts = new ArrayList<>();
    
    private ProductController productController;



    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, String> productIDColumn, sellerIDColumn, titleColumn, descriptionColumn, priceColumn, statusColumn, productTypeColumn;

    @FXML
    private TextField titleField, descriptionField, priceField, statusField, studentIdField;

    @FXML 
    private ComboBox<String> comboBox;

    @FXML
    private Button uploadButton;

    @FXML
    private ImageView imageView;
    
    @FXML
    private TextField textField;

    @FXML
    public void initialize() {       
        if (productTableView == null) {
            System.out.println("productTableView 未初始化");
        } else {
            System.out.println("productTableView 已初始化");
        }
        
     // 初始化 ProductController
        ProductService productService = new ProductService();
        productController = new ProductController(productService);

        // 加载商品数据
        productController.loadProducts();

        // 初始化表格和下拉框
        comboBox.setItems(FXCollections.observableArrayList("Groceries", "Clothing", "Beauty", "Computers", "Cellphones", "Others"));
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        sellerIDColumn.setCellValueFactory(new PropertyValueFactory<>("sellerIDAsString"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("priceAsString"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));

        // 设置表格数据
        productTableView.setItems(FXCollections.observableArrayList(productController.getAllProducts()));

    }
    

    @FXML
    public void Add(ActionEvent event) {
        try {
            // 输入校验
            if (titleField.getText().isEmpty() || descriptionField.getText().isEmpty() || priceField.getText().isEmpty() || comboBox.getValue() == null) {
                System.out.println("fill all field！");
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceField.getText());
            } catch (NumberFormatException e) {
                System.out.println("price must be number！");
                return;
            }

            // 获取输入值
            String title = titleField.getText();
            String description = descriptionField.getText();
            String status = statusField.getText();
            String productType = comboBox.getValue();
            String sellerID = studentIdField.getText();

            Product newProduct = new Product(
                    String.valueOf(productController.getAllProducts().size() + 1),
                    title, description, String.valueOf(price), sellerID, status, "", productType
            );

            // 添加商品
            productController.addProduct(newProduct);

            // 更新表格
            productTableView.setItems(FXCollections.observableArrayList(productController.getAllProducts()));
            System.out.println("商品添加成功！");
        } catch (Exception e) {
            System.err.println("添加商品时出错：" + e.getMessage());
        }
    }

    private Product getSelectedProduct() {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            System.out.println("请先选择一个商品！");
        }
        return selectedProduct;
    }

    @FXML
    public void Update(ActionEvent event) {
        Product selectedProduct = getSelectedProduct();
        if (selectedProduct != null) {
            try {
                selectedProduct.setTitle(titleField.getText());
                selectedProduct.setDescription(descriptionField.getText());
                selectedProduct.setPrice(Double.parseDouble(priceField.getText()));
                selectedProduct.setStatus(statusField.getText());
                selectedProduct.setProductType(comboBox.getValue());
                selectedProduct.setImagePath(textField.getText());

                productController.saveProducts(); 
                productTableView.refresh(); 
                System.out.println("update success！");
            } catch (Exception e) {
                System.err.println("update error：" + e.getMessage());
            }
        }
    }

    @FXML
    public void Delete(ActionEvent event) {
        Product selectedProduct = getSelectedProduct();
        if (selectedProduct != null) {
            productController.deleteProduct(selectedProduct);
            productTableView.setItems(FXCollections.observableArrayList(productController.getAllProducts()));
            System.out.println("delete success ！");
        }
    }

    


    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            textField.setText(selectedFile.getAbsolutePath());
            System.out.println("imagepath：" + selectedFile.getAbsolutePath());
        } else {
            System.out.println("no image！");
        }
    }
  

    @FXML

    public String getComboBoxInfo(ActionEvent event)
    {
        System.out.println("hi");
        return comboBox.getValue();
    }
}


