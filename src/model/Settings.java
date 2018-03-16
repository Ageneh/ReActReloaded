package model;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class Settings {
    
    private static SongLibrary songLibrary;
    
    public Settings() {
        songLibrary = new SongLibrary();
    }
    
    /** @see SongLibrary#addToLib(String...) */
    public void addToLib(String path, String... paths) {
        if (paths != null && paths.length > 0) {
            String[] temp = new String[paths.length + 1];
            temp[0] = path;
            for (int i = 0; i < paths.length; i++) {
                temp[i + 1] = paths[i];
            }
        } else {
            paths = new String[]{path};
        }
        songLibrary.addToLib(paths);
    }
    
}
