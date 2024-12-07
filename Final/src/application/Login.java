package application;

import java.io.IOException;
import java.util.Map;

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
    @FXML
    private Button registerButton;
    @Override
    public void USERLogIn(ActionEvent event) throws IOException {
        checkLogin();
    }
    private void checkLogin() throws IOException {
    	System.out.println("Begin Check the login");
    	FileDataLoader fileLoader = new FileDataLoader();
    	fileLoader.loadUsers("");
    	Map<String,User> infoCheckList =  fileLoader.getUsernameUser();
    	String inputUsername = username.getText();
    	String inputPassword = password.getText();
    	if (infoCheckList.containsKey(inputUsername)) {
    		if (infoCheckList.get(inputUsername).getPassword().equals(inputPassword)) {
    			 wrongLogin.setText("Success");
    	         SceneManager.getInstance().changeScene("Home.fxml");
    		} else {
    			wrongLogin.setText("Wrong Password");
    		}
    	} else {
    		wrongLogin.setText("No such user");
    	}
    	/*
        if ("".equals(username.getText()) && "".equals(password.getText())) {
            wrongLogin.setText("Success");
            SceneManager.getInstance().changeScene("Home.fxml");
        } else {
            wrongLogin.setText("Failed");
        }
        */
    }
    @FXML
    public void registerUser(ActionEvent event) throws IOException {
        System.out.println("Register button clicked!");
        SceneManager.getInstance().changeScene("Register.fxml");
    }
}
