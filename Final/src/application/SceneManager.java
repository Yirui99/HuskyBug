package application;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class SceneManager {
    private static SceneManager instance;
    private Stage stage;

    // Private constructor to prevent direct instantiation
    private SceneManager() {}

    // Public method to access the single instance
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    // Initialize the stage (should be called only once from Main class)
    public void setStage(Stage stage) {
        if (this.stage == null) {
            this.stage = stage;
        } else {
            throw new IllegalStateException("Stage already set!");
        }
    }

    // Change the scene by loading a new FXML file
    public void changeScene(String fxml) throws IOException {
        if (stage == null) {
            throw new IllegalStateException("Stage not initialized. Call setStage() first.");
        }
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(pane);
    }
}
