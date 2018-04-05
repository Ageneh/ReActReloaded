package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
class Playlist {
    
    /** A marker for the showing the current song. */
    private int pos;
    /** A collection of song paths. */
    private ArrayList<String> songs;
    
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
    }
    
    public Song getNext() {
        // TODO: fix return of songs; add Exception when last song was already played
        if (pos < songs.size() - 1) {
            return new Song(this.songs.get(pos++));
        }
        return new Song(this.songs.get((int) (Math.random()) * songs.size()));
    }
    
    public ArrayList<String> getSongs() {
        return songs;
    }
    
    void replaceAllWith(ArrayList<String> newPaths) {
        this.songs.clear();
        this.songs.addAll(newPaths);
    }
    
    Song currentSong() {
        return new Song(this.songs.get(pos - 1));
    }
//////////// METHODS
    /**
     * @param exlcude These songs should be excluded.
     * @return Returns a random {@link Song} from {@link #songs}
     */
    Song getRandomSong(ArrayList<Song> exlcude) {
        ArrayList<String> temp = new ArrayList<>(songs.size());
        temp.addAll(this.songs);
        
        for (Song song : exlcude) temp.remove(song.getPath());
        
        int rdm = ((int) (Math.random() * 4)) + 2;
        for (int i = 0; i < rdm; i++) Collections.shuffle(temp);
        
        return new Song(temp.get((int) (Math.random() * temp.size())));
    }
    
}
