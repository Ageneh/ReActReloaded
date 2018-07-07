package scenes.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import model.User;

import java.io.IOException;

public class NormalGameSceneController {

    private Scene scene;
    private User user;

    public NormalGameSceneController() {
    }

    public NormalGameSceneController(User user) {
    }

    public NormalGameSceneController(Scene scene, User user) throws IOException {
        this.scene = scene;
        this.user = user;
        Parent newScene = FXMLLoader.load(getClass().getResource("normalgame_scene.fxml"));
        this.scene.setRoot(newScene);
    }

    public void getStartScene(MouseEvent mouseEvent) {

    }
}
