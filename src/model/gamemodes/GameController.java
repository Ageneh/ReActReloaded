package model.gamemodes;

import model.ObservableModel;

import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public abstract class GameController extends ObservableModel implements Observer {
    
    public GameController(Observer o, Observer... observers) {
        super();
        super.addAllObserver(o, observers);
    }
    
    //////////// METHODS
    public abstract void test();
    
    public abstract void replay();
}
