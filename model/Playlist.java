package model;

import java.util.ArrayList;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class Playlist {

    private ArrayList<String> songs;
    private int pos;

    Playlist(ArrayList<String> songpaths){
        this.pos = 0;
        this.songs = songpaths;
    }

    public Song getNext(){
        // TODO: fix return of songs; add Exception when last song was already played
        if(pos < songs.size() - 1) {
            return new Song(this.songs.get(pos++));
        }
        return new Song(this.songs.get((int)(Math.random()) * songs.size()));
    }

    public ArrayList<String> getSongs() {
        return songs;
    }

    void replaceAllWith(ArrayList<String> newPaths){
        this.songs.clear();
        this.songs.addAll(newPaths);
    }

}
