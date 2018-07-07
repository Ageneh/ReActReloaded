package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class Playlist {
    
    /** A marker for the showing the current song. */
    private int pos;
    /** A collection of song paths. */
    private ArrayList<String> songs;
    private Stack<String> playedSongs;
    
    Playlist(ArrayList<String> songpaths) {
        this(songpaths, false);
    }
    
    Playlist(ArrayList<String> songpaths, boolean randomized) {
        this.pos = 0;
        if (randomized) {
            Collections.shuffle(songpaths);
            Collections.shuffle(songpaths);
            Collections.shuffle(songpaths);
        }
        this.songs = songpaths;
        this.playedSongs = new Stack<>();
    }
    
    public Song currentSong() {
        if (pos == 0) return new Song(this.songs.get(pos));
        return new Song(this.songs.get(pos - 1));
    }
    
    public ArrayList<String> getSongs() {
        return songs;
    }
    
    public Song getNext() {
        if (pos >= this.songs.size()) {
            throw new ArrayIndexOutOfBoundsException("Reached last song.");
        }
        String song = this.songs.get((int) (Math.random()) * songs.size());
        this.playedSongs.push(song);
        this.songs.remove(song);
        return new Song(song);
    }
    
    /**
     * @return Returns a random {@link Song} from {@link #songs}
     */
    Song getRandomSong() {
        ArrayList<String> temp = new ArrayList<>(songs.size());
        temp.addAll(this.songs);
    
        for (String song : this.playedSongs) temp.remove(song);
        
        int rdm = ((int) (Math.random() * 4)) + 2;
        for (int i = 0; i < rdm; i++) Collections.shuffle(temp);
        
        return new Song(temp.get((int) (Math.random() * temp.size())));
    }
//////////// METHODS
    
    void replaceAllWith(ArrayList<String> newPaths) {
        this.songs.clear();
        this.songs.addAll(newPaths);
    }
    
}
