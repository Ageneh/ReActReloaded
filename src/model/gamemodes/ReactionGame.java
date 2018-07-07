package model.gamemodes;

import model.GameMode;

import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class ReactionGame extends GameMode {
    
    protected ReactionGame(String username, Observer o, Observer... observers) {
        super(Mode.REACTION, username, o, observers);
    }
    
    
}
