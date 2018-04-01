package model;

///////////////
///////////////
///////////////
///////////////
// MIGHT NOT BE USEFUL
///////////////
///////////////
///////////////
///////////////

/**
 * @author Henock Arega
 * @project ReActReloaded
 * <p>
 * The main model. <br>
 * To create, start, control a gameMode an instance of {@link Model} must be used.
 */
public class Model {
    
    /** The current {@link GameMode} of an user. */
    private GameMode gameMode;
    
    /** The user who plays this {@link model.Model#gameMode}. */

    public Model(GameMode.Mode mode, String username) {
        switch (mode) {
            case TIMED:
                break;
            case NORMAL:
                
                break;
            case CONTINUOUS:
                break;
        }
    }
    
}
