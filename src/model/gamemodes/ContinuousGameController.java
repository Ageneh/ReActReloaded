package model.gamemodes;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class ContinuousGameController extends GameController {
    
    //TODO: vielleicht: statt dem spiel den controller als observer zu vergeben vielleicht direkt die schon mitgebgebennen observer mitgeben
    
    
    private ContinuousGame continuousGame;
    
    public ContinuousGameController(Observer o, Observer... observers) {
        super(o, observers);
        continuousGame = new ContinuousGame(this);
    }
    
    public ContinuousGameController(String name, Observer o, Observer... observers) {
        super(o, observers);
        continuousGame = new ContinuousGame(name, this);
    }
    
    //////////// OVERRIDES
    @Override
    public long getCurrentPlayTime() {
        return 0;
    }
    
    @Override
    public void close(Code code) {
    
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
    
    @Override
    public void test() {
    
    }
    
    @Override
    public void replay() {
    
    }
}
