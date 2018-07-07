package design;

import java.io.File;
import java.io.IOException;

public enum IconPath {
    
    PLAY("icons/icon_play.png"),
    REPEAT("icons/icon_repeat.png"),
    REWIND("icons/icon_rewind.png"),
    SETTINGS("icons/icon_settings.png");
    
    private File path;
    
    IconPath(String path) {
        this.path = new File(path);
    }
    
    public String getPath() {
        try {
            return path.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path.getPath();
    }
    
}
