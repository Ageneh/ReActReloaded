package model;

/**
 * @author Henock Arega
 * @author Michael Heide
 *
 * @project ReActReloaded
 */
public class NotEnoughSongsException extends RuntimeException {
    
    public NotEnoughSongsException(int count, int minCount) {
        super("There are too few Songs. There have to be at least " + minCount + " Songs to play the game.\n"
                + "There are " + count + " of " + minCount + " needed songs.");
    }
    
}
