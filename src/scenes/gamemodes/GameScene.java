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
import model.Close;
import model.GameMode;
import model.User;
import model.isGame;
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
public abstract class GameScene extends ObservableScene implements Observer, Close {
    
    private GameBackground background;
    private Label points;
    private Label username;
    private Label multi;
    private Label round;
    private User user;
    private HBox top;
    private String fxmlPath;
    protected HBox buttons;
    protected ObservableList<AnswerButton> buttonsList;
    protected ReButton start;
    protected SimpleBooleanProperty started;
    protected SimpleBooleanProperty answered;
    protected PauseTransition pauseTransition;
    protected FadeTransition fadeTransition;
    protected GameMode game;
    
    GameScene(String fxmlPath, GameMode game, Observer... observers) {
        super();
        this.game = game;
        this.fxmlPath = fxmlPath;
        this.background = new GameBackground();
        addAllObserver(observers);
        
        Labels label = Labels.M_SMALL;
        this.points = label.getLabel(0);
        this.username = label.getLabel("Player");
        this.multi = label.getLabel("1x");
        this.round = label.getLabel("1");
    
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
    
    protected void setStartBG() {
        if (! this.started.get() && ! this.answered.get()) {
            start.setBackground(ElementBackgroundCreator.createBackgroundImg("res/icons/icon_play.png"));
        } else {
            start.setBackground(ElementBackgroundCreator.createBackgroundImg("res/icons/icon_repeat.png"));
        }
    }
    
    private void init(){
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
    
    public void start(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), this.buttons);
        fadeTransition.setByValue(0.1);
        fadeTransition.setFromValue(this.buttons.getOpacity());
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }
    
    public BorderPane getBackground() {
        return background.root;
    }
    
    public String getFxmlPath() {
        return fxmlPath;
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
