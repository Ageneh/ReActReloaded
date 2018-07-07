package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class Playlist {
    
    /** A marker for the showing the current song. */
    private int pos;
    /** A collection of song paths. */
    private final ArrayList<String> allSongs;
    private ArrayList<String> songs;
    private Stack<String> playedSongs;
    private boolean endless;
    
    public Playlist(SongLibrary library) {
        this(library.getSongs());
    }
    
    public Playlist(ArrayList<String> songpaths) {
        this(songpaths, false, false);
    }
    
    public Playlist(SongLibrary library, boolean randomized, boolean endless) {
        this(library.getSongs(), randomized, endless);
    }
    
    public Playlist(ArrayList<String> songpaths, boolean randomized, boolean endless) {
        this.pos = 0;
        if (randomized) {
            Collections.shuffle(songpaths, new Random(2345));
            Collections.shuffle(songpaths, new Random(17));
            Collections.shuffle(songpaths, new Random(42));
        }
        this.endless = endless;
        this.songs = new ArrayList<>(songpaths);
        this.allSongs = new ArrayList<>(songpaths);
        this.playedSongs = new Stack<>();
    }
    
    public Song currentSong() {
        if (pos == 0) return new Song(this.songs.get(pos));
        return new Song(this.songs.get(pos - 1));
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
    
    public ArrayList<String> getSongs() {
        return songs;
    }
    
    public int getTotalSongCount() {
        return this.allSongs.size();
    }
    
    /**
     * @return Returns a random {@link Song} from {@link #songs}
     */
    Song getRandomSong(ArrayList<Song> exclude) {
        ArrayList<String> temp = new ArrayList<>(songs.size());
        if(this.songs.size() < this.allSongs.size() / 2 && this.endless){
            this.songs.clear();
            this.songs.addAll(new ArrayList<>(this.allSongs));
            Collections.shuffle(this.songs);
        }
        temp.addAll(this.allSongs);
        for (Song song : exclude) temp.remove(song.getPath());
        
        int rdm = ((int) (Math.random() * 4)) + 2;
        for (int i = 0; i < rdm; i++) Collections.shuffle(temp);
        
        return new Song(temp.get((int) (Math.random() * temp.size())));
    }
    
    void replaceAllWith(ArrayList<String> newPaths) {
        this.songs.clear();
        this.songs.addAll(newPaths);
    }
    
}
