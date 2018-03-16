package model.gamemodes;

import model.Game;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 * <p>
 * In {@link GameMode#TIMED} game mode the player will have a total of the given amount of play
 * time (in minutes; {@link Game.GameMode#TIMED}) to get as many songs as possible.
 */
public class TimedGame extends Game {
    
    protected TimedGame(Observer o, Observer... observers) {
        super(GameMode.TIMED, o, observers);
    }
    
    protected TimedGame(String name, Observer o, Observer... observers) {
        super(GameMode.TIMED, name, o, observers);
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
    
}
