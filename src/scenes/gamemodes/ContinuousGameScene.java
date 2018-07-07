package scenes.gamemodes;

import functions.ANSI;
import functions.ElementBackgroundCreator;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import model.GameMode;
import model.Song;
import model.gamemodes.ContinuousGame;
import model.isGame;
import scenes.elements.AnswerButton;
import scenes.elements.BackButton;
import scenes.elements.ReButton;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class ContinuousGameScene extends GameScene<ContinuousGame> {
    
    private Label lifeCountLabel;
    
    public ContinuousGameScene(Observer observer) {
        this(null, observer);
    }
    
    public ContinuousGameScene(String name, Observer observer) {
        super("scenes/fxml/continuousgame_scene.fxml", new ContinuousGame(name), observer);
        this.lifeCountLabel = LABEL_STYLE.getLabel(game.getLifeCount());
        top.getChildren().add(this.lifeCountLabel);
    }
    
    @Override
    public void close(Code code) {
        game.close(code);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        ANSI.RED.println("__UPDATE__");
    
        try {
            if (arg instanceof GameMode.Mode) {
                GameMode.Mode mode = (GameMode.Mode) arg;
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
                        a.setContentText("");
                        a.showAndWait();
                        break;
                }
                return;
            }
            if (arg instanceof isGame.Action) {
                isGame.Action action = (isGame.Action) arg;
                switch (action) {
                    case POINTS:
                        super.update(o, arg);
                        break;
                    case ANSWERS:
                        setAnswers((Song[]) action.getVal());
                        break;
                    case LIFECOUNT:
                        this.lifeCountLabel.setText(action.getVal().toString());
                        break;
                    case NEW_MULTIPLIER:
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
        } catch (NullPointerException e) {
        }
    
        if (arg instanceof isGame.Action) {
            isGame.Action action = (isGame.Action) arg;
            switch (action) {
                case POINTS:
                    super.points.setText(action.getVal().toString());
                    break;
                case NEW_MULTIPLIER:
                    break;
                case ANSWER:
                    break;
            }
        }
    }
}
