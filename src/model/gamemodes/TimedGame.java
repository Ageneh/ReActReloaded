package model.gamemodes;

import model.GameMode;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 * <p>
 * In {@link GameModeProp#TIMED} game mode the player will have a total of the given amount of play
 * time (in minutes; {@link GameModeProp#TIMED}) to get as many songs as possible.
 */
public class TimedGame extends GameMode {
    
    protected TimedGame(Observer o, Observer... observers) {
        super(GameModeProp.TIMED, o, observers);
    }
    
    protected TimedGame(String name, Observer o, Observer... observers) {
        super(GameModeProp.TIMED, name, o, observers);
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
    
}
