package scenes.gamemodes;

import functions.ANSI;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import model.GameMode;
import model.Song;
import model.User;
import model.gamemodes.CoOpGame;
import model.isGame;
import scenes.elements.UserBox;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 */
public class CoOpGameScene extends GameScene<CoOpGame> {
    
    private HashMap<KeyCode, String> keyUserRelation;
    private boolean userIsActive;
    
    public CoOpGameScene(String user1, String user2, Observer o) {
        this(user1, KeyCode.A, user2, KeyCode.K, o);
    }
    
    public CoOpGameScene(String user1, KeyCode kc1, String user2, KeyCode kc2, Observer o) {
        super(new CoOpGame(user1, user2), o);
        this.keyUserRelation = new HashMap<>();
        this.keyUserRelation.put(kc1, user1);
        this.keyUserRelation.put(kc2, user2);
        this.init();
    }
    
    private void init(){
        getScene().setOnKeyPressed(event -> {
            String user = this.keyUserRelation.getOrDefault(event.getCode(), null);
            if(user == null) return;
            game.pause();
            game.setActiveUser(user);
            userIsActive = true;
            fadeInNode(super.buttons);
        });
    }
    
    public void start() {
        this.game.start();
    }
    
    @Override
    protected void evalAction(isGame.Action action) {
        switch (action){
            case CURRENT_SONG:
                break;
            case ANSWERS:
                this.setAnswers((Song[]) action.getVal());
                break;
            case NEW_MULTIPLIER:
                break;
            case ANSWER:
                break;
            case LIFECOUNT:
                break;
            case ACTIVE_USER:
                break;
            case RANK:
                break;
            case POINTS:
                User user = (User) action.getVal();
                for(UserBox ub : userBoxes){
                    if(ub.getUser().getName().equals(user.getName())){
                        ub.setPoints(user.getPoints());
                        break;
                    }
                }
    
                fadeOutNode(buttons);
                break;
            case REPLAY:
                break;
        }
    }
    
    @Override
    protected void evalMode(GameMode.Mode mode) {
        switch (mode){
            case GAME_OVER:
                
                break;
            case GAME_DONE:
                break;
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        ANSI.RED.println("__UPDATE__");
    
        try {
            if (arg instanceof GameMode.Mode) {
                GameMode.Mode mode = (GameMode.Mode) arg;
                this.evalMode(mode);
            }
            else if (arg instanceof isGame.Action) {
                isGame.Action action = (isGame.Action) arg;
                this.evalAction(action);
            }
        } catch (NullPointerException e) {
        }
    }
}
