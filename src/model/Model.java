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
 * To create, start, control a game an instance of {@link Model} must be used.
 */
public class Model {
    
    /** The current {@link Game} of an user. */
    private Game game;
    
    /** The user who plays this {@link model.Model#game}. */
    
    public Model(Game.GameMode mode, String username) {
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
