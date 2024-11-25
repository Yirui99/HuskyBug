package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.event.ActionEvent; 
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class SideNavigateController {

    @FXML
    private ImageView exit;

    @FXML
    private StackPane contentArea;

    @FXML
    private TextField searchField;

    @FXML
    private Button addItem;


    @FXML
    public void initialize() {       
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("Home.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Home button clicked!");
    }

    @FXML
    public void exit(ActionEvent event) {
        exit.setOnMouseClicked(e -> {
            System.exit(0);
        });  	
    }

    // 公共加载方法
    private void loadAndSetScene(ProductControllerFactory factory, String fxmlFile, String productType) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            
            // 使用工厂创建控制器实例
            BaseProductController controller = factory.createController();
            loader.setController(controller);

            Parent fxml = loader.load();

            // 设置 ProductType
            controller.loadProductsByType(productType);

            // 更新界面内容
            contentArea.getChildren().clear();
            contentArea.getChildren().add(fxml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Beauty(ActionEvent event) {
        loadAndSetScene(new BeautyControllerFactory(), "Beauty.fxml","Beauty");
    }

    @FXML
    void Computers(ActionEvent event) {
        loadAndSetScene(new ComputersControllerFactory(), "Computers.fxml","Computers");
    }

    @FXML
    void Others(ActionEvent event) {
        loadAndSetScene(new OthersControllerFactory(), "Others.fxml","Others");
    }

    @FXML
    void Cellphones(ActionEvent event) {
        loadAndSetScene(new CellphonesControllerFactory(), "Cellphones.fxml","Cellphones");
    }

    @FXML
    void Clothing(ActionEvent event) {
        loadAndSetScene(new ClothingControllerFactory(), "Clothing.fxml","Clothing");
        System.out.println("Clothing button clicked!");
    }

    @FXML
    void Groceries(ActionEvent event) {
        loadAndSetScene(new BeautyControllerFactory(), "Groceries.fxml","Groceries");
        System.out.println("Groceries button clicked!");
    }


    @FXML
    void onSearch(ActionEvent event)
    {
        ;
    }

    @FXML
    void Home(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("Home.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("Home button clicked!");
    }

    @FXML
    void addItem(ActionEvent event) {
        try {
            SceneManager.getInstance().changeScene("AddItem.fxml");
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("add button clicked!");
    }
}
