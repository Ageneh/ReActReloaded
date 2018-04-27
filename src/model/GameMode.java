package model;

import functions.ANSI;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.util.*;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

/**
 * @author Henock Arega
 * @project ReActReloaded
 * <p>
 * <p>
 * TODO: block giving answer (GUI/JavaFX) while song is playing
 */
public abstract class GameMode extends ObservableModel implements Observer, isGame {
    
    //////////// VARIABLES
    
    /*
        TODO: make implement multiplier change in gamemode
    */
    /** Max threshold to step get a higher multiplier score. */
    public static final int MAXSTEP = 7;
    /** The max val for the multiplier. */
    public static final int MAX_MULT = 6;
    /** Min threshold to step get a higher multiplier score. */
    public static final int MINSTEP = 3;
    /** The min val for the multiplier. */
    public static final int MIN_MULT = 1;
    private final static int SEC_PER_MIN = 60;
    /** The standard amount of answers at the beginning of a game. */
    private final int STD_ANSWERCOUNT;
    private final GameStatus gameStatus;
    private boolean correctAnswer;
    private final static int MILLIS_PER_SEC = 1000;
    private final static int MIN_PER_H = 60;
    private final int POINTS = 10;
    /**
     * A flag used for {@link #answer(Song)}.
     *
     * @see #answer(Song)
     */
    private boolean isAnswered;
    /** A multiplier used to multiply the given points depending on the {@link #streak}. */
    private int multiplier;
    /** The {@link MusicPlayer} used to play each {@link Song random song}. */
    private MusicPlayer musicPlayer;
    /** The game mode of each game. */
    protected Mode mode;
    /** The amount of answers which are defined by the actual game round */
    private int answerCount;
    /** The answers the {@link User} can choose from - including the correct answer. */
    private Song[] answers;
    /** A counter which will count the amount of times a song has been replayed. */
    private int replayCount;
    /** The playlist for each game round. Will be recreated with every game. */
    private Playlist gamePlaylist;
    /** A flag which will be set when {@link #start()} has been called. */
    private boolean hasStarted;
    /** The song library used for each game. */
    private SongLibrary songLibrary;
    /** Logs the time when a game has started, as a reference for the duration of a game. */
    private long startTime;
    /**
     * A flag which will be set when {@link #next()} has been called.
     * Will make it possible to call {@link #start()} through {@link #next()}.
     */
    private boolean nextCalled;
    /** A counter for the points of each game */
    private int points;
    private int step;
    private int streak;
    protected GameMode(Mode mode, String username, Observer o, Observer... observers) {
        this(mode, 2, username, o, observers);
    }
    /** A user object in which all the specific game data will be saved in for each {@link User player}. */
    private User user;
    
    protected GameMode(Mode mode, Observer o, Observer... observers) {
        this(mode, "ReActor", o, observers);
    }
    
    protected GameMode(Mode mode, final int answerCount, String username, Observer o, Observer... observers) {
        this.STD_ANSWERCOUNT = answerCount;
        this.addAllObserver(o, observers);
        this.mode = mode;
        this.user = new User(username);
        this.musicPlayer = new MusicPlayer(this);
        this.startTime = 0;
        this.replayCount = 0;
        this.songLibrary = new SongLibrary();
        this.gamePlaylist = new Playlist(this.songLibrary.getSongs());
        this.hasStarted = this.nextCalled = this.correctAnswer = this.isAnswered = false;
        this.songLibrary.close(Code.CLOSE);
        this.step = MINSTEP;
        this.multiplier = this.MIN_MULT;
        
        this.gameStatus = new GameStatus();
    }
    //////////// METHODS
    /**
     * Called to start and play the next song. Can only be called once (from outside of this class); only when starting.
     * After initial call will only be called by {@link GameMode#next()} to play next.
     */
    public synchronized void start() {
        if (!this.hasStarted || (this.hasStarted && this.nextCalled)) {
            if (!this.hasStarted) this.hasStarted = true;
            this.nextCalled = false;
            this.isAnswered = false;
            this.replayCount = 0;
            this.startTime = System.currentTimeMillis();
            try {
                this.musicPlayer.play(this.gamePlaylist.getNext(), this.mode.lengthInMillis);
            } catch (ArrayIndexOutOfBoundsException e) {
                this.musicPlayer.play(this.gamePlaylist.getNext(), this.mode.lengthInMillis);
            }
            
            this.createAnswers(this.answerCount); //// TESTING TESTING
            
            notifyOfGameStatus();
        } else {
            throw new AlreadyStartedException();
        }
    }
    public Song song() {
        /********************************/
        /********************************/
        /******* ONLY FOR TESTING *******/
        /********************************/
        /********************************/
        return this.musicPlayer.currentSong();
        /********************************/
        /********************************/
        /******* ONLY FOR TESTING *******/
        /********************************/
        /********************************/
    }
    
