package model;

import java.util.Observable;
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

    /** The song library used for each game. */
    private SongLibrary songLibrary;

    /** The {@link MusicPlayer} used to play each {@link Song random song}. */
    private MusicPlayer musicPlayer;
    /** Logs the time when a game has started, as a reference for the duration of a game. */
    private long startTime;
    /** A counter which will count the amount of times a song has been replayed. */
    private int replayCount;
    /** The game mode of each game. */
    private GameMode mode;
    /** The playlist for each game round. Will be recreated with every game. */
    private Playlist gamePlaylist;
    /** A flag which will be set when {@link #start()} has been called. */
    private boolean hasStarted;
    /**
     * A flag which will be set when {@link #next()} has been called.
     * Will make it possible to call {@link #start()} through {@link #next()}.
     */
    private boolean nextCalled;
    /** A counter for the points of each game */
    private int points;
    /** A user object in which all the specific game data will be saved in for each {@link User player}. */
    private User user;

    protected Game(GameMode mode) {
        this(mode, null);
    }

    protected Game(GameMode mode, String username) {
        this.mode = mode;
        this.user = new User(username);
        this.musicPlayer = new MusicPlayer(this);
        this.startTime = 0;
        this.replayCount = 0;
        this.songLibrary = new SongLibrary();
        this.gamePlaylist = new Playlist(this.songLibrary.getFolderPaths());
        this.hasStarted = this.nextCalled = false;
        this.songLibrary.close(Code.CLOSE);
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

    protected GameMode getMode(){
        return this.mode;
    }

    public SongLibrary getSongLibrary() {
        return songLibrary;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof MusicPlayer){
            /* when the musicplayer has changed (e.g. song is done) */
            
        }
    }
    
    /**
     * @author Henock Arega
     * @project ReActReloaded
     */
    protected enum  GameMode{

        /**
         * @see model.gamemodes.NormalGame
         */
        NORMAL(1000, 1000, 8),
        /**
         * @see model.gamemodes.TimedGame
         */
        TIMED(TimeUnit.SECONDS.toMillis(10) * SEC_PER_MIN),
        /**
         * @see model.gamemodes.ContinuousGame
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
