package scenes.fxml;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.User;
import scenes.Controller;
import scenes.fxml.exception.NoUserCreatedException;

import java.io.IOException;

public class NameSceneController extends Controller {

    private User player;
    private Scene scene;
    private Parent newScene;
    private NormalGameSceneController ngsc;

    @FXML
    private HBox startBtn;

    @FXML
    private HBox backBtn;

    @FXML
    private TextField nameInput;


    public NameSceneController() {
    }

    public NameSceneController(Scene scene) throws IOException {
        super();
        this.scene = scene;
        this.newScene = FXMLLoader.load(getClass().getResource("nameScene.fxml"));
        this.scene.setRoot(newScene);
    }

    public void startNormalGame(javafx.scene.input.MouseEvent mouseEvent) throws IOException, NoUserCreatedException {
        ngsc = new NormalGameSceneController(startBtn.getScene(), new User(nameInput.getText()));
        Parent newScene = FXMLLoader.load(getClass().getResource("normalgame_scene.fxml"));
//        if(nameInput.getText().equals(null)){
//            throw new NoUserCreatedException("Es wurde kein Name eingetragen");
//        }else{
//            player = new User(nameInput.getText());
//        }

//        startBtn.getScene().setRoot(newScene);
    }

    public void getStartScene(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Parent newScene = FXMLLoader.load(getClass().getResource("ReAct.fxml"));
        backBtn.getScene().setRoot(newScene);
    }
}
