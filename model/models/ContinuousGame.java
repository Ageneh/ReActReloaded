package model.models;

import model.Game;
import model.Playlist;

import java.util.Observable;

/**
 * @author Henock Arega
 * @project ReActReloaded
 *
 * Will play a song and even if the answer was wrong one it will continue to the next song - regardless.
 * Plus: one can replay every song for {@linkplain model.Game.GameMode#maxReplay} amount of times.
 */
public class ContinuousGame extends Game {

    public ContinuousGame(Playlist playlist) {
        super(GameMode.CONTINUOUS, playlist);
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
