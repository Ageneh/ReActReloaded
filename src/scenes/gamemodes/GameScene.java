package scenes.gamemodes;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import scenes.ObservableScene;

import java.util.Observer;

/**
 * @project ReActReloaded
 * @author Henock Arega
 */
public abstract class GameScene extends ObservableScene implements Observer {
    
    private GameBackground background;

    GameScene(Observer o, Observer ... observers) {
        background = new GameBackground();
    }
    
    @Override
    public Scene getScene() {
        return this.background.scene;
    }
    
    private class GameBackground {
    
        private StackPane root;
        private Scene scene;
        
        private GameBackground(){
            this.root = new StackPane();
            this.scene = new Scene(this.root);
        }
        
    }

}
