package scenes.gamemodes;

import design.Colors;
import design.Labels;
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
import scenes.elements.UserBox;

import java.util.ArrayList;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public abstract class GameScene<T extends GameMode> extends ObservableScene implements Observer, Close {
    
    public static final Labels LABEL_STYLE = Labels.M_SMALL;
    protected final T game;
    protected ArrayList<UserBox> userBoxes;
    protected Label multi;
    protected Label round;
    protected HBox buttons;
    protected ObservableList<AnswerButton> buttonsList;
    protected ReButton start;
    protected SimpleBooleanProperty started;
    protected SimpleBooleanProperty answered;
    protected PauseTransition pauseTransition;
    protected FadeTransition fadeTransition;
    protected HBox top;
    private GameBackground background;
    private String fxmlPath;
    
    GameScene(T game, Observer o) {
        super();
        this.game = game;
        this.game.addObserver(o);
        this.game.addObserver(this);
        addObserver(o);
        this.fxmlPath = fxmlPath;
        this.background = new GameBackground();
        
        this.userBoxes = new ArrayList<>();
        for(User user : game.getUsers()){
            this.userBoxes.add(new UserBox(user));
        }
        
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
        for(UserBox userBox : userBoxes){
            top.getChildren().add(userBox);
        }
        top.getChildren().addAll(multi, round);
        top.setSpacing(20);
        top.setAlignment(Pos.CENTER);
        this.background.root.setTop(this.top);
        getRoot().getChildren().add(this.background.root);
    }
    
    protected abstract void evalAction(isGame.Action action);
    
    protected abstract void evalMode(GameMode.Mode mode);
    
    public BorderPane getBackground() {
        return background.root;
    }
    
    public String getFxmlPath() {
        return fxmlPath;
    }
    
    public void ready() {
        this.setAnswers(game.getAnswers());
    }
    
    public void setMulti(int multi) {
        this.multi.setText(String.valueOf(multi));
    }
    
    public void setPoints(int points) {
        User user = this.game.getUser();
        for(UserBox userBox : userBoxes){
            if(userBox.getUser().getName().equals(user.getName())){
                userBox.setPoints(points);
            }
        }
    }
    
    public void setUser(String username) {
        this.game.setUsers(username);
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
        
        for (int i = 0; i < game.maxAnswercount; i++) {
            buttonsList.add(new AnswerButton());
        }
        root.setBottom(buttons);
        
        this.background.root.setLeft(new BackButton("Zurück"));
        addLayer(root);
    }
    
    protected void fadeInNode(Node node){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), node);
        fadeTransition.setByValue(0.1);
        fadeTransition.setFromValue(node.getOpacity());
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }
    
    protected void fadeOutNode(Node node){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), node);
        fadeTransition.setByValue(0.1);
        fadeTransition.setFromValue(node.getOpacity());
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
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
        this.game.close(code);
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
