package scenes.gamemodes;

import functions.ANSI;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import model.GameMode;
import model.Song;
import model.User;
import model.gamemodes.CoOpGame;
import model.isGame;
import scenes.GameModeScene;
import scenes.GameOverScene;
import scenes.ObservableScene;
import scenes.elements.UserBox;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @author Michael Heide
 */
public class CoOpGameScene extends GameScene<CoOpGame> {
    
    private HashMap<KeyCode, String> keyUserRelation;
    private SimpleBooleanProperty userIsActive;
    private String getsPoints;
    
    public CoOpGameScene(Observer o) {
        this(null, KeyCode.A, null, KeyCode.K, o);
    }
    
    public CoOpGameScene(ObservableScene o) {
        this(null, KeyCode.A, null, KeyCode.K, o);
    }
    
    public CoOpGameScene(String user1, KeyCode kc1, String user2, KeyCode kc2, Observer o) {
        super(new CoOpGame(user1, user2), o);
        this.init(kc1, kc2);
    }
    
    public CoOpGameScene(String user1, KeyCode kc1, String user2, KeyCode kc2, ObservableScene o) {
        super(new CoOpGame(user1, user2), o);
        this.init(kc1, kc2);
    }
    
    private void init(KeyCode kc1, KeyCode kc2){
        this.keyUserRelation = new HashMap<>();
        this.keyUserRelation.put(kc1, game.getUsers().get(0).getName());
        this.keyUserRelation.put(kc2, game.getUsers().get(1).getName());
        this.userIsActive = new SimpleBooleanProperty(false);
        this.userIsActive.addListener((observable, oldValue, newValue) -> {
            start.setDisable(newValue);
            if(newValue){
                fadeOutNode(start);
            }
            else{
                fadeInNode(start);
            }
        });
        
        getScene().setOnKeyPressed(event -> {
            String user = this.keyUserRelation.getOrDefault(event.getCode(), null);
            if(user == null) return;
            game.pause();
            game.setActiveUser(user);
            userIsActive.set(true);
            fadeInNode(super.buttons);
        });
    }
    
    public void start() {
        this.game.start();
    }
    
    public KeyCode getKeyCodeOf(String username){
        for(KeyCode k : this.keyUserRelation.keySet()){
            if (this.keyUserRelation.get(k).equals(username)){
                return k;
            }
        }
        return null;
    }
    
    @Override
    protected void evalAction(isGame.Action action) {
        switch (action){
            case SHOW_HOME:
            case SHOW_RANKING:
                setChanged();
                notifyObservers(action);
                break;
            case CURRENT_SONG:
                break;
            case ANSWERS:
                this.setAnswers((Song[]) action.getVal());
                break;
            case NEW_MULTIPLIER:
                break;
            case ANSWER_CORRECT:
                getsPoints = ((User) action.getVal()).getName();
                userIsActive.set(false);
                fadeOutNode(buttons);
                break;
            case ANSWER_INCORRECT:
                String not = ((User) action.getVal()).getName();
                for(String uname : keyUserRelation.values()){
                    if(!uname.equals(not)){
                        getsPoints = uname;
                        break;
                    }
                }
                userIsActive.set(false);
                fadeOutNode(buttons);
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
                for(UserBox ub : userBoxes){
                    if(ub.getName().equals(getsPoints)){
                        ub.addPoints((Integer) (action.getVal()));
                        break;
                    }
                }
                break;
            case REPLAY:
                break;
        }
    }
    
    @Override
    protected void evalMode(GameMode.Mode mode) {
        switch (mode){
            case GAME_OVER:
            case GAME_DONE:
                setChanged();
                notifyObservers(new GameOverScene(this));
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
