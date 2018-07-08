package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class User implements Serializable {
    
    public static final String STD_NAME = "ReActPlayer";
    private final String FORMAT = "dd-MM-yyyy";
    /** A list which shows which {@link Song} has been answered correctly. */
    private ArrayList<Boolean> correctAnswered;
    /** The {@link LocalDateTime day and time} the users played. */
    private LocalDateTime datePlayed;
    private String datePlayedFormatted;
    /** The slowest reaction time beginning with the start of a {@link Song}. */
    private long maxReaction;
    /** The fastest reaction time beginning with the start of a {@link Song}. */
    private long minReaction;
    /** The name of the User. Used for ranking. */
    private String name;
    /** A {@link Playlist} containing the list of {@link Song songs} which have been answered. */
    private Playlist playedPlaylist;
    /** The amount of points reached. */
    private int points;
    /** A collection of all reaction times in a game. Used e.g. to get the fastest an slowest reaction times. */
    private ArrayList<Long> reactionTimes;
    
    public User(String name) {
        if (name == null || (name != null && name.isEmpty())) this.name = STD_NAME;
        else this.name = name;
        this.reactionTimes = new ArrayList<>();
        this.points = 0;
        this.datePlayed = LocalDateTime.now();
        datePlayedFormatted = this.datePlayed.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.correctAnswered = new ArrayList<>();
        this.minReaction = this.maxReaction = this.points;
    }
    
    public void addReactionTime(long milliseconds) {
        this.reactionTimes.add(milliseconds);
        this.minReaction = Collections.min(this.reactionTimes);
        this.maxReaction = Collections.max(this.reactionTimes);
    }
    
    public LocalDateTime getDatePlayed() {
        return datePlayed;
    }
    
    public String getDatePlayedFormatted() {
        return datePlayedFormatted;
    }
    
    public long getMaxReaction() {
        return maxReaction;
    }
    
    public long getMinReaction() {
        return minReaction;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public Playlist getPlayedPlaylist() {
        return playedPlaylist;
    }
    
    void setPlayedPlaylist(Playlist playedPlaylist) {
        this.playedPlaylist = playedPlaylist;
    }
    
    //////////// METHODS
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    
    public ArrayList<Long> getReactionTimes() {
        return reactionTimes;
    }
    
    @Override
    public String toString() {
        return String.format("%s,%s,%d", this.name, this.datePlayed.format(DateTimeFormatter.ofPattern(FORMAT)), this.points);
    }
}
