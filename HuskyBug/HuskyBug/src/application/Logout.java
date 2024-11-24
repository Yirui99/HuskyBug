package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Logout {
	@FXML
	private Button logout;
	
	public void userLogout(ActionEvent event) throws IOException {
		MarketPlaceApp m = new MarketPlaceApp();
		m.changeScene("login.fxml");
	}
}
