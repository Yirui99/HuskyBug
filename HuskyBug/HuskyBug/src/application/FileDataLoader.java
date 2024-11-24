package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileDataLoader implements DataLoader {
    private Map<Integer, User> users = new HashMap<>();
    private List<Product> products = new ArrayList<>();

    @Override
    public void loadUsers(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("User file not found: " + path);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(" \\| ");
                if (parts.length < 5) {
                    System.err.println("Invalid line format: " + line);
                    continue;
                }

                try {
                    int userID = Integer.parseInt(parts[0].split(":")[1].trim());
                    String username = parts[1].split(":")[1].trim();
                    String password = parts[2].split(":")[1].trim();
                    String email = parts[3].split(":")[1].trim();
                    String phone = parts[4].split(":")[1].trim();

                    users.put(userID, new User(userID, username, password, email, phone));
                } catch (Exception e) {
                    System.err.println("Error parsing user line: " + line);
                }
            }
        }
    }

    @Override
    public void loadProducts(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("Product file not found: " + path);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(" \\| ");
                if (parts.length < 7) {
                    System.err.println("Invalid product line format: " + line);
                    continue;
                }

                try {
                    String productID = parseField(parts[0], "productID");
                    String title = parseField(parts[1], "title");
                    String description = parseField(parts[2], "description");
                    double price = Double.parseDouble(parseField(parts[3], "price"));
                    int sellerID = Integer.parseInt(parseField(parts[4], "sellerID"));
                    String status = parseField(parts[5], "status");
                    String imagePath = parseField(parts[6], "imagePath");

                    User seller = users.get(sellerID);
                    if (seller != null) {
                        products.add(new Product(productID, title, description, price, seller, status, imagePath));
                    } else {
                        System.err.println("Seller with ID " + sellerID + " not found for product: " + title);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing product line: " + line);
                }
            }
        }
    }

    private String parseField(String part, String fieldName) {
        String[] keyValue = part.split(":");
        if (keyValue.length < 2 || keyValue[1].trim().isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Field " + fieldName + " is missing or invalid in part: " + part);
        }
        return keyValue[1].trim();
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
	    return products.stream()
	            .filter(product -> product.getTitle().toLowerCase().contains(query.toLowerCase()) ||
	                    product.getDescription().toLowerCase().contains(query.toLowerCase()))
	            .collect(Collectors.toList());
	}

}
