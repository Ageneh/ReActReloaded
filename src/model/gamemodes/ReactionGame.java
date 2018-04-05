package model.gamemodes;

import model.GameMode;

import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class ReactionGame extends GameMode {
    
    protected ReactionGame(Mode mode, Observer o, Observer... observers) {
        super(mode, o, observers);
    }
    
    
}
