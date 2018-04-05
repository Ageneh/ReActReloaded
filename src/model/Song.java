package model;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * A class which will contain all the necessary information about a mp3-file
 * for the gameplay.
 * <br>Additionally the{@link MetaExtractor} is used to extract the necessary from a
 * mp3-file.
 * @see MetaExtractor
 *
 * @author Henock Arega
 * @project ReActReloaded
 */
public class Song {
    
    /**
     * The accepted file extension.
     */
    public static final String EXTENSION = "mp3";
    /**
     * The path of the standard cover image
     * @see Song#coverImg
     */
    public static final String COVER_IMG = "img/std_cover.jpg";
    
    /**
     * An array containing the title, album and artist of the {@link Song}.
     */
    private String[] meta;
    private MetaExtractor metaExtractor;
    private long length;
    /**
     * A {@link java.lang.reflect.Array byte array} of the cover image.
     */
    private byte[] coverImg;
    private String path;
    private String absPath;
    
    Song(String path) throws SongInvalidException {
        try {
            this.path = path;
            File temp = checkSong(path);
            this.absPath = temp.getAbsolutePath();
            try {
                if (absPath.contains("12 Stainless (feat. Dria)")) {
                    System.out.println("STOP");
                }
                this.metaExtractor = new MetaExtractor(temp);
                this.meta = new String[MetaPos.values().length];
                this.meta[MetaPos.TITLE.val] = this.metaExtractor.extractTitle();
                this.meta[MetaPos.ARTIST.val] = this.metaExtractor.extractArtist();
                this.meta[MetaPos.ALBUM.val] = this.metaExtractor.extractAlbum();
                this.length = this.metaExtractor.extractLength();
                this.coverImg = this.metaExtractor.extractCoverImg();
            } catch (IOException ignored) {
//                e.printStackTrace();
            } catch (UnsupportedTagException ignored) {
//                e.printStackTrace();
            } catch (InvalidDataException ignored) {
//                e.printStackTrace();
            }
        } catch (SongInvalidException e) {
            throw e;
        }
    }
    
    /**
     * @return Returns the title of the {@link Song}.
     * @author Henock Arega
     */
    public String getTitle() {
        return this.meta[MetaPos.TITLE.val];
    }
    
    /**
     * @return Returns the album title of the {@link Song}.
     * @author Henock Arega
     */
    public String getAlbum() {
        return this.meta[MetaPos.ALBUM.val];
    }
    
    /**
     * @return Returns the artist name of the {@link Song}.
     * @author Henock Arega
     */
    public String getArtist() {
        return this.meta[MetaPos.ARTIST.val];
    }
    
    public long lengthMillis() {
        return this.length;
    }
    
    public String getPath() {
        return path;
    }
    
    public String getAbsPath() {
        return absPath;
    }

//////////// METHODS
    /**
     * @param path The path which is to be checked.
     * Checks the given path and if the given path directs to anything but a valid mp3-song a
     * {@link SongInvalidException} will be thrown.
     * @return Returns a File if the argument links to a mp3 song but throws a {@link SongInvalidException} if the given
     * argument links to anything else.
     *
     * @author Henock Arega
     */
    private File checkSong(String path) {
        final File temp = new File(path);
        
        if (!temp.exists() || temp.isDirectory() || !temp.getName().endsWith(EXTENSION)
                || temp.length() <= 750) {
            throw new SongInvalidException(path);
        }
        return temp;
    }
    
    private enum MetaPos {
        
        TITLE(0), ALBUM(2), ARTIST(1);
        
        private int val;
        
        MetaPos(int val) {
            this.val = val;
        }
        
    }
    
    /**
     * A class used to extract necessary information/meta-data from a given mp3-file.
     *
     * @author Henock Arega
     */
    private class MetaExtractor {
        
        /**
         * @author Henock Arega
         * A {@link File} object used as a "backup" for missing information such as the
         * {@link Song#meta title} of a {@link Song}.
         */
        private File file;
        private ID3v2 id;
        
        private MetaExtractor(File file) throws InvalidDataException, IOException, UnsupportedTagException {
            this.file = file;
            this.id = new Mp3File(file.getAbsolutePath()).getId3v2Tag();
        }
        
        private String extractTitle() {
            try {
                return this.id.getTitle();
            } catch (NullPointerException e) {
                return this.file.getName().replace("." + Song.EXTENSION, "");
            }

//            if(this.id.getTitle().equals(null) || this.id.getTitle() == null || this.id.getTitle().equals("")){
//                return file.getName().replace(Song.EXTENSION, "");
//            }
//            return this.id.getTitle();
        }
        
        private String extractArtist() {
            try {
                return this.id.getArtist();
            } catch (NullPointerException e) {
                return "Artist Unknown";
            }

//            if(this.id.getArtist().equals(null) || this.id.getArtist() == null || this.id.getArtist().equals("")){
//                return "Artist Unknown";
//            }
//            return this.id.getArtist();
        }
        
        private String extractAlbum() {
            try {
                return this.id.getAlbum();
            } catch (NullPointerException e) {
                return "Album Unknown";
            }

//            if(this.id.getAlbum().equals(null) || this.id.getAlbum() == null || this.id.getAlbum().equals("")){
//                return "Album Unknown";
//            }
//            return this.id.getAlbum();
        }
        
        private long extractLength() {
            try {
                Mp3File mp3File = new Mp3File(this.file);
                return mp3File.getLengthInMilliseconds();
            } catch (IOException | InvalidDataException | UnsupportedTagException e) {
                e.printStackTrace();
            }
            return id.getLength();
        }
        
        private byte[] extractCoverImg() {
            if (this.id.getAlbumImage() != null) {
                return this.id.getAlbumImage();
            }
            
            byte[] arr = null;
            try {
                File img_temp = new File(Song.COVER_IMG);
                arr = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            return arr;
        }
        
    }
    
}
