package model;

import functions.INIReader;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;

import java.io.*;
import java.util.*;

/**
 * <p>
 * A class used to manage all folders, which are to be used as source folders.
 * <p>
 * Every time a playlist is needed one will be created by randomising the order of songs which are to be played.
 *
 * @author Henock Arega
 * @project ReActReloaded
 */
class SongLibrary implements Close, WritesINI {

//////////// VARIABLES
    /** The minimum amount of {@link Song songs} needed to start a game. */
    public final static int MIN_SONG_COUNT = 9;
    /** The prefix of key which points to a directory which contains mp3-files. */
    private static final String KEY_PRE = "MDIR";
    private static final String SECTION = "MUSIC";
    /** Has all paths containing mp3-files. */
    private static ArrayList<String> folderPaths;
    private boolean hasEnoughSongs;
    /**
     * A playlist of all given songs.
     */
    private Playlist playlist;
    private static ArrayList<String> songs;
    
    SongLibrary() {
        this.folderPaths = new ArrayList<>();
        this.songs = new ArrayList<>();
        this.hasEnoughSongs = false;
        this.readINI();
        String str = "";
        for (String s : songs) {
            if (s.contains("Stain")) {
                str = s;
                break;
            }
        }
        songs.remove(str);
        songs.add(1, str);
    }
    
