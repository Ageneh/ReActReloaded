package scenes.gamemodes;

import model.GameMode;
import model.gamemodes.TimedGame;
import model.isGame;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class TimedGameScene extends GameScene {
    
    public TimedGameScene(Observer observer) {
        super(new TimedGame("", null), observer);
    }
    
    @Override
    public void close(Code code) {
    
    }
    
    @Override
    protected void evalAction(isGame.Action action) {
    
    }
    
    @Override
    protected void evalMode(GameMode.Mode mode) {
    
    }
    
    @Override
    public void start() {
    
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
}
