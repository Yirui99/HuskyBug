package application;

public class Product {
    private String productID;
    private String title;
    private String description;
    private double price;
    private User seller; // 修改为 User 类型
    private String status;
    private String imagePath;
	private String productType;

    public Product(String productID, String title, String description, double price, User seller, String status, String imagePath, String productType) {
    	this.productID = productID;
        this.title = title;
        this.description = description;
        this.price = price;
        this.seller = seller;
        this.status = status;
        this.imagePath = imagePath;
        this.productType = productType;
    }

    public Product(String productID, String title, String description, String price, String seller, String status, String imagePath, String productType) {
     	this.productID = productID;
        this.title = title;
        this.description = description;
        this.status = status;
        this.imagePath = imagePath;
        this.productType = productType;
        
        this.price = Double.parseDouble(price);
        this.seller =  new User(Integer.parseInt(seller),"","","","");
        }
    
    public String getProductType() {
    	return productType;
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

    public String getPriceAsString() {
        System.out.println("getPriceAsString 被调用");
        return String.format("$%.2f", price);
    }

    public User getSeller() { // 返回 User 对象
        return seller;
    }

    public String getSellerIDAsString() {
    return seller != null ? String.valueOf(seller.getUserID()) : "Unknown";
}

    public String getStatus() {
        return status;
    }

    public String getImagePath() {
        return imagePath;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    @Override
    public String toString() {
        return title + " - $" + price + " (" + status + ")";
    }
}
