package scenes.gamemodes;

import functions.ANSI;
import functions.ElementBackgroundCreator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.GameMode;
import model.Song;
import model.gamemodes.NormalGame;
import model.isGame;
import scenes.StartScene;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class NormalGameScene extends GameScene {
    
    public NormalGameScene(Observer observer) {
        this(null, observer);
    }
    
    public NormalGameScene(String name, Observer observer) {
        super(new NormalGame(name), observer);
//        this.game = new NormalGame(name, this);
        this.started = new SimpleBooleanProperty(false);
        this.started.addListener(((observable, oldValue, newValue) -> {
            if (oldValue) this.started.set(oldValue);
            else {
                this.setStartBG();
            }
        }));
        
        this.answered = new SimpleBooleanProperty(false);
        this.answered.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.start.setBackground(ElementBackgroundCreator.createBackgroundImg("/Users/HxA/Pictures/Icons/149629-essential-compilation/png/next.png"));
            } else {
                this.setStartBG();
            }
        });
    }
    
    @Override
    protected void evalAction(isGame.Action action) {
        switch (action) {
            case POINTS:
                super.setPoints(Integer.parseInt(action.getVal().toString()));
                break;
            case ANSWERS:
                setAnswers((Song[]) action.getVal());
                break;
            case NEW_MULTIPLIER:
                break;
            case ANSWER_CORRECT:
                this.answered.set(false);
                break;
            case ANSWER_INCORRECT:
                this.answered.set(true);
                game.endGame(false);
                break;
            case ANSWER:
                break;
        }
    }
    
    @Override
    protected void evalMode(GameMode.Mode mode) {
        switch (mode) {
            case GAME_OVER:
                setChanged();
                notifyObservers(isGame.Action.RANK.setVal(game.getUser()));
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Game Over");
//                alert.setContentText("You reached " + game.getPoints() + " points.");
//                alert.showAndWait();
                setChanged();
                notifyObservers(isGame.Action.RANK.setVal(game.getUser()));
                setChanged();
                notifyObservers(this);
                break;
            case GAME_DONE:
//                Alert a = new Alert(Alert.AlertType.INFORMATION);
//                a.setTitle("Congrats!");
//                a.setContentText("You have guessed all your songs correctly. Congrats.");
//                a.showAndWait();
                setChanged();
                notifyObservers(isGame.Action.RANK.setVal(game.getUser()));
                setChanged();
                notifyObservers(this);
                break;
        }
    }
    
    //////////// OVERRIDES
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
