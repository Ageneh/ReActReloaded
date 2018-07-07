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
    
    public NormalGameScene(Observer... observers) {
        this(null, observers);
    }
    
    public NormalGameScene(String name, Observer... observers) {
        super("scenes/fxml/continuousgame_scene.fxml", new NormalGame(name), observers);
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
    
    public void ready() {
        this.setAnswers(game.getAnswers());
    }
    
    public void setUser(String username) {
        this.game.setUser(username);
    }
    
    private void setAnswers(Song[] songs) {
        
        ArrayList<AnswerButton> btns = new ArrayList<>();
        System.out.println(songs.length);
        int i = 0;
        for (Song song : songs) {
            AnswerButton a = buttonsList.get(i++);
            a.setText(song.getTitle());
            a.setSong(song);
            a.setOnAction(event -> {
                if (this.started.get()) {
                    boolean correct = game.answer(song);
                    a.setColor(correct);
                    if (! correct) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Game Over");
                        alert.setContentText("You reached " + game.getPoints() + " points.");
                        alert.showAndWait();
                    }
                }
            });
            btns.add(a);
        }
        this.buttons.getChildren().setAll(btns);
    }
    
    private void setAnswers(ArrayList<Song> songs) {
        Song[] s = new Song[songs.size()];
        for (int i = 0; i < songs.size(); i++) {
            s[i] = songs.get(i);
        }
        setAnswers(s);
    }
    
    private void setFadeOut(Duration duration, Node node) {
        node.setOpacity(1);
        pauseTransition = new PauseTransition(Duration.seconds(1));
        pauseTransition.setOnFinished(event -> {
            fadeTransition = new FadeTransition(duration, node);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(true);
            fadeTransition.setToValue(0);
            fadeTransition.setByValue(0.1);
            fadeTransition.setFromValue(node.getOpacity());
            fadeTransition.play();
        });
    }
    
    @Override
    public void close(Code code) {
        game.close(code);
    }
    
    @Override
    public void start() {
        super.game.start();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), this.buttons);
        fadeTransition.setByValue(0.1);
        fadeTransition.setFromValue(this.buttons.getOpacity());
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }
    
    //////////// OVERRIDES
    @Override
    public void update(Observable o, Object arg) {
        ANSI.RED.println("__UPDATE__");
        ANSI.YELLOW.println(o);
        try {
            ANSI.GREEN.println(arg);
        } catch (NullPointerException e) {
        }
    
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
                        break;
                    case GAME_DONE:
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("You have guessed all your songs correctly. Congrats.");
                        a.showAndWait();
                        break;
                }
                return;
            }
            if (arg instanceof PlayerResult) {
                System.out.println("Music changed something");
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
                    case NEW_MULTIPLIER:
                        break;
                    case ANSWER_CORRECT:
                        this.answered.set(false);
                        break;
                    case ANSWER_INCORRECT:
                        // todo
                        this.answered.set(true);
                        break;
                    case ANSWER:
                        break;
                }
            }
        } catch (NullPointerException e) {
        }
        
    }
    
}
