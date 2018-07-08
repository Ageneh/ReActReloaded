package scenes;

import javafx.scene.layout.GridPane;
import scenes.elements.CustomActionBtn;
import scenes.gamemodes.CoOpGameScene;
import scenes.gamemodes.ContinuousGameScene;
import scenes.gamemodes.NormalGameScene;

import java.util.Observer;

public class GameModeScene extends BaseReactScene {
    
    private CustomActionBtn normalGame;
    private CustomActionBtn continuousGame;
    private CustomActionBtn multiGame;
    
    public GameModeScene(ObservableScene o) {
        super("Spielmodi auswählen:", o);
        this.init();
    }
    
    public GameModeScene(ObservableScene observableScene, Observer o) {
        super("Spielmodi auswählen:", observableScene);
        this.init();
    }
    
    public GameModeScene(Observer o) {
        super("Spielmodi auswählen:", o);
        this.init();
    }
    
    private void init() {
        GridPane grid = getGrid();
        
        normalGame = new CustomActionBtn("Normales Spiel");
        continuousGame = new CustomActionBtn("Endlos Spiel");
        multiGame = new CustomActionBtn("Multiplayer");
        
        
        grid.add(normalGame.getBox(), 0, 1);
        grid.add(continuousGame.getBox(), 0, 2);
        grid.add(multiGame.getBox(), 0, 3);
        
        
        normalGame.getBox().setOnMouseClicked(event -> {
            setChanged();
            notifyObservers(new NormalGameScene(this));
        });
        
        continuousGame.getBox().setOnMouseClicked(event -> {
            setChanged();
            notifyObservers(new ContinuousGameScene(this));
        });
        
        multiGame.getBox().setOnMouseClicked(event -> {
            setChanged();
            notifyObservers(new CoOpGameScene(this));
        });
    }
    
}
