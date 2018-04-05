package model.gamemodes;

import model.GameMode;
import model.Song;

import java.util.Observable;
import java.util.Observer;

/**
 * In the {@link Mode#NORMAL} game mode the song will first play for the given amount
 * of seconds and its value will be increased every time the player clicks on replay. With each
 * click on replay the amount of possible points reachable decreases.
 *
 * @author Henock Arega
 * @project ReActReloaded
 */
public class NormalGame extends GameMode {
    
    private int STEP = 3;
    
    /** A flag used for the {@link #answerType} indicating whether an answer was correct or incorrect. */
    private boolean answerType;
    /** A multiplier used to multiply the given points depending on the {@link #streak}. */
    private int multiplier;
    /** A counter for a streak of either correct or wrong answers. Depends on {@link #answerType}. */
    private int streak;
    
    public NormalGame(Observer o, Observer... observers) {
        super(Mode.NORMAL, o, observers);
        this.init();
    }
    
    public NormalGame(String name, Observer o, Observer... observers) {
        super(Mode.NORMAL, name, o, observers);
        this.init();
    }
    
    //////////// METHODS
    private void init() {
        this.answerType = false;
        this.streak = 0;
        this.multiplier = 1;
    }
    
    //////////// OVERRIDES
    @Override
    public void replay() {
        if (super.getReplayCount() < super.mode.getMaxReplay()) {
            super.replay();
            super.addPoints(-(1 / 2.0));
        }
    }
    
    @Override
    public boolean answer(Song answer) {
        boolean res = super.answer(answer);
        super.pause();
        if (res) {
            if (this.answerType && this.streak >= 0) {
                this.streak++;
                if (this.multiplier >= 1 && this.multiplier <= MAX_MULT && streak > 0 && this.streak % MINSTEP == 0) {
                    this.multiplier++;
                }
                System.out.println("SRTEAK:\t" + streak);
                System.out.println("MULTI:\t " + multiplier);
            }
            super.addPoints();
            super.notifyOfGameStatus();
            super.next();
    
    
            //******TEST
            setChanged();
            notifyObservers(super.getSong());
        } else {
            // TODO show screen overlay where name can be entered and one choses to go to home, play again etc.
            super.subtractPoints();
            super.close(Code.GAME_OVER);
            super.notifyOfGameStatus();
        }
        this.answerType = res;
        return res;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
    }
    
}
