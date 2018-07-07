package scenes.gamemodes;

import model.gamemodes.TimedGame;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class TimedGameScene extends GameScene {
    
    public TimedGameScene(Observer... observers) {
        super("scenes/fxml/timedgame_scene.fxml", new TimedGame(null, null), observers);
    }
    
    @Override
    public void close(Code code) {
    
    }
    
    @Override
    public void start() {
    
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
}
