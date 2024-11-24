package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MarketPlaceApp extends Application {
	private static Stage stg;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		try {
			stg = primaryStage;
			primaryStage.setResizable(false);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setTitle("MarketPlace");
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void changeScene(String fxml) throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getScene().setRoot(pane);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
