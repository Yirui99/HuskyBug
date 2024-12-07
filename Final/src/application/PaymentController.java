package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PaymentController {

    @FXML
    private TableView<Item> tableView;

    @FXML
    private TableColumn<Item, String> nameColumn;

    @FXML
    private TableColumn<Item, Double> unitPriceColumn;

    @FXML
    private TableColumn<Item, Integer> numberColumn;

    @FXML
    private TextArea totalPriceField;

    @FXML
    private Button buyButton;

    @FXML
    private Button cancelButton;

    private ObservableList<Item> itemList = FXCollections.observableArrayList();

    private Product product; 
    
    

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        tableView.setItems(itemList);
    }


    public void setProduct(Product product) {
        this.product = product;


        Item item = new Item(product.getTitle(), product.getPrice(), 1);
        itemList.add(item);

        totalPriceField.setText(String.valueOf(product.getPrice()));
    }

    @FXML
    private void handleBuyAction() {
        if (product != null) {

            product.setStatus("sold");
            System.out.println("Product " + product.getTitle() + " status updated to 'sold'.");


            Stage stage = (Stage) buyButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void handleCancelAction() {

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public static class Item {
        private final String name;
        private final double unitPrice;
        private final int number;

        public Item(String name, double unitPrice, int number) {
            this.name = name;
            this.unitPrice = unitPrice;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public int getNumber() {
            return number;
        }
    }
}
