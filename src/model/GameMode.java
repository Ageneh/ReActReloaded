package model;

import functions.ANSI;

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
    private static final int SEC_PER_MIN = 60;
    private static final int MILLIS_PER_SEC = 1000;
    private static final int MIN_PER_H = 60;
    /** The standard amount of answers at the beginning of a game. */
    private final int STD_ANSWERCOUNT;
    private final GameStatus gameStatus;
    protected final int POINTS = 10;
    public int maxAnswercount;
    /** The game mode of each game. */
    protected Mode mode;
    protected boolean correctAnswer;
    /** A multiplier used to multiply the given points depending on the {@link #streak}. */
    protected int multiplier;
    /** The {@link MusicPlayer} used to play each {@link Song random song}. */
    protected MusicPlayer musicPlayer;
    /** The playlist for each game round. Will be recreated with every game. */
    protected Playlist gamePlaylist;
    /** The song library used for each game. */
    protected SongLibrary songLibrary;
    protected int streak;
    /** A users object in which all the specific game data will be saved in for each {@link User player}. */
    protected ArrayList<User> users;
    protected int gameRound;
    /**
     * A flag used for {@link #answer(Song)}.
     *
     * @see #answer(Song)
     */
    private boolean isAnswered;
    /** The amount of answers which are defined by the actual game round */
    private int answerCount;
    /** The answers the {@link User} can choose from - including the correct answer. */
    private Song[] answers;
    /** A counter which will count the amount of times a song has been replayed. */
    private int replayCount;
    /** A flag which will be set when {@link #start()} has been called. */
    private boolean hasStarted;
    /** Logs the time when a game has started, as a reference for the duration of a game. */
    protected long startTime;
    /**
     * A flag which will be set when {@link #next()} has been called.
     * Will make it possible to call {@link #start()} through {@link #next()}.
     */
    private boolean nextCalled;
    private int step;
    protected int activeUser;
    
    private GameMode(Mode mode, Observer o, Observer... observers) {
        this.STD_ANSWERCOUNT = answerCount;
        this.mode = mode;
        this.users = new ArrayList<>();
        
        this.resetActiveUser();
        
        this.gameStatus = new GameStatus();
        
        this.musicPlayer = new MusicPlayer(this);
        this.startTime = 0;
        this.replayCount = 0;
        this.gameRound = 0;
        
        this.songLibrary = new SongLibrary();
        this.gamePlaylist = new Playlist(this.songLibrary);
        
        this.hasStarted = this.nextCalled = this.correctAnswer = this.isAnswered = false;
        
        this.step = MINSTEP;
        this.multiplier = this.MIN_MULT;
        
        this.answerCount = mode.getAnswerCount();
        this.maxAnswercount = mode.getMaxAnswerCount();
        
        this.addAllObserver(o, observers);
    }
    
    protected GameMode(Mode mode, String username, Observer o, Observer... observers) {
        this(mode, o, observers);
        addUser(username);
    }
    
    protected GameMode(Mode mode, String username1, String username2, Observer o, Observer... observers) {
        this(mode, o, observers);
        ArrayList<String> users = new ArrayList<>();
        users.add(username1);
        users.add(username2);
        for (String name : users) this.users.add(new User(name));
    }
    
    protected GameMode(Mode mode, ArrayList<String> usernames, Observer o, Observer... observers) {
        this(mode, o, observers);
        for (String name : usernames) this.users.add(new User(name));
    }
    
    //////////// METHODS
    
    /**
     * Called to start and play the next song. Can only be called once (from outside of this class); only when starting.
     * After initial call will only be called by {@link GameMode#next()} to play next.
     */
    public synchronized void start() {
        if (! this.hasStarted || (this.hasStarted && this.nextCalled)) {
            if (! this.hasStarted) this.hasStarted = true;
            this.nextCalled = false;
            this.isAnswered = false;
            this.replayCount = 0;
            this.startTime = System.currentTimeMillis();
            this.gameRound++;
            System.out.println("Game round::" + this.gameRound);
            
            this.musicPlayer.play(this.gamePlaylist.getNext(), this.mode.lengthInMillis);
            
            this.answers = this.createAnswers(this.answerCount); //// TESTING TESTING
            
            setChanged();
            notifyObservers(Action.ANSWERS.setVal(this.answers));
        } else {
            throw new AlreadyStartedException();
        }
    }
    
    /**
     * A standardized method to add {@link #POINTS} to the {@link User#points}. <br>
     * Calls {@link #addPoints(double)} with a value of {@literal 1}.
     */
    public void addPoints() {
        this.addPoints(1);
    }
    
    /**
     * Adds to the points. Uses a multiplier (either positive or negative) to make it possible for combo points
     * to be added and directly calculated.
     *
     * @param multiplier A multiplier which multiplies the {@link #POINTS}. The product will then be added to {@link
     *                   User#points}.
     */
    public void addPoints(double multiplier) {
        this.users.get(activeUser).setPoints(
                (int) (this.users.get(activeUser).getPoints() +
                        (((double) this.POINTS * this.multiplier)
                                * multiplier)));
        setChanged();
        notifyObservers(Action.POINTS.setVal(this.users.get(activeUser).getPoints()));
    }
    
    public void addUser(String username) {
        this.users.add(new User(username));
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
        musicPlayer.pause();
        this.isAnswered = true;
        setChanged();
        if (answer.equals(this.musicPlayer.currentSong()) || answer.getPath().equals(this.musicPlayer.currentSong().getPath())) {
            this.correctAnswer = true;
            ANSI.GREEN.println("======================\n====  CORRECT ANSWER\n======================");
            notifyObservers(Action.ANSWER_CORRECT);
        } else {
            this.correctAnswer = false;
            ANSI.RED.println("======================\n====  WRONG ANSWER\n======================");
            notifyObservers(Action.ANSWER_INCORRECT);
        }
        this.calcMultiplier();
//        this.reactionTimes.add(time - this.startTime);
        
        if (this.users.size() == 1) {
            activeUser = 0;
        }
        this.users.get(activeUser).addReactionTime(time - this.startTime);
        return this.correctAnswer;
    }
    
    public void endGame(boolean gameDone) {
        musicPlayer.stop();
        setChanged();
        if (gameDone) notifyObservers(Mode.GAME_DONE);
        else notifyObservers(Mode.GAME_OVER);
    }
    
    public Song[] getAnswers() {
        if (answers == null) {
            this.createAnswers(this.answerCount);
        }
        return answers;
    }
    
    public long getCurrentPlaytime() {
        return this.musicPlayer.getPlaytime();
    }
    
    public GameStatus getGameStatus() {
        return gameStatus;
    }
    
    public int getMultiplier() {
        return multiplier;
    }
    
    public int getPoints() {
        return this.users.get(activeUser).getPoints();
    }
    
    public int getReplayCount() {
        return replayCount;
    }
    
    public String getSong() {
        return this.gamePlaylist.currentSong().getTitle();
    }
    
    public SongLibrary getSongLibrary() {
        return songLibrary;
    }
    
    public int getStreak() {
        return streak;
    }
    
    public User getUser() {
        if (this.users.size() > 1) {
            return users.get(activeUser);
        }
        return this.users.get(0);
    }
    
    public User getWinner() {
        if (this.users.size() > 1) {
            ArrayList<User> users = new ArrayList<>(this.users);
            users.sort(Comparator.comparing(User::getPoints).reversed());
            return users.get(0);
        }
        return this.users.get(0);
    }
    
    public ArrayList<User> getUsers() {
        return users;
    }
    
    public void setUsers(String... usernames) {
        this.users = new ArrayList<>();
        for (String username : usernames) this.users.add(new User(username));
    }
    
    public boolean isAnswered() {
        return isAnswered;
    }
    
    /**
     * Loads the next song and then calls {@link GameMode#start()}.
     */
    public void next() {
        this.nextCalled = true;
        this.start();
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
//        this.notifyOfGameStatus();
        setChanged();
        notifyObservers(Action.REPLAY);
    }
    
    public void resetActiveUser() {
        if (this.users.size() > 1) this.activeUser = - 1;
        if (this.users.size() == 1) this.activeUser = 0;
    }
    
    public void setActiveUser(String username) {
        if (this.users.size() == 1) {
            activeUser = 0;
            return;
        }
        for (User user : this.users) {
            if (user.getName().equals(username)) {
                this.activeUser = this.users.indexOf(user);
                setChanged();
                notifyObservers(Action.ACTIVE_USER.setVal(this.users.get(this.activeUser)));
                return;
            }
        }
        this.activeUser = - 1;
    }
    
    public void setActiveUser(User user) {
        if (this.users.size() == 1) {
            activeUser = 0;
            return;
        }
        this.activeUser = this.users.indexOf(user);
        if (activeUser >= 0 && activeUser < this.users.size()) {
            setChanged();
            notifyObservers(Action.ACTIVE_USER.setVal(this.users.get(this.activeUser)));
        }
    }
    
    public void setActiveUser(int indx) {
        if (this.users.size() == 1) {
            activeUser = 0;
            return;
        }
        if (indx >= 0 && indx < this.users.size()) {
            this.activeUser = indx;
            setChanged();
            notifyObservers(Action.ACTIVE_USER.setVal(this.users.get(this.activeUser)));
        }
    }
    
    public void setAnswerCount(int answerCount) {
        if (answerCount > this.maxAnswercount) answerCount = maxAnswercount;
        if (answerCount < this.STD_ANSWERCOUNT) answerCount = STD_ANSWERCOUNT;
        this.answerCount = answerCount;
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
     * A standardized method to subtract {@link #POINTS} from the {@link User#points}. <br>
     * Calls {@link #addPoints(double)} with a value of {@literal -1}.
     */
    public void subtractPoints() {
        this.addPoints(- 1);
    }
    
    /** Calculates/defines the value of the multiplier. */
    protected void calcMultiplier() {
        if (this.correctAnswer) {
            this.streak++;
            if (this.multiplier >= MIN_MULT && this.multiplier < MAX_MULT - 1 && streak > 0 && this.streak % this.step == 0) {
                this.multiplier++;
                streak = 0;
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
        setChanged();
        notifyObservers(Action.NEW_MULTIPLIER.setVal(this.multiplier));
    }
    
    protected Playlist getGamePlaylist() {
        return gamePlaylist;
    }
    
    protected Mode getMode() {
        return this.mode;
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
        if (answers[0] == null) {
            answers[0] = this.gamePlaylist.currentSong();
        }
        answersList.add(answers[0]);
        
        for (int i = 1; i < answerCount; i++) {
            try {
                answers[i] = this.gamePlaylist.getRandomSong(answersList);
            } catch (Exception e) {
                System.out.println();
            }
            answersList.add(answers[i]);
            Collections.shuffle(answersList);
        }
        Collections.shuffle(answersList);
        Collections.shuffle(answersList, new Random((long) (Math.random() * System.currentTimeMillis() * 1000 / 17)));
        Collections.shuffle(answersList);
        
        ANSI.CYAN.println("======================");
        for (int i = 0; i < answerCount; i++) {
            ANSI.CYAN.println((i) + ":\t" + answers[i].getTitle() + " - " + answers[i].getTitle());
        }
        ANSI.CYAN.println("======================");
        
        setChanged();
        notifyObservers(Action.ANSWERS.setVal(answers));
        
        return answersList.toArray(answers);
    }
    
    private void initAnswerCount(int answerCount, int maxAnswercount) {
        if (answerCount < 2) answerCount = 2;
        if (maxAnswercount < 2) answerCount = 2;
        
        if (answerCount > maxAnswercount) {
            this.answerCount = maxAnswercount;
            this.maxAnswercount = answerCount;
            return;
        }
        this.answerCount = answerCount;
        this.maxAnswercount = maxAnswercount;
    }
    
    private void stepUp() {
    
    }
    
    //////////// OVERRIDES
    @Override
    public void update(Observable o, Object arg) {
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
        NORMAL(1000, 1000, 8, 3, 5),
        /**
         * @see model.gamemodes.TimedGame
         */
        TIMED(1000, 1000, 3, 3, 3),
        /**
         * @see model.gamemodes.ContinuousGame
         */
        CONTINUOUS(1000, 0, 3, 3, 6),
        /** Song plays for up to {@link #lengthInMillis} seconds */
        REACTION(TimeUnit.SECONDS.toMillis(10), 0, 0),
        GAME_OVER,
        GAME_DONE;
        
        /** The time added to playback when replaying {@link Song}. */
        private long additionalTime;
        /** The beginning length of playback. */
        private long lengthInMillis;
        /** The mac count of replays allowed. */
        private int maxReplay;
        private int answerCount = 3;
        private int maxAnswerCount = 5;
        
        //////////// CONSTRUCTORS
        Mode() {
            this(0, 0, 0);
        }
        
        Mode(long length, long additionalTimeVal, int maxReplay) {
            this.lengthInMillis = length;
            this.additionalTime = additionalTimeVal;
            this.maxReplay = maxReplay;
        }
        
        Mode(long length, long additionalTimeVal, int maxReplay, int answerCount, int maxAnswerCount) {
            this.lengthInMillis = length;
            this.additionalTime = additionalTimeVal;
            this.maxReplay = maxReplay;
            this.answerCount = answerCount;
            this.maxAnswerCount = maxAnswerCount;
        }
        
        Mode(long length) {
            this(length, 0, 0);
        }
        
        public long getAdditionalTime() {
            return this.additionalTime;
        }
        
        public int getAnswerCount() {
            return answerCount;
        }
        
        public long getLengthInMillis() {
            return lengthInMillis;
        }
        
        public int getMaxAnswerCount() {
            return maxAnswerCount;
        }
        
        public int getMaxReplay() {
            return maxReplay;
        }
        
        public long getMaxReplayTimeMillis() {
            return maxReplay * additionalTime;
        }
        
        public long hours() {
            return TimeUnit.MILLISECONDS.toHours(this.lengthInMillis);
        }
        
        //////////// METHODS
        public long millis() {
            return TimeUnit.MILLISECONDS.toMillis(this.lengthInMillis);
        }
        
        public long minutes() {
            return TimeUnit.MILLISECONDS.toMinutes(this.lengthInMillis);
        }
        
        public long seconds() {
            return TimeUnit.MILLISECONDS.toSeconds(this.lengthInMillis);
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
        
        public Song[] answers() {
            return answers;
        }
        
        public boolean correctAnswer() {
            return correctAnswer;
        }
        
        //////////// METHODS
        public boolean isAnswered() {
            return isAnswered;
        }
        
        public Mode mode() {
            return this.mode;
        }
        
        public int points() {
            return GameMode.this.users.get(activeUser).getPoints();
        }
        
        public int replayCount() {
            return GameMode.this.replayCount;
        }
        
        public long time() {
            return System.currentTimeMillis();
        }
        
        public ArrayList<User> user() {
            return users;
        }
        
        //////////// OVERRIDES
        @Override
        public String toString() {
            String str = "";
            
            str += "Users:\t\t\t" + users.toString();
            str += System.lineSeparator();
//            str += "Points:\t\t\t" + points;
//            if(isAnswered)
            str += System.lineSeparator();
            str += "Song:\t\t\t" + gamePlaylist.currentSong().getTitle() + ", " + gamePlaylist.currentSong().getArtist();

//            ArrayList<Long> times = GameMode.this.users.getReactionTimes();
//            if (times.size() >= 1) {
//                str += System.lineSeparator();
//                long minSec = TimeUnit.MILLISECONDS.toSeconds(GameMode.this.users.getMinReaction());
//                long minMillis = GameMode.this.users.getMinReaction() - TimeUnit.SECONDS.toMillis(minSec);
//                long maxSec = TimeUnit.MILLISECONDS.toSeconds(GameMode.this.users.getMaxReaction());
//                long maxMillis = GameMode.this.users.getMaxReaction() - TimeUnit.SECONDS.toMillis(minSec);
//                str += "Reaction (min/max):\t\t" + String.format("%d:%d sec / %d:%d sec", minSec, minMillis, maxSec, maxMillis);
//            }
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
