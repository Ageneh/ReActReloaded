package scenes.gamemodes;

import java.util.Observable;
import java.util.Observer;

/**
 * @project ReActReloaded
 * @author Henock Arega
 */
public class TimedGameScene extends GameScene {

    TimedGameScene(Observer o, Observer... observers) {
        super(o, observers);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
