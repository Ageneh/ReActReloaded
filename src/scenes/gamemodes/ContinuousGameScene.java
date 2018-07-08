package scenes.gamemodes;

import functions.ANSI;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import model.GameMode;
import model.Song;
import model.gamemodes.ContinuousGame;
import model.isGame;

import java.util.Observable;
import java.util.Observer;


public class ContinuousGameScene extends GameScene<ContinuousGame> {
    
    private Label lifeCountLabel;
    
    public ContinuousGameScene(Observer observer) {
        this(null, observer);
    }
    
    public ContinuousGameScene(String name, Observer observer) {
        super( new ContinuousGame(name), observer);
        this.lifeCountLabel = LABEL_STYLE.getLabel(game.getLifeCount());
        top.getChildren().add(this.lifeCountLabel);
    
        getScene().setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.R) game.replay();
        });
    }
    
    @Override
    protected void evalAction(isGame.Action action) {
        switch (action) {
            case POINTS:
                super.addPoints(Integer.parseInt(action.getVal().toString()));
                break;
            case ANSWERS:
                setAnswers((Song[]) action.getVal());
                break;
            case LIFECOUNT:
                this.lifeCountLabel.setText(action.getVal().toString());
                break;
            case NEW_MULTIPLIER:
                super.setMulti(Integer.parseInt(action.getVal().toString()));
                break;
            case ANSWER_CORRECT:
                this.answered.set(false);
                break;
            case ANSWER_INCORRECT:
                this.answered.set(true);
                break;
            case ANSWER:
                break;
        }
    }
    
    @Override
    protected void evalMode(GameMode.Mode mode) {
        switch (mode){
            case NORMAL:
                break;
            case TIMED:
                break;
            case CONTINUOUS:
                break;
            case REACTION:
                break;
            case GAME_OVER:
            case GAME_DONE:
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Congrats.");
                a.setContentText("Congrats.");
                a.showAndWait();
                setChanged();
                notifyObservers(Code.GAME_OVER);
                setChanged();
                notifyObservers(isGame.Action.RANK.setVal(game.getUser()));
                break;
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        ANSI.RED.println("__UPDATE__");
    
        try {
            if (arg instanceof GameMode.Mode) {
                GameMode.Mode mode = (GameMode.Mode) arg;
                this.evalMode(mode);
            }
            if (arg instanceof isGame.Action) {
                isGame.Action action = (isGame.Action) arg;
                this.evalAction(action);
            }
        } catch (NullPointerException e) {
        }
    }
}
