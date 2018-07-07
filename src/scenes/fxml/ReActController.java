package scenes.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Close;
import model.GameMode;
import model.Ranking;
import scenes.Controller;
import scenes.gamemodes.ContinuousGameScene;
import scenes.gamemodes.GameScene;
import scenes.gamemodes.NormalGameScene;
import scenes.gamemodes.TimedGameScene;

import java.io.IOException;
import java.net.URL;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * @author Henock Arega
 */
public class ReActController extends Controller implements Close {
    
    private Ranking ranking;
    private GameMode.Mode mode;
    private GameScene gameScene;
    private StartScene startScene;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane root;

    @FXML
    private HBox gameModeBtn;

    @FXML
    private HBox settingsBtn;

    @FXML
    private HBox quickGameBtn;

    @FXML
    private Pane rankingBtn;
    
    public ReActController(Observer o) {
        this.addObserver(o);
        this.startScene = new StartScene();
    }
    
    @FXML
    public void setGameMode(GameMode.Mode mode) throws IOException {
        this.mode = mode;
        switch (mode) {
            case NORMAL:
                this.gameScene = new NormalGameScene(this);
                break;
            case TIMED:
                this.gameScene = new TimedGameScene(this);
                break;
            case CONTINUOUS:
                this.gameScene = new ContinuousGameScene(this);
                break;
        }
        this.start();
    }
    
    @FXML
    public void start() throws IOException {
        this.root = FXMLLoader.load(getClass().getResource(this.gameScene.getFxmlPath()));
    }

    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'ReAct.fxml'.";
        assert gameModeBtn != null : "fx:id=\"gameModeBtn\" was not injected: check your FXML file 'ReAct.fxml'.";
        assert settingsBtn != null : "fx:id=\"settingsBtn\" was not injected: check your FXML file 'ReAct.fxml'.";
        assert quickGameBtn != null : "fx:id=\"quickGameBtn\" was not injected: check your FXML file 'ReAct.fxml'.";
        assert rankingBtn != null : "fx:id=\"rankingBtn\" was not injected: check your FXML file 'ReAct.fxml'.";

    }

    @FXML
    void showGameModeSelection(ActionEvent event) {
        System.out.println("Mode selection");
    }

    @FXML
    void showRanking(ActionEvent event) {
        System.out.println("Ranking");
    }

    @FXML
    void showSettings(ActionEvent event) {
        System.out.println("Settigns");
    }

    @FXML
    void startNormalGame(ActionEvent event) {
        System.out.println("Quick Maths");
    }
    
    @Override
    public void close(Code code) {
        this.gameScene.close(code);
        this.ranking.close(code);
    }


}