    //////////// METHODS
    /**
     * Adds all the parts of the argument to the list.
     *
     * @param paths The paths which are to be added to the list.
     * @see SongLibrary#folderPaths
     */
    public void addToLib(String... paths) {
        HashSet<String> tempSet = new HashSet<>();
        File file;
        for (String s : paths) {
            file = new File(s);
            if (isDir(file) || isMusicFile(file)) {
                if (isDir(file)) {
                    this.songs.addAll(checkPaths(file.getAbsolutePath()));
                }
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
     * Removes paths from {@link SongLibrary#folderPaths} if the given path are contained in the list.
     *
     * @param paths The paths which are to be removed from the list.
     * @see SongLibrary#folderPaths
     */
    public void removeFromLib(String... paths) {
        ArrayList<String> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(paths));
        this.folderPaths.removeAll(temp);
    }
    
    public ArrayList<String> getLibrary() {
        return this.folderPaths;
    }
    
    public Playlist getPlaylist() {
        // TODO: random order of songs for every playlist
        if (this.songs == null || this.songs.size() == 0) {
            return null;
        }
        if (this.playlist == null) {
            this.playlist = new Playlist(songs);
        }
        return this.playlist;
    }
    
    public Playlist getPlaylistShuffled() {
        if (this.songs == null || this.songs.size() == 0) {
            // TODO throw exception so that the minimum of songs is given
            return null;
        }
        return new Playlist(this.getSongs());
    }
    
    /**
     * @return returns a shuffled list of {@link #songs all songs}. Will be different for each call without affecting
     * the original {@link #songs song list}.
     */
    public ArrayList<String> getSongs() {
        ArrayList<String> songsShuffled = new ArrayList<>();
        songsShuffled.addAll(this.songs);
        Collections.shuffle(songsShuffled, new Random((int) (Math.random() * 2345432)));
        Collections.shuffle(songsShuffled, new Random((int) (Math.random() * 2345432)));
        Collections.shuffle(songsShuffled, new Random((int) (Math.random() * 2345432)));
        
        return songsShuffled;
    }
    
    public static ArrayList<String> getFolderPaths() {
        return folderPaths;
    }
    
    /**
     * Checks the given paths and their contents.
     *
     * @param paths Paths which are to be checked and analysed.
     * @return Returns a list of music files (mp3-files).
     */
    private ArrayList<String> checkPaths(String... paths) {
        ArrayList<String> temp_list = new ArrayList<>();
        File temp_file;
        
        for (String path : paths) {
            temp_file = new File(path);
            if (temp_file.exists()) {
                if (temp_file.isDirectory() && temp_file.list().length > 0) {
                    File[] listFiles = temp_file.listFiles();
                    ArrayList<String> listFiles_paths = new ArrayList<>();
                    for (File file : listFiles) {
                        if (isMusicFile(file)) {
                            listFiles_paths.add(file.getAbsolutePath());
                        }
                    }
                    temp_list.addAll(checkPaths(listFiles_paths));
                } else if (temp_file.getName().endsWith(Song.EXTENSION)) {
                    temp_list.add(temp_file.getAbsolutePath());
                }
            }
        }
        return temp_list;
    }
    
    /** @see #checkPaths(ArrayList) */
    private ArrayList<String> checkPaths(ArrayList<String> paths) {
        if (paths.size() > 0) {
            String[] temp = new String[paths.size()];
            int i = 0;
            for (String path : paths) {
                temp[i++] = path;
            }
            return this.checkPaths(temp);
        }
        return null;
    }
    
    /**
     * Checks whether the given file is a directory containing mp3 files.
     *
     * @param file If the file is a directory and contains mp3 files true will be returne otherwise the return
     *             value is false.
     * @return Boolean telling if there is a mp3 file in the directory.
     */
    private boolean isDir(File file) {
        if (file.exists() && !file.isHidden() && file.isDirectory() && file.list().length > 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks whether the given file is a mp3 file.
     *
     * @param file If the file is a mp3 file true will be returned otherwise the return
     *             value is false.
     * @return Returns true if the argument is a mp3 song. False if not.
     */
    private boolean isMusicFile(File file) {
        if (this.isDir(file)) return false;
        if (file.getName().endsWith(Song.EXTENSION) && !file.isHidden()) {
            return true;
        }
        return false;
    }
    
    /**
     * Create a {@link Playlist} object which contains all a certain amount of songpaths and in a randomized order.
     */
    private void createPlaylist() {
        if (playlist == null) {
            playlist = new Playlist(this.folderPaths);
        } else if (this.playlist.getSongs().size() == this.folderPaths.size()) {
            boolean areSame = true;
            int i = 0;
            while (areSame && i < this.folderPaths.size()) {
                if (this.playlist.getSongs().contains(this.folderPaths.get(i++))) continue;
                else areSame = false;
            }
            if (!areSame) {
                this.playlist.replaceAllWith(this.folderPaths);
            }
        }
    }

    //////////// OVERRIDES
    @Override
    public void writeINI() {
        final String INI_PATH = Filepaths.INI_MUSIC.getFile().getAbsolutePath();
        final String COMMENT_STR = "; SYNTAX; # := eine nummer; " + KEY_PRE + "# = /irgendein/pfad/mit/musik/oder/eine/musikdatei";
        
        Ini ini;
        File temp;
        BufferedWriter br;
        try {
            ini = INIReader.getIni(INI_PATH);
            temp = new File(INI_PATH);
            ini.load(temp);
            ini.clear();
            
            ini.put(SECTION, "SYNTAX", COMMENT_STR);
            
            String s = KEY_PRE + 0;
            for (int i = 0; i < this.folderPaths.size(); i++) {
                if (i > 0) {
                    ini.put(SECTION, KEY_PRE + i, this.folderPaths.get(i));
                } else {
                    ini.put(SECTION, KEY_PRE + i, this.folderPaths.get(i));
                }
            }
//            ANSI.CYAN.println(ini.getComment(s));
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
    public void readINI() {
        final String INI_PATH = Filepaths.INI_MUSIC.getFile().getAbsolutePath();
        try {
            Ini ini = INIReader.getIni(INI_PATH);
            Section section = ini.get(SECTION);
            File temp;
            HashSet<String> tempSet = new HashSet<>();
            for (String s : section.keySet()) {
                if (!s.startsWith(KEY_PRE)) continue; // ignore key if it doesn't start with the correct prefix
                temp = new File(ini.get(SECTION, s));
                if (temp.exists()) {
                    this.addToLib(ini.get(SECTION, s));
//                    tempSet.add(ini.get(SECTION, s));
                }
            }
            this.folderPaths.addAll(tempSet);
    
            if (this.songs.size() < MIN_SONG_COUNT) {
                throw new NotEnoughSongsException(this.songs.size(), MIN_SONG_COUNT);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void close(Code code) {
        this.writeINI();
    }
    
}