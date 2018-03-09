package model;

import java.nio.file.InvalidPathException;

/**
 * An exception which will be thrown whenever a {@link Song songpath} is given
 * which is invalid.
 */
class SongInvalidException extends InvalidPathException {

    SongInvalidException(String path, String msg){
        super(path, msg);
    }

    SongInvalidException(String path){
        this(path, "Der geforderte Song existiert nicht.");
    }

}
