package application;

import java.io.IOException;
import java.util.Map;

public class RegisterService {

    private Map<Integer, User> userStore; 
    private Map<String, User> usernameUserMap; 
    private int newestID; 
    private FileDataLoader fileDataLoader; 

    public RegisterService() {
        this.fileDataLoader = new FileDataLoader();
        this.newestID = 0; 
        loadUsersFromFile(); 
    }

    public synchronized boolean registerUser(String username, String password, String email, String phone) {
        if (username == null || username.isEmpty()) {
            System.out.println("Username is required.");
            return false;
        }
        if (password == null || password.isEmpty()) {
            System.out.println("Password is required.");
            return false;
        }
        if (email == null || !isValidEmail(email)) {
            System.out.println("Invalid email address.");
            return false;
        }
        if (phone == null || !isValidPhone(phone)) {
            System.out.println("Invalid phone number.");
            return false;
        }

        if (usernameUserMap.containsKey(username)) {
            System.out.println("Username already exists.");
            return false;
        }

        User newUser = new User(newestID, username, password, email, phone);
        usernameUserMap.put(username, newUser);
        userStore.put(newestID, newUser);
        saveUserToFile(newUser);
        updateNewestID();
        System.out.println("User registered successfully: " + newUser);
        return true;
    }

    public boolean isUsernameTaken(String username) {
        return usernameUserMap.containsKey(username);
    }

    private void saveUserToFile(User user) {
        try {
            fileDataLoader.loadUsers("");
            String formattedUser = String.format(
                "userID:%d | username:%s | password:%s | email:%s | phone:%s",
                user.getUserID(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone()
            );
            fileDataLoader.writeToFile(formattedUser); 
        } catch (IOException e) {
            System.err.println("Error saving user to file: " + e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
    
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d+");
    }

    public Map<Integer, User> getAllUsers() {
        return userStore;
    }

    private void updateNewestID() {
        newestID++;
    }

    private void loadUsersFromFile() {
        try {
            fileDataLoader.loadUsers("");
            this.userStore = fileDataLoader.getAllUsers(); 
            this.usernameUserMap = fileDataLoader.getUsernameUser();
            this.newestID = userStore.keySet().stream().max(Integer::compare).orElse(0) + 1; 
            System.out.println("Loaded " + userStore.size() + " users. Next available ID: " + newestID);
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
        }
    }
}
