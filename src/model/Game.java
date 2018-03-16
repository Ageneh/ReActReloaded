package model;

import functions.ANSI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

/**
 * @author Henock Arega
 * @project ReActReloaded
 * <p>
 * <p>
 * TODO: block giving answer (GUI/JavaFX) while song is playing
 */
public abstract class Game extends ObservableModel implements Observer {
    
    private final static int MILLIS_PER_SEC = 1000;
    private final static int SEC_PER_MIN = 60;
    private final static int MIN_PER_H = 60;
    private final GameStatus gameStatus;
    private final int STD_ANSWERCOUNT = 2;
    private final int STD_REPLAY = 1;
    private final int POINTS = 20;
    /** The answers the {@link User} can choose from - including the correct answer. */
    private Song[] answers;
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
    private ArrayList<Long> reactionTimes;
    private boolean isAnswered;
    private boolean correctAnswer;
    
    protected Game(GameMode mode, Observer o, Observer... observers) {
        this(mode, "ReActor", o, observers);
    }
    
    protected Game(GameMode mode, String username, Observer o, Observer... observers) {
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
        this.reactionTimes = new ArrayList<>();
        
        this.gameStatus = new GameStatus();
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
     * Loads the next song and then calls {@link Game#start()}.
     */
    public void next() {
        this.nextCalled = true;
        this.start();
    }
    
    /**
     * Called to start and play the next song.
     * Can only be called once (from outside of this class); only when starting.
     * After initial call will only be called by {@link Game#next()} to play next.
     */
    public void start() {
        if (!this.hasStarted || (this.hasStarted && this.nextCalled)) {
            if (!this.hasStarted) this.hasStarted = true;
            this.startTime = System.currentTimeMillis();
            this.nextCalled = false;
            this.isAnswered = false;
            this.replayCount = STD_REPLAY;
            this.musicPlayer.play(this.gamePlaylist.getNext(), this.mode.lengthInMillis);
            
            this.createAnswers(3); //// TESTING TESTING
            
            setChanged();
            notifyObservers(this.gameStatus);
        }
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
        setChanged();
        if (this.replayCount >= this.mode.maxReplay - 1) {
            notifyObservers(this.mode); //if maxReplayCount-1 is reached return the mode so that the max count can be used and compared
            return;
        }
        this.replayCount++;
        this.points -= POINTS / 2;
        
        this.musicPlayer.replay(this.mode.additionalTime);
        notifyObservers(this.gameStatus);
    }
    
    public void end() {
        // TODO: end ACTIONS
        long end = System.currentTimeMillis();
        long millis = end / MILLIS_PER_SEC;
        long sec = (end / MILLIS_PER_SEC) % SEC_PER_MIN;
        long min = (end / (MILLIS_PER_SEC * SEC_PER_MIN)) % SEC_PER_MIN;
        System.out.println(String.format("%02d:%02d:%02d", min, millis, sec));
        this.musicPlayer.close(Code.CLOSE);
    }
    
    public void gameEnd() {
        this.user.setPlayedPlaylist(this.gamePlaylist);
        this.user.setPoints(this.points);
        this.user.setReactionTimes(this.reactionTimes);
        setChanged();
        notifyObservers(this.gameStatus);
    }
    
    public boolean answer(int idx) {
        return this.answer(this.answers[idx % this.answers.length]);
    }
    
    /**
     * Evaluates the given answer and depending on the result and the player's game stat either the points will be
     * increased, if the answer is correct or the game will be ended.
     * Exceptions are:
     * -> when one has a combo: the combe will drop down to 1x and points will be decreased
     * -> depending on {@link GameMode#CONTINUOUS} the game can just continue to the next one
     *
     * @param answer
     */
    public boolean answer(Song answer) {
        long time = System.currentTimeMillis();
//        musicPlayer.pause();
        this.isAnswered = true;
        if (answer.equals(this.musicPlayer.currentSong())) {
            this.correctAnswer = true;
            ANSI.GREEN.println("======================");
            ANSI.GREEN.println("====  CORRECT ANSWER");
            ANSI.GREEN.println("======================");
        } else {
            this.correctAnswer = false;
            ANSI.RED.println("======================");
            ANSI.RED.println("====  WRONG ANSWER");
            ANSI.RED.println("======================");
        }
        this.reactionTimes.add(time - this.startTime);
//        setChanged();
//        notifyObservers(this.gameStatus);
        return this.correctAnswer;
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
     * Calls {@link #addPoints(int)} with a value of {@literal 1}.
     */
    public void addPoints() {
        this.addPoints(1);
    }
    
    /**
     * A standardized method to subtract {@link #POINTS} from the {@link #points}. <br>
     * Calls {@link #addPoints(int)} with a value of {@literal -1}.
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
    void addPoints(int multiplier) {
        this.points += (this.POINTS * multiplier);
    }
    
    protected GameMode getMode() {
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
        this.answers = answersList.toArray(answers);
        
        ANSI.CYAN.println("======================");
        for (int i = 0; i < answerCount; i++) {
            ANSI.CYAN.println((i) + ":\t" + this.answers[i].getTitle() + " - " + this.answers[i].getTitle());
        }
        ANSI.CYAN.println("======================");
        return this.answers;
    }
    
    /**
     * @see #createAnswers(int)
     */
    private Song[] createAnswers() {
        return this.createAnswers(STD_ANSWERCOUNT);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MusicPlayer) {
            /* when the musicplayer has changed (e.g. song is done) */
            
        }
    }
    
    @Override
    public void close(Code code) {
        if (code == Code.GAME_OVER) {
            /* If the game is done, change the game mode to game_over (in gameStatus). */
            this.gameStatus.mode = GameMode.GAME_OVER;
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
    public enum GameMode {
        
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
        CONTINUOUS(1000, 0, 3),
        /** Song plays for up to {@link #lengthInMillis} seconds */
        REACTION(TimeUnit.SECONDS.toMillis(10), 0, 0),
        GAME_OVER;
        
        private long lengthInMillis;
        private long additionalTime;
        private int maxReplay;
        
        GameMode() {
            this(0, 0, 0);
        }
        
        GameMode(long length, long additionalTimeVal, int maxReplay) {
            this.lengthInMillis = length;
            this.additionalTime = additionalTimeVal;
            this.maxReplay = maxReplay;
        }
        
        GameMode(long length) {
            this(length, 0, 0);
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
        
        public long getAdditionalTime() {
            return this.additionalTime;
        }
        
    }
    
    /**
     * Will be used as an argument when notifying observers when e.g. {@link #replay()} is called.
     * Provides multiple {@link Game game values}.
     */
    public class GameStatus {
        
        private GameMode mode;
        
        private GameStatus() {
            this.mode = Game.this.mode;
        }
        
        public boolean isAnswered() {
            return isAnswered;
        }
        
        public boolean correctAnswer() {
            return correctAnswer;
        }
        
        public int points() {
            return Game.this.points;
        }
        
        public int replayCount() {
            return Game.this.replayCount;
        }
        
        public long time() {
            return System.currentTimeMillis();
        }
        
        public User user() {
            return user;
        }
        
        public GameMode mode() {
            return this.mode;
        }
        
        public Song[] answers() {
            return answers;
        }
        
        @Override
        public String toString() {
            String str = "";
            
            str += "User:\t\t\t" + user.getName();
            str += System.lineSeparator();
            str += "Points:\t\t\t" + points;
//            if(isAnswered)
            str += System.lineSeparator();
            str += "Song:\t\t\t" + gamePlaylist.currentSong().getTitle() + ", " + gamePlaylist.currentSong().getArtist();
            
            if (reactionTimes.size() >= 1) {
                str += System.lineSeparator();
                long sec = TimeUnit.MILLISECONDS.toSeconds(reactionTimes.get(reactionTimes.size() - 1));
                long millisec = reactionTimes.get(reactionTimes.size() - 1) - TimeUnit.SECONDS.toMillis(sec);
                str += "Reaction:\t\t" + String.format("%d:%d sec", sec, millisec);
            }
            return str;
        }
    }
    
}
