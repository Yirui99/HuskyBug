package application;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DataLoader {
    void loadUsers(String path) throws IOException;
    void loadProducts(String path) throws IOException;
    Map<Integer, User> getAllUsers();
    List<Product> getAllProducts();
    List<Product> searchProducts(String query);
    public List<Product> getPrioProducts();
    Map<String, User> getUsernameUser();
}
