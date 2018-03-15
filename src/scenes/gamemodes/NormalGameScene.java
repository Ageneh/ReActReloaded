package scenes.gamemodes;

import model.gamemodes.NormalGame;

import java.util.Observable;
import java.util.Observer;

/**
 * @project ReActReloaded
 * @author Henock Arega
 */
public class NormalGameScene extends GameScene {

    private NormalGame game;

    public NormalGameScene(String name, Observer o, Observer ... observers) {
        super(o, observers);
        this.game = new NormalGame(name, o, observers);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
    
}
