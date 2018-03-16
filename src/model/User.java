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
    
    private String name;
    private int points;
    private LocalDateTime datePlayed;
    private Playlist playedPlaylist;
    /** A list which shows which {@link Song} has been answered correctly. */
    private ArrayList<Boolean> correctAnswered;
    /** The fastest reaction time beginning with the start of a {@link Song}. */
    private long minReaction;
    /** The slowest reaction time beginning with the start of a {@link Song}. */
    private long maxReaction;
    
    public User(String name) {
        if (name == null || (name != null && name.isEmpty())) this.name = getRandomName();
        else this.name = name;
        this.points = 0;
        this.datePlayed = LocalDateTime.now();
        this.correctAnswered = new ArrayList<>();
        this.minReaction = this.maxReaction = this.points;
    }
    
    public String getName() {
        
        return name;
    }
    
    public LocalDateTime getDatePlayed() {
        
        return datePlayed;
    }
    
    public int getPoints() {
        
        return points;
    }
    
    void setPoints(int points) {
        
        this.points = points;
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
    
    void setPlayedPlaylist(Playlist playedPlaylist) {
        
        this.playedPlaylist = playedPlaylist;
    }
    
    void setReactionTimes(ArrayList<Long> milliseconds) {
        
        this.minReaction = Collections.min(milliseconds);
        this.maxReaction = Collections.max(milliseconds);
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
