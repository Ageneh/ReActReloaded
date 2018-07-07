package model.gamemodes;

import model.GameMode;
import model.Playlist;
import model.Song;

import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 * <p>
 * Will play a song and even if the answer was wrong one it will continue to the next song - regardless.
 * Plus: one can replay every song for {@linkplain Mode#maxReplay} amount of times.
 */
public class ContinuousGame extends GameMode {
    
    private final int MAX_LIFECOUNT = 3;
    private int lifeCount;
    
    public ContinuousGame(Observer o, Observer... observers) {
        this(null, o, observers);
        
    }
    
    public ContinuousGame(String name) {
        this(name, null);
    }
    
    public ContinuousGame(String name, Observer o, Observer... observers) {
        super(Mode.CONTINUOUS, name, o, observers);
        this.lifeCount = this.MAX_LIFECOUNT;
        super.gamePlaylist = new Playlist(super.songLibrary, true, true);
    }
    
    public int getLifeCount() {
        return lifeCount;
    }
    
    private void incLifeCount(boolean inc) {
        if (inc) lifeCount++;
        else lifeCount--;
        if (lifeCount > MAX_LIFECOUNT) lifeCount = MAX_LIFECOUNT;
        if (lifeCount < 0) lifeCount = 0;
        setChanged();
        notifyObservers(Action.LIFECOUNT.setVal(this.lifeCount));
    }
    
    //////////// OVERRIDES
    @Override
    public boolean answer(Song answer) {
        boolean res = super.answer(answer);
        super.pause();
        if (res) {
            if (this.lifeCount < MAX_LIFECOUNT && this.lifeCount > 0 && streak % 5 == 0) {
                this.incLifeCount(true);
            }
            this.incLifeCount(true);
            super.addPoints();
        } else {
            super.subtractPoints();
            this.incLifeCount(false);
            if (lifeCount == 0) {
                setChanged();
                notifyObservers(Mode.GAME_OVER);
                super.endGame();
                return false;
            }
        }
        super.next();
        return res;
    }
    
}
