package scenes.gamemodes;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class ContinuousGameScene extends GameScene {
    ContinuousGameScene(Observer o, Observer... observers) {
        super(o, observers);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
