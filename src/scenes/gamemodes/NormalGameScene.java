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
    
    private NormalGame game;
    private AnswerButton answer1, answer2, answer3;
    private Label currentTitle;
    private ObservableList<AnswerButton> buttonsList;
    private HBox buttons;
    private ReButton start;
    private SimpleBooleanProperty started;
    private SimpleBooleanProperty answered;
    private PauseTransition pauseTransition;
    private FadeTransition fadeTransition;
    
    
    public NormalGameScene(Observer... observers) {
        this("Normalgame", observers);
    }
    
    public NormalGameScene(String name, Observer... observers) {
        super(observers);
        this.game = new NormalGame(name, this);
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
        this.init();
    }
    
    public NormalGame getGame() {
        return game;
    }
    
    public void ready() {
        this.setAnswers(game.getAnswers());
    }
    
    public void setUser(String username) {
        this.game.setUser(username);
    }
    
    private void init() {
        BorderPane root = new BorderPane();
        
        this.start = new ReButton("");
        this.start.setBackground(ElementBackgroundCreator.createBackgroundImg("/Users/HxA/Pictures/Icons/149629-essential-compilation/png/play-button.png"));
        root.setCenter(start);
        start.setPrefSize(40, 40);
        start.setCursor(Cursor.HAND);
        this.setStartBG();
        start.setOnAction(event -> {
            if (! this.started.get() && ! answered.get()) {
                this.start();
                this.answered.set(false);
                this.started.set(true);
            } else {
                if (answered.get()) {
                    this.game.next();
                } else {
                    game.replay();
                }
            }
        });
        
        this.buttonsList = FXCollections.observableList(new ArrayList<>());
        for (int i = 0; i < game.MAX_ANSWERCOUNT; i++) {
            this.buttonsList.add(new AnswerButton());
        }
        buttons = new HBox();
        buttons.getChildren().setAll(this.buttonsList);
        this.buttons.setOpacity(0);
        this.buttonsList.addListener((ListChangeListener<AnswerButton>) c -> {
            this.buttons.getChildren().setAll(this.buttonsList);
        });
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(40);
        buttons.setPrefHeight(80);
        root.setBottom(buttons);
        
        super.getBackground().setLeft(new BackButton("Zurück"));
        
        super.addLayer(root);
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
    
    private void setStartBG() {
        if (! this.started.get() && ! this.answered.get()) {
            start.setBackground(ElementBackgroundCreator.createBackgroundImg("/Users/HxA/Pictures/Icons/149629-essential-compilation/png/play-button-1.png"));
        } else {
            start.setBackground(ElementBackgroundCreator.createBackgroundImg("/Users/HxA/Pictures/Icons/149629-essential-compilation/png/restart.png"));
        }
    }
    
    @Override
    public void close(Code code) {
        game.close(code);
    }
    
    @Override
    public void start() {
        this.game.start();
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
                        currentTitle.setText("Correct Answer");
                        setFadeOut(Duration.millis(300), currentTitle);
                        pauseTransition.play();
                        this.setAnswers(this.game.getAnswers());
                        this.answered.set(false);
                        break;
                    case ANSWER_INCORRECT:
                        // todo
                        currentTitle.setText("Correct");
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
