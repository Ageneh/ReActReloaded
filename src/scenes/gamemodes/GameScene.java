package scenes.gamemodes;

import design.Colors;
import design.Labels;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.*;
import scenes.ObservableScene;
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
public abstract class GameScene<T extends GameMode> extends ObservableScene implements Observer, Close {
    
    protected final Labels LABEL_STYLE = Labels.M_SMALL;
    protected final T game;
    protected Label points;
    protected Label username;
    protected Label multi;
    protected Label round;
    protected HBox buttons;
    protected ObservableList<AnswerButton> buttonsList;
    protected ReButton start;
    protected SimpleBooleanProperty started;
    protected SimpleBooleanProperty answered;
    protected PauseTransition pauseTransition;
    protected FadeTransition fadeTransition;
    private GameBackground background;
    private User user;
    protected HBox top;
    private String fxmlPath;
    
    GameScene(String fxmlPath, T game, Observer o) {
        super();
        this.game = game;
        this.game.addObserver(o);
        this.game.addObserver(this);
        addObserver(o);
        this.fxmlPath = fxmlPath;
        this.background = new GameBackground();
        
        this.points = LABEL_STYLE.getLabel(0);
        this.username = LABEL_STYLE.getLabel("Player");
        this.multi = LABEL_STYLE.getLabel("1x");
        this.round = LABEL_STYLE.getLabel("1");
        
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
        
        this.top = new HBox();
        top.getChildren().addAll(username, points, multi, round);
        top.setSpacing(20);
        top.setAlignment(Pos.CENTER);
        this.background.root.setTop(this.top);
        getRoot().getChildren().add(this.background.root);
    }
    
    public BorderPane getBackground() {
        return background.root;
    }
    
    public String getFxmlPath() {
        return fxmlPath;
    }
    
    public void ready() {
        this.setAnswers(game.getAnswers());
    }
    
    public void setUser(String username) {
        this.game.setUser(username);
    }
    
    public void start() {
        this.game.start();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), this.buttons);
        fadeTransition.setByValue(0.1);
        fadeTransition.setFromValue(this.buttons.getOpacity());
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }
    
    protected void addLayer(Node node) {
        this.addLayers(node);
    }
    
    protected void addLayers(Node... node) {
        if (node == null) return;
        for (Node n : node) {
            if (n == null) continue;
            this.background.bottom.getChildren().add(n);
        }
    }
    
    protected void setAnswers(Song[] songs) {
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
                    setChanged();
                    if (correct) {
                        notifyObservers(isGame.Action.ANSWER_CORRECT);
                    } else {
                        notifyObservers(isGame.Action.ANSWER_INCORRECT);
                    }
                }
            });
            btns.add(a);
        }
        this.buttons.getChildren().setAll(btns);
    }
    
    protected void setStartBG() {
        if (! this.started.get() && ! this.answered.get()) {
            start.setBackground(ElementBackgroundCreator.createBackgroundImg("res/icons/icon_play.png"));
        } else {
            start.setBackground(ElementBackgroundCreator.createBackgroundImg("res/icons/icon_repeat.png"));
        }
    }
    
    private void init() {
        this.buttonsList = FXCollections.observableList(new ArrayList<>());
        buttons = new HBox();
        buttons.getChildren().setAll(this.buttonsList);
        this.buttons.setOpacity(0);
        this.buttonsList.addListener((ListChangeListener<AnswerButton>) c -> {
            this.buttons.getChildren().setAll(this.buttonsList);
        });
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(40);
        buttons.setPrefHeight(80);
        
        BorderPane root = new BorderPane();
        
        this.start = new ReButton("");
        this.start.setBackground(ElementBackgroundCreator.createBackgroundImg("res/icons/icon_play.png"));
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
        
        
        for (int i = 0; i < game.MAX_ANSWERCOUNT; i++) {
            buttonsList.add(new AnswerButton());
        }
        root.setBottom(buttons);
        
        this.background.root.setLeft(new BackButton("Zurück"));
        addLayer(root);
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
    
    private void setUser(User user) {
        if (user != null) return;
        
        this.user = user;
        
        this.points.setText("0");
        this.username.setText(this.user.getName());
        this.multi.setText("1x");
        this.round.setText("1");
    }
    
    @Override
    public void update(Observable o, Object arg) {
        ANSI.RED.println("__UPDATE__");
        
        try {
            if (arg instanceof GameMode.Mode) {
                GameMode.Mode mode = (GameMode.Mode) arg;
                switch (mode) {
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
                        this.answered.set(true);
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Game Over");
                        alert.setContentText("You reached " + game.getPoints() + " points.");
                        alert.showAndWait();
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
                    this.points.setText(action.getVal().toString());
                    break;
                case NEW_MULTIPLIER:
                    break;
                case ANSWER:
                    break;
            }
        }
    }
    
    private class GameBackground {
        
        private BorderPane root;
        private StackPane bottom;
        private Scene scene;
        
        private GameBackground() {
            this.bottom = new StackPane();
            this.root = new BorderPane();
            this.root.setCenter(bottom);
            this.root.setBackground(ElementBackgroundCreator.getBackground(Colors.BASE_BG));
            this.scene = new Scene(this.root);
        }
        
    }
}
