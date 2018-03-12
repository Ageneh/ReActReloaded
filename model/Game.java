package model;

import java.util.Observer;
import java.util.concurrent.TimeUnit;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public abstract class Game extends ObservableModel implements Observer {

    private final static int MILLIS_PER_SEC = 1000;
    private final static int SEC_PER_MIN = 60;
    private final static int MIN_PER_H = 60;

    private MusicPlayer musicPlayer;
    private long startTime;
    private int replayCount;
    private GameMode mode;
    private Playlist gamePlaylist;
    private boolean hasStarted;
    private boolean nextCalled;

    private int points;
    // TODO: create and add class >User<

    protected Game(GameMode mode, Playlist playlist) {
        this.mode = mode;
        this.musicPlayer = new MusicPlayer(this);
        this.startTime = 0;
        this.replayCount = 0;
        this.gamePlaylist = playlist;
        this.hasStarted = this.nextCalled = false;
    }

    protected GameMode getMode(){
        return this.mode;
    }

    /**
     * Called to start and play the next song.
     * Can only be called once (from outside of this class); only when starting.
     * After initial call will only be called by {@link Game#next()} to play next.
     */
    public void start(){
        if(!this.hasStarted || (this.hasStarted && this.nextCalled)) {
            if(!this.hasStarted) this.hasStarted = true;
            this.startTime = System.currentTimeMillis();
            this.musicPlayer.play(this.gamePlaylist.getNext());
            this.nextCalled = false;
        }
    }

    /**
     * Loads the next song and then calls {@link Game#start()}.
     */
    public void next(){
        this.nextCalled = true;
        this.start();
    }

    /**
     * @author Henock Arega
     * @project ReActReloaded
     *
     * Used to replay the current song.
     */
    public void replay(){
        // TODO: call play method musicplayer.replay
        this.replayCount++;
        this.points -= 25;

        this.musicPlayer.replay(this.mode.additionalTime);
    }

    public void end(){
        long end = System.currentTimeMillis();
        long millis = end / MILLIS_PER_SEC;
        long sec = (end / MILLIS_PER_SEC) % SEC_PER_MIN;
        long min = (end / (MILLIS_PER_SEC*SEC_PER_MIN)) % SEC_PER_MIN;
        System.out.println(String.format("%02d:%02d:%02d", min, millis, sec));
    }

    /**
     * Evaluates the given answer and depending on the result and the player's game stat either the points will be
     * increased, if the answer is correct or the game will be ended.
     * Exceptions are:
     *      -> when one has a combo: the combe will drop down to 1x and points will be decreased
     *      -> depending on {@link GameMode#CONTINUOUS} the game can just continue to the next one
     * @param answer
     */
    public void answer(Song answer){
        // TODO: evaluation of answer and notify others
    }

    /**
     * @author Henock Arega
     * @project ReActReloaded
     */
    protected enum  GameMode{

        /**
         * @see model.models.NormalGame
         */
        NORMAL(1000, 1000, 8),
        /**
         * @see model.models.TimedGame
         */
        TIMED(TimeUnit.SECONDS.toMillis(10) * SEC_PER_MIN),
        /**
         * @see model.models.ContinuousGame
         */
        CONTINUOUS(1, 0, 3);
        ;

        private long lengthInMillis;
        private long additionalTime;
        private int maxReplay;

        GameMode(long length){
            this(length, 0, 0);
        }

        GameMode(long length, long additionalTimeVal, int maxReplay){
            this.lengthInMillis = length;
            this.additionalTime = additionalTimeVal;
            this.maxReplay = maxReplay;
        }

        public long millis() {
            return TimeUnit.MILLISECONDS.toMillis(this.lengthInMillis);
        }

        public long seconds() {
            return TimeUnit.MILLISECONDS.toSeconds(this.lengthInMillis);
        }

        public long minutes() {
            return TimeUnit.MILLISECONDS.toMinutes(this.lengthInMillis);
        }

        public long hours() {
            return TimeUnit.MILLISECONDS.toHours(this.lengthInMillis);
        }

        public long getAdditionalTime(){
            return this.additionalTime;
        }

    }

}
