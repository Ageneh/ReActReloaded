package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class used to manage all folders, which are to be used as source folders.
 */
public class SongLibrary {

    private ArrayList<String> folderPaths;

    private SongLibrary(){
        this.folderPaths = new ArrayList<>();
    }

    private ArrayList<String> checkPaths(String ... paths){
        ArrayList<String> temp_list = new ArrayList<>();
        File temp_file;

        for(String path : paths){
            temp_file = new File(path);
            if(temp_file.exists() && temp_file.isDirectory() && temp_file.list().length > 0){
                temp_list.add(temp_file.getAbsolutePath());
            }
        }
        return temp_list;
    }

    private ArrayList<String> checkPaths(ArrayList<String> paths){
        if(paths.size() > 0) {
            String[] temp = new String[paths.size()];
            return this.checkPaths(temp);
        }
        return null;
    }

    public void addToLib(String ... paths){
        this.folderPaths.addAll(checkPaths(paths));
    }

    public void removeFromLib(String ... paths){
        ArrayList<String> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(paths));
        this.folderPaths.removeAll(temp);
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

    public ArrayList<String> getLibrary(){
        return this.folderPaths;
    }

}
