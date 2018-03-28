package model.gamemodes;

import functions.ANSI;
import model.GameMode;
import model.MusicPlayer;
import model.Song;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @author ${USER
 * @project ReActReloaded
 * <p>
 * In the {@link GameModeProp#NORMAL} game mode the song will first play for the given amount
 * of seconds and its value will be increased every time the player clicks on replay. With each
 * click on replay the amount of possible points reachable decreases.
 */
class NormalGame extends GameMode {
    
    public NormalGame(Observer o, Observer... observers) {
        super(GameModeProp.NORMAL, o, observers);
    }
    
    public NormalGame(String name, Observer o, Observer... observers) {
        super(GameModeProp.NORMAL, name, o, observers);
    }
    
    @Deprecated
    private void evalAnswer(Object arg) {
    
    }
    
    //////////// OVERRIDES
    @Override
    public void replay() {
        if (super.getReplayCount() < super.mode.getMaxReplay()) {
//            superj. k
            super.replay();
            super.addPoints(-(1 / 2.0));
        }
    }
    
    @Override
    public boolean answer(Song answer) {
        boolean res = super.answer(answer);
        if (res) {
            super.pause();
            super.addPoints();
            super.notifyOfGameStatus();
            super.next();
        } else {
            // TODO show screen overlay where name can be entered and one choses to go to home, play again etc.
            super.subtractPoints();
            super.close(Code.GAME_OVER);
            ANSI.RED.println("Wrong answer.");
            super.notifyOfGameStatus();
        }
        return res;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MusicPlayer) {
            this.evalAnswer(arg);
        }
    }
    
}
