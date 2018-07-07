package model.gamemodes;

import model.GameMode;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 */
public class CoOpGame extends GameMode {
    
    public CoOpGame(Mode mode, String player1, String player2, Observer o, Observer... observers) {
        super(mode, player1, o, observers);
    }
    
    public CoOpGame(Mode mode, Observer o, Observer... observers) {
        super(mode, o, observers);
    }
    
    public CoOpGame(Mode mode, int answerCount, String username, Observer o, Observer... observers) {
        super(mode, answerCount, username, o, observers);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
    }
    
}
