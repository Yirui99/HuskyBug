package application;

import java.io.IOException;
import java.util.Map;

public class RegisterService {

    private Map<Integer, User> userStore; // 用户存储（ID映射）
    private Map<String, User> usernameUserMap; // 用户存储（用户名映射）
    private int newestID; // 最新ID
    private FileDataLoader fileDataLoader; // 数据加载器

    // 构造函数
    public RegisterService() {
        this.fileDataLoader = new FileDataLoader();
        this.newestID = 0; // 默认ID为0
        loadUsersFromFile(); // 从文件加载用户
    }

    // 注册用户
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

    // 检查用户名是否存在
    public boolean isUsernameTaken(String username) {
        return usernameUserMap.containsKey(username);
    }

    // 保存用户到文件（追加模式，不重写整个文件）
    private void saveUserToFile(User user) {
        try {
            // 调用 FileDataLoader 的方法追加数据
            fileDataLoader.loadUsers("");
            String formattedUser = String.format(
                "userID:%d | username:%s | password:%s | email:%s | phone:%s",
                user.getUserID(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone()
            );
            fileDataLoader.writeToFile(formattedUser); // 假设新增了一个写入方法
        } catch (IOException e) {
            System.err.println("Error saving user to file: " + e.getMessage());
        }
    }

    // 验证邮箱格式
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    // 验证电话号码格式
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d+");
    }

    // 获取所有用户
    public Map<Integer, User> getAllUsers() {
        return userStore;
    }

    // 更新最新ID
    private void updateNewestID() {
        newestID++;
    }

    // 从文件加载用户
    private void loadUsersFromFile() {
        try {
            fileDataLoader.loadUsers("");
            this.userStore = fileDataLoader.getAllUsers(); // 从 FileDataLoader 加载用户数据
            this.usernameUserMap = fileDataLoader.getUsernameUser();
            this.newestID = userStore.keySet().stream().max(Integer::compare).orElse(0) + 1; // 动态计算最新ID
            System.out.println("Loaded " + userStore.size() + " users. Next available ID: " + newestID);
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
        }
    }
}
