package scenes.fxml;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.GameMode;
import model.Ranking;
import scenes.Controller;
import scenes.gamemodes.ContinuousGameScene;
import scenes.gamemodes.GameScene;
import scenes.gamemodes.NormalGameScene;
import scenes.gamemodes.TimedGameScene;

import java.io.IOException;
import java.util.Observer;

/**
 * @author Henock Arega
 */
public class ReActController extends Controller {
    
    @FXML
    private Parent root;
    
    private Ranking ranking;
    private GameMode.Mode mode;
    private GameScene gameScene;
    
    public ReActController(Observer o) {
        this.addObserver(o);
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
    
    
}
