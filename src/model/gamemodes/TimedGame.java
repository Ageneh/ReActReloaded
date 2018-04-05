package model.gamemodes;

import functions.ANSI;
import model.GameMode;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Henock Arega
 * @project ReActReloaded
 * <p>
 * In {@link Mode#TIMED} game mode the player will have a total of the given amount of play
 * timer (in minutes; {@link Mode#TIMED}) to get as many songs as possible.
 */
public class TimedGame extends GameMode {
    
    private final int DELAY = 1000;
    private final int PERIOD = 100;
    private boolean isInit;
    private Length length;
    private Timer timer;
    private TimerTask timerTask;
    
    protected TimedGame(Observer o, Observer... observers) {
        this(Length.SHORT, o, observers);
    }
    
    protected TimedGame(Length length, Observer o, Observer... observers) {
        super(Mode.TIMED, o, observers);
        this.length = length;
        this.isInit = false;
        this.init();
    }
    
    protected TimedGame(String name, Observer o, Observer... observers) {
        this(name, Length.SHORT, o, observers);
    }
    
    protected TimedGame(String name, Length length, Observer o, Observer... observers) {
        super(Mode.TIMED, name, o, observers);
        this.init();
        this.isInit = false;
        this.length = length;
    }
    
    //////////// METHODS
    private void init() {
        if (!isInit)
            this.isInit = true;
        this.timerTask = new TimerTask() {
            long elapsedTime = 0; // in milliseconds
            //////////// OVERRIDES
            @Override
            public void run() {
                ANSI.GREEN.print(++elapsedTime + " ");
                if (elapsedTime % 10 == 0) System.out.println();
                
                if (elapsedTime >= TimedGame.this.length.seconds * (1000 / PERIOD)) {
                    cancel();
                    TimedGame.super.close(Code.GAME_OVER);
                }
                
                setChanged();
                notifyObservers(new ElapsedTime(elapsedTime));
            }
            
        };
        this.timer = new Timer("TimeOfPlay");
    }
    
    //////////// OVERRIDES
    @Override
    public void start() {
        this.timer.scheduleAtFixedRate(this.timerTask, this.DELAY, this.PERIOD);
        super.start();
    }
    @Override
    public void update(Observable o, Object arg) {
    
    }
    
    /**
     * An enum used to define the length of a {@link TimedGame}.
     */
    public enum Length {
        
        SHORT(30), MEDIUM(60), LONG(120);
        
        private int seconds;
        
        //////////// CONSTRUCTORS
        Length(int seconds) {
            this.seconds = seconds;
        }
        
    }
    
    public class ElapsedTime {
        
        /** Elapsed time in seconds. */
        private long time;
        
        //////////// CONSTRUCTORS
        private ElapsedTime(long time) {
            this.time = time;
        }
        
        //////////// METHODS
        public long time() {
            return time;
        }
        
    }
    
}


class TimedTest implements Observer {
    
    TimedTest() {
        TimedGame tg = new TimedGame(this);
        tg.start();
    }
    //////////// METHODS
    public static void main(String[] args) {
        new TimedTest();
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
}