package functions;

import javafx.scene.image.Image;

import java.io.File;

/**
 * This small class is used to create an {@link Image} after checking whether the wanted
 * image or path does exist and is valid.
 * Makes it easier when trying to create an {@link Image}.
 */
public abstract class ImageCreator {
    
    public static Image getImage(String path) {
        File f = new File(path);
        if (! f.exists()) {
            return null;
        }
        return new Image(f.toURI().toString());
    }
    
}
