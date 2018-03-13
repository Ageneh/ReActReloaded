package model;

import functions.INIReader;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author Henock Arega
 * @project ReActReloaded
 *
 * A class used to manage all folders, which are to be used as source folders.
 *
 * Every time a playlist is needed one will be created by randomising the order of songs which are to be played.
 */
class SongLibrary implements Close {

    private static final String INI_PATH = "/Users/HxA/IdeaProjects/ReActReloaded/res/init/music.ini";
    private static final String SECTION = "MUSIC";
    /** The prefix of key which points to a directory which contains mp3-files. */
    private static final String KEY_PRE = "MDIR";

    /** Has all paths containing mp3-files. */
    private static ArrayList<String> folderPaths;
    /**
     * A playlist of all given songs.
     */
    private Playlist playlist;

    SongLibrary(){
        this.folderPaths = new ArrayList<>();
        Ini ini = null;
        try {
            ini = INIReader.iniReader(INI_PATH);
            Section section = ini.get(SECTION);
            File temp;
            HashSet<String> tempSet = new HashSet<>();
            for(String s : section.keySet()){
                if(!s.startsWith(KEY_PRE)) continue; // ignore key if it doesn't start with the correct prefix
                temp = new File(ini.get(SECTION, s));
                if(temp.exists()){
                    tempSet.add(ini.get(SECTION, s));
                }
            }
            this.folderPaths.addAll(tempSet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks the given paths and their contents.
     * @param paths Paths which are to be checked and analysed.
     * @return Returns a list of music files (mp3-files).
     */
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
                        if(isMusicFile(file)) {
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

    /** @see #checkPaths(ArrayList) */
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
        HashSet<String> tempSet = new HashSet<>();
        File file;
        for(String s : paths){
            file = new File(s);
            if(isDir(file) || isMusicFile(file)){
                tempSet.add(file.getAbsolutePath());
            }
        }
        this.folderPaths.addAll(tempSet);
        tempSet.clear();
        tempSet.addAll(this.folderPaths);
        this.folderPaths.clear();
        this.folderPaths.addAll(tempSet);
        Collections.sort(folderPaths);

        this.writeINI();
    }

    /**
     * Checks whether the given file is a directory containing mp3 files.
     * @param file If the file is a directory and contains mp3 files true will be returne otherwise the return
     *             value is false.
     * @return Boolean telling if there is a mp3 file in the directory.
     */
    private boolean isDir(File file){
        if(file.exists() && !file.isHidden() && file.isDirectory() && file.list().length > 0){
            return true;
        }
        return false;
    }

    /**
     * Checks whether the given file is a mp3 file.
     * @param file If the file is a mp3 file true will be returned otherwise the return
     *             value is false.
     */
    private boolean isMusicFile(File file){
        if(this.isDir(file)) return false;
        if(file.getName().endsWith(Song.EXTENSION) && !file.isHidden()){
            return true;
        }
        return false;
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
        if(playlist == null){
            playlist = new Playlist(this.folderPaths);
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
            this.playlist = new model.Playlist(this.folderPaths);
        }
        return playlist;
    }

    public static ArrayList<String> getFolderPaths() {
        return folderPaths;
    }

    /** Writes all directories containing the music to a file. */
    private void writeINI(){
        Ini ini;
        File temp;
        Section section;
        HashSet<String> tempSet;
        BufferedWriter br;
        try {
            ini = INIReader.iniReader(INI_PATH);
            temp = new File(INI_PATH);
            ini.load(temp);
            ini.clear();
            for(int i = 0; i < this.folderPaths.size(); i++) {
                ini.put(SECTION,
                        KEY_PRE+i,
                        this.folderPaths.get(i));
            }
            br = new BufferedWriter(new FileWriter(temp));
            ini.store(br);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFileFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close(Code code) {
        this.writeINI();
    }

}