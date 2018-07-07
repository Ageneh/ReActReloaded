package scenes.gamemodes;

import functions.ANSI;
import functions.ElementBackgroundCreator;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import model.GameMode;
import model.PlayerResult;
import model.Song;
import model.gamemodes.NormalGame;
import model.isGame;
import scenes.elements.AnswerButton;
import scenes.elements.BackButton;
import scenes.elements.ReButton;

import java.util.ArrayList;
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
        super("scenes/fxml/continuousgame_scene.fxml", new NormalGame(name), observer);
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
    public void close(Code code) {
        game.close(code);
    }
    
    //////////// OVERRIDES
    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
    }
    
}
