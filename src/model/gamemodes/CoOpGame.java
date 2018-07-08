package model.gamemodes;

import functions.ANSI;
import model.GameMode;
import model.Song;

import java.util.Observable;
import java.util.Observer;


public class CoOpGame extends GameMode {
    
    private int maxGameRound;
    
    public CoOpGame(String player1, String player2) {
        this(player1, player2, null);
    }
    
    public CoOpGame(String player1, String player2, Observer o, Observer... observers) {
        super(Mode.NORMAL, player1, player2, o, observers);
        this.maxGameRound = 10;
    }
    
    @Override
    public boolean answer(Song answer) {
        long time = System.currentTimeMillis();
        musicPlayer.pause();
        setChanged();
        boolean res = answer.equals(this.musicPlayer.currentSong()) ||
                answer.getPath().equals(this.musicPlayer.currentSong().getPath());
        if (res) {
            this.correctAnswer = true;
            ANSI.GREEN.println("======================\n====  CORRECT ANSWER\n======================");
            addPoints(2);
            notifyObservers(Action.ANSWER_CORRECT.setVal(getUsers().get(activeUser)));
            setChanged();
            notifyObservers(Action.POINTS.setVal(POINTS * 2));
        } else {
            this.correctAnswer = false;
            ANSI.RED.println("======================\n====  WRONG ANSWER\n======================");
            notifyObservers(Action.ANSWER_INCORRECT.setVal(getUsers().get(activeUser)));
            setChanged();
            notifyObservers(Action.POINTS.setVal(POINTS));
        }
        super.correctAnswer = res;
        super.users.get(activeUser).addReactionTime(time - startTime);
        
        if (super.gameRound >= this.maxGameRound) {
            super.endGame(true);
        } else {
            super.next();
        }
        
        resetActiveUser();
        
        return res;
    }
    
    
    
    @Override
    public void addPoints(double multiplier) {
        this.users.get(activeUser).setPoints(
                (int) (this.users.get(activeUser).getPoints() +
                        (((double) super.POINTS * this.multiplier)
                                * multiplier)));
    }
    
    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
    }
    
}
