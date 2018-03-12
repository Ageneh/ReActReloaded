package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Henock Arega
 * @project ReActReloaded
 *
 * A class used to manage all folders, which are to be used as source folders.
 *
 * Every time a playlist is needed one will be created by randomising the order of songs which are to be played.
 */
public class SongLibrary {

    private ArrayList<String> folderPaths;
    private Playlist playlist;

    SongLibrary(){
        this.folderPaths = new ArrayList<>();
    }

    public SongLibrary(String path, String ... paths){
        this();
        String[] temp_arr = new String[paths.length + 1];
        temp_arr[0] = path;
        for(int i = 0; paths != null && i < paths.length; i++){
            temp_arr[i+1] = paths[i];
        }
        this.folderPaths = this.checkPaths(temp_arr);
    }

    private ArrayList<String> checkPaths(String ... paths){
        ArrayList<String> temp_list = new ArrayList<>();
        File temp_file;

        for(String path : paths){
            temp_file = new File(path);
            if(temp_file.exists()){
                if(temp_file.isDirectory() && temp_file.list().length > 0) {
                    File[] listFiles = temp_file.listFiles();
                    ArrayList<String> listFiles_paths = new ArrayList<>();
                    for(File file : listFiles){
                        if(file.getName().endsWith(Song.EXTENSION)) {
                            listFiles_paths.add(file.getAbsolutePath());
                        }
                    }
                    temp_list.addAll(checkPaths(listFiles_paths));
                }
                else if(temp_file.getName().endsWith(Song.EXTENSION)){
                    temp_list.add(temp_file.getAbsolutePath());
                }
            }
        }
        return temp_list;
    }

    private ArrayList<String> checkPaths(ArrayList<String> paths){
        if(paths.size() > 0) {
            String[] temp = new String[paths.size()];
            int i = 0;
            for(String path : paths){
                temp[i++] = path;
            }
            return this.checkPaths(temp);
        }
        return null;
    }

    /**
     * Adds all the parts of the argument to the list.
     * @param paths The paths which are to be added to the list.
     * @see SongLibrary#folderPaths
     */
    public void addToLib(String ... paths){
        this.folderPaths.addAll(checkPaths(paths));
    }

    /**
     * Removes paths from {@link SongLibrary#folderPaths} if the given path are contained in the list.
     * @param paths The paths which are to be removed from the list.
     * @see SongLibrary#folderPaths
     */
    public void removeFromLib(String ... paths){
        ArrayList<String> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(paths));
        this.folderPaths.removeAll(temp);
    }

    public ArrayList<String> getLibrary(){
        return this.folderPaths;
    }

    /**
     * Create a {@link Playlist} object which contains all a certain amount of songpaths and in a randomized order.
     */
    private void createPlaylist(){
        if(this.playlist == null){
            this.playlist = new Playlist(this.folderPaths);
        }
        else if(this.playlist.getSongs().size() == this.folderPaths.size()){
            boolean areSame = true;
            int i = 0;
            while(areSame && i < this.folderPaths.size()){
                if(this.playlist.getSongs().contains(this.folderPaths.get(i++))) continue;
                else areSame = false;
            }
            if(!areSame) {
                this.playlist.replaceAllWith(this.folderPaths);
            }
        }
    }

    public Playlist getPlaylist() {
        // TODO: random order of songs for every playlist
        if(this.folderPaths == null || this.folderPaths.size() == 0){
            return null;
        }
        if(this.playlist == null){
            this.playlist = new Playlist(this.folderPaths);
        }
        return playlist;
    }

}