    /**
     * Loads the next song and then calls {@link GameMode#start()}.
     */
    public void next() {
        this.nextCalled = true;
        this.start();
    }
    public String getSong() {
        return this.gamePlaylist.currentSong().getTitle();
    }
    @Deprecated
    @Ignore
    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }
    
    public void pause() {
        this.musicPlayer.pause();
    }
    
    /**
     * @author Henock Arega
     * @project ReActReloaded
     * <p>
     * Used to replay the current song.
     */
    public void replay() {
        if (this.replayCount <= this.mode.maxReplay) {
            this.replayCount++;
            //        this.points -= POINTS / 2;
            this.musicPlayer.replay(this.mode.additionalTime);
        }
        this.notifyOfGameStatus();
    }
    
    public long getCurrentPlaytime() {
        return this.musicPlayer.getPlaytime();
    }
    
    @Deprecated
    @Ignore
    public void gameEnd() {
        this.user.setPlayedPlaylist(this.gamePlaylist);
        this.user.setPoints(this.points);
//        this.user.setReactionTimes(this.reactionTimes);
        setChanged();
        notifyObservers(this.gameStatus);
    }
    
    @Deprecated
    @Ignore
    public void end() {
        // TODO: end ACTIONS
        long end = System.currentTimeMillis();
        long millis = end / MILLIS_PER_SEC;
        long sec = (end / MILLIS_PER_SEC) % SEC_PER_MIN;
        long min = (end / (MILLIS_PER_SEC * SEC_PER_MIN)) % SEC_PER_MIN;
        System.out.println(String.format("%02d:%02d:%02d", min, millis, sec));
        this.musicPlayer.close(Code.CLOSE);
    }
    
    @Deprecated
    public boolean answer(int idx) {
        return this.answer(this.answers[idx % this.answers.length]);
    }
    
    /**
     * Evaluates the given answer and depending on the result and the player's game stat either the points will be
     * increased, if the answer is correct or the game will be ended.
     * Exceptions are:
     * -> when one has a combo: the combe will drop down to 1x and points will be decreased
     * -> depending on {@link Mode#CONTINUOUS} the game can just continue to the next one
     *
     * @param answer
     */
    public boolean answer(Song answer) {
        long time = System.currentTimeMillis();
//        musicPlayer.pause();
        this.isAnswered = true;
        if (answer.equals(this.musicPlayer.currentSong())) {
            this.correctAnswer = true;
            ANSI.GREEN.println("======================\n====  CORRECT ANSWER\n======================");
        } else {
            this.correctAnswer = false;
            ANSI.RED.println("======================\n====  WRONG ANSWER\n======================");
        }
        this.calcMultiplier();
//        this.reactionTimes.add(time - this.startTime);
        this.user.addReactionTime(time - this.startTime);
        return this.correctAnswer;
    }
    
    /** Calculates/defines the value of the multiplier. */
    private void calcMultiplier() {
        if (this.correctAnswer) {
            this.streak++;
            if (this.multiplier >= 1 && this.multiplier < MAX_MULT - 1 && streak > 0 && this.streak % this.step == 0) {
                this.multiplier++;
                this.step++;
                if (this.step >= this.MAXSTEP) this.step = this.MAXSTEP;
            }
            System.out.println("SRTEAK:\t" + streak);
            System.out.println("MULTI:\t " + multiplier);
        } else {
            this.streak = 0;
            this.step = this.MINSTEP;
            this.multiplier = this.MIN_MULT;
        }
    }
    
    private void stepUp(){
    
    }
    
    public int getReplayCount() {
        return replayCount;
    }
    
    public GameStatus getGameStatus() {
        return gameStatus;
    }
    
    public SongLibrary getSongLibrary() {
        return songLibrary;
    }
    
    public boolean isAnswered() {
        return isAnswered;
    }
    
    /**
     * A standardized method to add {@link #POINTS} to the {@link #points}. <br>
     * Calls {@link #addPoints(double)} with a value of {@literal 1}.
     */
    public void addPoints() {
        this.addPoints(1);
    }
    
    /**
     * A standardized method to subtract {@link #POINTS} from the {@link #points}. <br>
     * Calls {@link #addPoints(double)} with a value of {@literal -1}.
     */
    public void subtractPoints() {
        this.addPoints(-1);
    }
    
    /**
     * Adds to the points. Uses a multiplier (either positive or negative) to make it possible for combo points
     * to be added and directly calculated.
     *
     * @param multiplier A multiplier which multiplies the {@link #POINTS}. The product will then be added to {@link
     *                   #points}.
     */
    public void addPoints(double multiplier) {
        this.points += (((double) this.POINTS) * multiplier);
    }
    
    public Song[] getAnswers() {
        return answers;
    }
    
    protected Mode getMode() {
        return this.mode;
    }
    
    protected void notifyOfGameStatus() {
        setChanged();
        notifyObservers(this.gameStatus);
    }
    
    /**
     * Creates a selection for answers. The actual answer will be positioned at a random index.
     *
     * @param answerCount The amount of answers which are requested.
     * @return Returns a list of {@link Song} objects with one correct answer.
     */
    private Song[] createAnswers(int answerCount) {
        if (answerCount < this.STD_ANSWERCOUNT) answerCount = this.STD_ANSWERCOUNT;
        
        Song[] answers = new Song[answerCount];
        ArrayList<Song> answersList = new ArrayList<>();
        
        answers[0] = this.musicPlayer.currentSong();
        answersList.add(answers[0]);
        
        for (int i = 1; i < answerCount; i++) {
            answers[i] = this.gamePlaylist.getRandomSong(answersList);
            answersList.add(answers[i]);
            Collections.shuffle(answersList);
        }
        Collections.shuffle(answersList);
        Collections.shuffle(answersList, new Random((long) (Math.random() * System.currentTimeMillis() * 1000 / 17)));
        Collections.shuffle(answersList);
        this.answers = answersList.toArray(answers);
        
        ANSI.CYAN.println("======================");
        for (int i = 0; i < answerCount; i++) {
            ANSI.CYAN.println((i) + ":\t" + this.answers[i].getTitle() + " - " + this.answers[i].getTitle());
        }
        ANSI.CYAN.println("======================");
        setChanged();
        notifyObservers(answers);
        return this.answers;
    }
    
    //////////// OVERRIDES
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MusicPlayer) {
            /* when the musicplayer has changed (e.g. song is done) */
    
        }
        if (arg != null) {
            if (arg instanceof PlayerResult) {
                setChanged();
                notifyObservers(arg);
            }
        }
    }
    
    @Override
    public long getCurrentPlayTime() {
        return this.musicPlayer.getPlaytime();
    }
    
    @Override
    public void close(Code code) {
        if (code == Code.GAME_OVER) {
            /* If the game is done, change the game mode to game_over (in gameStatus). */
            this.gameStatus.mode = Mode.GAME_OVER;
        }
        this.musicPlayer.close(code);
        this.songLibrary.close(code);
    }
    
    /**
     * @author Henock Arega
     * @project ReActReloaded
     * <p>
     * TODO: make protected; is public for testing
     * TODO: make protected; is public for testing
     * TODO: make protected; is public for testing
     */
    public enum Mode {
        
        /**
         * @see model.gamemodes.NormalGame
         */
        NORMAL(1000, 1000, 8),
        /**
         * @see model.gamemodes.TimedGame
         */
        TIMED(1000, 1000, 3),
        /**
         * @see model.gamemodes.ContinuousGame
         */
        CONTINUOUS(1000, 0, 3),
        /** Song plays for up to {@link #lengthInMillis} seconds */
        REACTION(TimeUnit.SECONDS.toMillis(10), 0, 0),
        GAME_OVER;
    
        /** The time added to playback when replaying {@link Song}. */
        private long additionalTime;
        /** The beginning length of playback. */
        private long lengthInMillis;
        /** The mac count of replays allowed. */
        private int maxReplay;
    
        //////////// CONSTRUCTORS
        Mode() {
            this(0, 0, 0);
        }
    
        Mode(long length, long additionalTimeVal, int maxReplay) {
            this.lengthInMillis = length;
            this.additionalTime = additionalTimeVal;
            this.maxReplay = maxReplay;
        }
    
        Mode(long length) {
            this(length, 0, 0);
        }
        
        //////////// METHODS
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
        
        public long getAdditionalTime() {
            return this.additionalTime;
        }
    
        public int getMaxReplay() {
            return maxReplay;
        }
    
        public long getMaxReplayTimeMillis() {
            return maxReplay * additionalTime;
        }
    }
    
    /**
     * Will be used as an argument when notifying observers when e.g. {@link #replay()} is called.
     * Provides multiple {@link GameMode game values}.
     */
    public class GameStatus {
    
        private Mode mode;
    
        //////////// CONSTRUCTORS
        private GameStatus() {
            this.mode = GameMode.this.mode;
        }
    
        //////////// METHODS
        public boolean isAnswered() {
            return isAnswered;
        }
        
        public boolean correctAnswer() {
            return correctAnswer;
        }
        
        public int points() {
            return GameMode.this.points;
        }
        
        public int replayCount() {
            return GameMode.this.replayCount;
        }
        
        public long time() {
            return System.currentTimeMillis();
        }
        
        public User user() {
            return user;
        }
    
        public Mode mode() {
            return this.mode;
        }
        
        public Song[] answers() {
            return answers;
        }
    
        //////////// OVERRIDES
        @Override
        public String toString() {
            String str = "";
        
            str += "User:\t\t\t" + user.getName();
            str += System.lineSeparator();
            str += "Points:\t\t\t" + points;
//            if(isAnswered)
            str += System.lineSeparator();
            str += "Song:\t\t\t" + gamePlaylist.currentSong().getTitle() + ", " + gamePlaylist.currentSong().getArtist();
    
            ArrayList<Long> times = GameMode.this.user.getReactionTimes();
            if (times.size() >= 1) {
                str += System.lineSeparator();
                long minSec = TimeUnit.MILLISECONDS.toSeconds(GameMode.this.user.getMinReaction());
                long minMillis = GameMode.this.user.getMinReaction() - TimeUnit.SECONDS.toMillis(minSec);
                long maxSec = TimeUnit.MILLISECONDS.toSeconds(GameMode.this.user.getMaxReaction());
                long maxMillis = GameMode.this.user.getMaxReaction() - TimeUnit.SECONDS.toMillis(minSec);
                str += "Reaction (min/max):\t\t" + String.format("%d:%d sec / %d:%d sec", minSec, minMillis, maxSec, maxMillis);
            }
            return str;
        }
    }
    
    public class AlreadyStartedException extends IllegalStateException {
        
        public AlreadyStartedException() {
            super("The game has already been started once.");
        }
        
        public AlreadyStartedException(String s) {
            super(s);
        }
        
    }
    
}