package model.gamemodes;

import model.ObservableModel;
import model.isGame;

import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
abstract class GameController extends ObservableModel implements Observer, isGame {
    
    GameController(Observer o, Observer... observers) {
        super();
        super.addAllObserver(o, observers);
    }
    
    //////////// METHODS
    public abstract void test();
    
    public abstract void replay();
    
}
