package scenes.gamemodes;

import functions.ElementBackgroundCreator;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Cursor;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import model.gamemodes.ContinuousGame;
import scenes.elements.AnswerButton;
import scenes.elements.BackButton;
import scenes.elements.ReButton;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class ContinuousGameScene extends GameScene {
    
    public ContinuousGameScene(Observer observer) {
        this(null, observer);
    }
    
    public ContinuousGameScene(String name, Observer observer) {
        super("scenes/fxml/continuousgame_scene.fxml", new ContinuousGame(name), observer);
    }
    
    @Override
    public void close(Code code) {
        game.close(code);
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
}
