package model.gamemodes;

import model.GameMode;
import model.Song;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 * <p>
 * Will play a song and even if the answer was wrong one it will continue to the next song - regardless.
 * Plus: one can replay every song for {@linkplain Mode#maxReplay} amount of times.
 */
public class ContinuousGame extends GameMode {
    
    public ContinuousGame(Observer o, Observer... observers) {
        super(Mode.CONTINUOUS, o, observers);
    }
    
    public ContinuousGame(String name, Observer o, Observer... observers) {
        super(Mode.CONTINUOUS, name, o, observers);
    }
    
    //////////// OVERRIDES
    @Override
    public boolean answer(Song answer) {
        boolean res = super.answer(answer);
        super.pause();
        if (res) {
            super.addPoints();
        } else {
            super.subtractPoints();
        }
        super.notifyOfGameStatus();
        super.next();
        return res;
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
}
