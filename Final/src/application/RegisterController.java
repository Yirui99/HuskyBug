package application;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    private final RegisterService registerService = new RegisterService();

    @FXML
    private void handleRegisterButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            System.out.println("All fields are required.");
        } else if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match.");
        } else if (!email.contains("@") || !email.contains(".")) {
            System.out.println("Invalid email address.");
        } else if (!phone.matches("\\d+")) {
            System.out.println("Phone number must contain only digits.");
        } else {
            boolean success = registerService.registerUser(username, password, email, phone);
            if (success) {
                System.out.println("User registered successfully:");
                System.out.println("Username: " + username);
                System.out.println("Email: " + email);
                System.out.println("Phone: " + phone);

                try {
                    SceneManager.getInstance().changeScene("Home.fxml");
                } catch (Exception e) {
                    System.err.println("Failed to load the Home.fxml: " + e.getMessage());
                }
            } else {
                System.out.println("Registration failed.");
            }
        }
    }
}
