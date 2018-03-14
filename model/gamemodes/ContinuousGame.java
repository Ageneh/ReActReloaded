package model.gamemodes;

import model.Game;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 *
 * Will play a song and even if the answer was wrong one it will continue to the next song - regardless.
 * Plus: one can replay every song for {@linkplain model.Game.GameMode#maxReplay} amount of times.
 */
public class ContinuousGame extends Game {

    public ContinuousGame(Observer o, Observer ... observers) {
        super(GameMode.CONTINUOUS, o, observers);
    }

    public ContinuousGame(String name, Observer o, Observer ... observers) {
        super(GameMode.CONTINUOUS, name, o, observers);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void end() {
        super.start();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void close(Code code) {

    }
}
