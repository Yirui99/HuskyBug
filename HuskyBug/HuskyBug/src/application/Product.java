package application;

public class Product {
    private String productID;
    private String title;
    private String description;
    private double price;
    private User seller; // 修改为 User 类型
    private String status;
    private String imagePath;

    public Product(String productID, String title, String description, double price, User seller, String status, String imagePath) {
        this.productID = productID;
        this.title = title;
        this.description = description;
        this.price = price;
        this.seller = seller;
        this.status = status;
        this.imagePath = imagePath;
    }

    public String getProductID() {
        return productID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public User getSeller() { // 返回 User 对象
        return seller;
    }

    public String getStatus() {
        return status;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return title + " - $" + price + " (" + status + ")";
    }
}
