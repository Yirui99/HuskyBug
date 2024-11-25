package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {

    double x,y = 0;
    @Override
    public void start(Stage primaryStage) {
        try {

            SceneManager.getInstance().setStage(primaryStage);

            //Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            root.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged(event ->{

                x = event.getSceneX() - x;
                y = event.getSceneY() - y;
            });

            Scene scene = new Scene(root,1000,600);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
