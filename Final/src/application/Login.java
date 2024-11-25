package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login implements LoginInterface {

    @FXML
    private Button button;
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @Override
    public void USERLogIn(ActionEvent event) throws IOException {
        checkLogin();
    }

    private void checkLogin() throws IOException {
        //TO DO
        if ("aaa".equals(username.getText()) && "aaa".equals(password.getText())) {
            wrongLogin.setText("Success");
            SceneManager.getInstance().changeScene("SideNavigate.fxml");
        } else {
            wrongLogin.setText("Failed");
        }
    }
}
