package model.gamemodes;

import model.Game;
import model.Song;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 *
 * In the {@link GameMode#NORMAL} game mode the song will first play for the given amount
 * of seconds and its value will be increased every time the player clicks on replay. With each
 * click on replay the amount of possible points reachable decreases.
 */
public class NormalGame extends Game {

    public NormalGame(Observer o, Observer ... observers) {
        super(GameMode.NORMAL, o, observers);
    }

    public NormalGame(String name, Observer o, Observer ... observers) {
        super(GameMode.NORMAL, name, o, observers);
    }
    
    @Override
    public void answer(Song answer) {
    }
    
    @Override
    public void close(Code code) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

}
