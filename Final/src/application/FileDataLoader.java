package application;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileDataLoader implements DataLoader {
    private static final int USER_FIELDS_COUNT = 5;       // 用户字段数量
    private static final int PRODUCT_FIELDS_COUNT = 8;    // 产品字段数量

    private Map<Integer, User> users = new HashMap<>();
    private List<Product> products = new ArrayList<>();
    private Map<String,User> usenameUserMap = new HashMap<>();
    private final String userFilePath = System.getProperty("user.dir") + "/Config/users.txt";
    private final String productFilePath = System.getProperty("user.dir") + "/Config/products.txt";


    @Override
    public void loadUsers(String path) throws IOException {
    	    List<String> lines = readFileLines(userFilePath);

    	 for (int i = 0; i < lines.size(); i++) {
    	        String line = lines.get(i);
    	        parseUserLine(line);
    	    }
    }

    @Override
    public void loadProducts(String path) throws IOException {
        List<String> lines = readFileLines(productFilePath);

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			parseProductLine(line);
		}
    }


    // Unified file reading method
    private List<String> readFileLines(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File not found: " + path);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
            return lines;
        }
    }


    // Parsing individual user line
    private void parseUserLine(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < USER_FIELDS_COUNT) {
            System.err.println("Invalid user line format: " + line);
            return;
        }
        try {
            int userID = parseIntField(parts[0], "userID");
            String username = parseField(parts[1], "username");
            String password = parseField(parts[2], "password");
            String email = parseField(parts[3], "email");
            String phone = parseField(parts[4], "phone");
            User currentUser = new User(userID, username, password, email, phone);
            users.put(userID, currentUser );
            usenameUserMap.put(username, currentUser);
        } catch (Exception e) {
            System.err.println("Error parsing user line: " + line);
        }
    }

    // Parsing individual product line
    private void parseProductLine(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < PRODUCT_FIELDS_COUNT) {
            System.err.println("Invalid product line format: " + line);
            return;
        }
        try {
            String productID = parseField(parts[0], "productID");
            String title = parseField(parts[1], "title");
            String description = parseField(parts[2], "description");
            double price = Double.parseDouble(parseField(parts[3], "price"));
            int sellerID = parseIntField(parts[4], "sellerID");
            String status = parseField(parts[5], "status");
            String imagePath = parseField(parts[6], "imagePath");
            String productType = parseField(parts[7], "productType");

            User seller = users.get(sellerID);
            if (seller != null) {
                products.add(new Product(productID, title, description, price, seller, status, imagePath, productType));
            } else {
                System.err.println("Seller with ID " + sellerID + " not found for product: " + title);
            }
        } catch (Exception e) {
            System.err.println("Error parsing product line: " + line);
        }
    }

    // Field parsing helper method
    private String parseField(String part, String fieldName) {
        String[] keyValue = part.split(":");
        if (keyValue.length < 2 || keyValue[1].trim().isEmpty()) {
            throw new IllegalArgumentException("Field " + fieldName + " is missing or invalid in part: " + part);
        }
        return keyValue[1].trim();
    }

    // Integer field parsing helper method
    private int parseIntField(String part, String fieldName) {
        return Integer.parseInt(parseField(part, fieldName));
    }

    @Override
    public Map<Integer, User> getAllUsers() {
        return users;
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public List<Product> getPrioProducts() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public List<Product> searchProducts(String query) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (isQueryInProduct(query, product)) {
                result.add(product);
            }
        }
        return result;
    }
    @Override
    public Map<String,User> getUsernameUser(){
    	return usenameUserMap;
    }
    private boolean isQueryInProduct(String query, Product product) {
        String lowerCaseQuery = query.toLowerCase();
        return containsIgnoreCase(product.getTitle(), lowerCaseQuery) ||
               containsIgnoreCase(product.getDescription(), lowerCaseQuery);
    }

    private boolean containsIgnoreCase(String source, String target) {
        if (source != null && target != null) {
            return source.toLowerCase().contains(target);
        }
        return false;
    }
    public void writeToFile(String line) throws IOException {
        File file = new File(userFilePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(line);
            writer.newLine(); // 换行
        }
    }


}
