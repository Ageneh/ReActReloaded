package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class User {
    
    /** A list which shows which {@link Song} has been answered correctly. */
    private ArrayList<Boolean> correctAnswered;
    /** The {@link LocalDateTime day and time} the user played. */
    private LocalDateTime datePlayed;
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
        if (name == null || (name != null && name.isEmpty())) this.name = getRandomName();
        else this.name = name;
        this.reactionTimes = new ArrayList<>();
        this.points = 0;
        this.datePlayed = LocalDateTime.now();
        this.correctAnswered = new ArrayList<>();
        this.minReaction = this.maxReaction = this.points;
    }
    
    //////////// METHODS
    public int getPoints() {
        return points;
    }

    public long getMinReaction() {
        return minReaction;
    }
    
    public long getMaxReaction() {
        return maxReaction;
    }
    
    public Playlist getPlayedPlaylist() {
        return playedPlaylist;
    }
    
    public String getName() {
        return name;
    }
    
    public LocalDateTime getDatePlayed() {
        return datePlayed;
    }
    
    void setPoints(int points) {
        this.points = points;
    }
    
    void setPlayedPlaylist(Playlist playedPlaylist) {
        this.playedPlaylist = playedPlaylist;
    }
    public ArrayList<Long> getReactionTimes() {
        return reactionTimes;
    }
    void addReactionTime(long milliseconds) {
        this.reactionTimes.add(milliseconds);
        this.minReaction = Collections.min(this.reactionTimes);
        this.maxReaction = Collections.max(this.reactionTimes);
    }
    private String getRandomName() {
        final String namePath = "/Users/HxA/IdeaProjects/ReActReloaded/res/init/names.txt";
        String str = "Player";
        try (BufferedReader br = new BufferedReader(new FileReader(new File(namePath)))) {
            String line;
            int i = 0;
            long lineCount = ((int) Math.random()) * br.lines().count();
            System.out.println(br.readLine());
            while ((line = br.readLine()) != null) {
                if (i++ >= lineCount && !line.isEmpty()) {
                    return line;
                }
                lineCount++;
            }
            return line;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
    
}
