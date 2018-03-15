package model;

import java.io.File;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public enum Filepaths {
    
    INI_MUSICPLAYER("res/init/musicplayer.ini"),
    INI_MUSIC("res/init/music.ini"),
    
    IMG_COVER("res/init/musicplayer.ini"),
    
    ICO_PLAY("res/icons/icon_play.png"),
    ICO_REPEAT("res/icons/icon_repeat.png"),
    ICO_REWIND("res/icons/icon_rewind.png"),
    ICO_SETTINGS("res/icons/icon_settings.png")
    ;
    
    private File file;
    
    private Filepaths(String path){
        this.file = new File(path);
    }
    
    public File getFile() {
        return file;
    }
    
}
