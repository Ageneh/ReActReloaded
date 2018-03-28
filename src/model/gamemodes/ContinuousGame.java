package model.gamemodes;

import model.GameMode;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 * <p>
 * Will play a song and even if the answer was wrong one it will continue to the next song - regardless.
 * Plus: one can replay every song for {@linkplain GameModeProp#maxReplay} amount of times.
 */
public class ContinuousGame extends GameMode {
    
    public ContinuousGame(Observer o, Observer... observers) {
        super(GameModeProp.CONTINUOUS, o, observers);
    }
    
    public ContinuousGame(String name, Observer o, Observer... observers) {
        super(GameModeProp.CONTINUOUS, name, o, observers);
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
}
