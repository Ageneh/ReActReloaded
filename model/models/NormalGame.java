package model.models;

import model.Game;

import java.util.Observable;

/**
 * @author Henock Arega
 * @project ReActReloaded
 *
 * In the {@link GameMode#NORMAL} game mode the song will first play for the given amount
 * of seconds and its value will be increased every time the player clicks on replay. With each
 * click on replay the amount of possible points reachable decreases.
 */
public class NormalGame extends Game {

    public NormalGame() {
        super(GameMode.NORMAL);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void end() {
        super.end();
    }

    @Override
    public void close(Code code) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

}
