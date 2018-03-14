package model.gamemodes;

import model.Game;

import java.util.Observable;

/**
 * @author Henock Arega
 * @project ReActReloaded
 *
 * In {@link GameMode#TIMED} game mode the player will have a total of the given amount of play
 * time (in minutes; {@link Game.GameMode#TIMED}) to get as many songs as possible.
 */
public class TimedGame extends Game {

    protected TimedGame() {
        super(GameMode.TIMED);
    }

    protected TimedGame(String name) {
        super(GameMode.TIMED, name);
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
