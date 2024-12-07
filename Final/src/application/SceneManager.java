package application;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class SceneManager {
    private static SceneManager instance;
    private Stage stage;

    private SceneManager() {}

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        if (this.stage == null) {
            this.stage = stage;
        } else {
            throw new IllegalStateException("Stage already set!");
        }
    }

    public void changeScene(String fxml) throws IOException {
        if (stage == null) {
            throw new IllegalStateException("Stage not initialized. Call setStage() first.");
        }
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(pane);
    }
}
