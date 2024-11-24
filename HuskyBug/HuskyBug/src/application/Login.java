package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {
	public Login() {
		
	}
	
	@FXML
	private Button button;
	@FXML
	private Label wrongLogin;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	
	public void userLogin(ActionEvent event) throws IOException {
		checkLogin();
	}
	
	private void checkLogin() throws IOException {
		MarketPlaceApp m = new MarketPlaceApp();
		if(username.getText().toString().equals("java") && password.getText().toString().equals("123")) {
			wrongLogin.setText("success!");
			
			m.changeScene("MarketplaceView.fxml");
		}
		else if(username.getText().isEmpty() && password.getText().isEmpty()) {
			wrongLogin.setText("Please enter.");
		}
		else {
			wrongLogin.setText("Wrong username or password!");
		}
	}
}
