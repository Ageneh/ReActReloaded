package scenes.gamemodes;

import design.Colors;
import design.Labels;
import functions.ElementBackgroundCreator;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.*;
import scenes.ObservableScene;
import scenes.elements.*;

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
    protected VBox buttons;
    protected ObservableList<AnswerButton> buttonsList;
    protected ReButton start;
    protected SimpleBooleanProperty started;
    protected SimpleBooleanProperty answered;

    protected SimpleStringProperty points;
    protected SimpleIntegerProperty pointValue;
    protected Label pointLabel;
    protected Label nameLabel;

    protected SimpleStringProperty multiProp;
    protected SimpleIntegerProperty multiValue;

    protected SimpleStringProperty roundProp;
    protected SimpleIntegerProperty roundValue;

    protected PauseTransition pauseTransition;
    protected FadeTransition fadeTransition;
    protected HBox top;
    private GameBackground background;
    private String fxmlPath;
    protected GridPane gp;
    
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
            this.userBoxes.add(new UserBox(user.getName()));
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

        this.gp = new GridPane();
        this.gp.setVgap(20);
        this.gp.setHgap(20);
        this.gp.setAlignment(Pos.TOP_RIGHT);
        this.gp.setPadding(new Insets(5,150,200,5));

        this.points = new SimpleStringProperty("0");
        this.multiProp = new SimpleStringProperty("0");
        this.roundProp = new SimpleStringProperty("0");

        this.top = new HBox();
        for(UserBox userBox : userBoxes){

            top.getChildren().add(userBox);
            this.pointLabel = LABEL_STYLE.getLabel(this.points.getValue());
            this.nameLabel = LABEL_STYLE.getLabel(userBox.getName());

            this.pointValue = new SimpleIntegerProperty(userBox.getIntPoint());
            this.points.bind(SimpleStringProperty.stringExpression(userBox.getPointsIntValue()));
            this.points.addListener((observable, oldValue, newValue) -> {
                pointLabel = LABEL_STYLE.getLabel(this.points.getValue());
                updateScoreTable(multi, round, pointLabel, nameLabel);
            });

            this.multiValue = new SimpleIntegerProperty(game.getMultiplier());
            this.multiProp.bind(SimpleStringProperty.stringExpression(game.getMultiPropProperty()));
            this.multiProp.addListener((observable, oldValue, newValue) -> {
                multi = LABEL_STYLE.getLabel(this.game.getMultiplier() + "x");
                updateScoreTable(multi, round, pointLabel, nameLabel);
            });

            this.roundValue = new SimpleIntegerProperty(game.getGameRound());
            this.roundProp.bind(SimpleStringProperty.stringExpression(game.getMultiPropProperty()));
            this.roundProp.addListener((observable, oldValue, newValue) -> {
                round = LABEL_STYLE.getLabel(this.game.getGameRound());
                updateScoreTable(multi, round, pointLabel, nameLabel);
            });


            updateScoreTable(multi, round, pointLabel, nameLabel);
        }

//      top.getChildren().addAll(multi, round);
        top.setSpacing(20);
        top.setAlignment(Pos.CENTER);
        this.background.root.setBottom(gp);
        getRoot().getChildren().add(this.background.root);
    }

    private void updateScoreTable( Label multi, Label round, Label pointLabel, Label nameLabel) {
        this.gp.getChildren().clear();

        this.gp.add(LABEL_STYLE.getLabel("Player"),0,0 );
        this.gp.add(LABEL_STYLE.getLabel("Points"),0,1 );
        this.gp.add(LABEL_STYLE.getLabel("Multiplier"),0,2 );
        this.gp.add(LABEL_STYLE.getLabel("Round"),0,3);


        this.gp.add(nameLabel,1,0);
        this.gp.add(pointLabel,1,1);
        this.gp.add(multi,1,2);
        this.gp.add(round, 1,3);

    }

    protected String changePointValue(UserBox user){
        return user.getPoints();
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
    
    public void addPoints(int points) {
        User user = this.game.getUser();
        for(UserBox userBox : userBoxes){
            if(userBox.getName().equals(user.getName())){
                userBox.addPoints(points);
                updateScore(userBox);
            }
        }
    }

    private void updateScore(UserBox userBox) {
        userBox.getPoints();
    }

    public void setPoints(int points) {
        User user = this.game.getUser();
        for(UserBox userBox : userBoxes){
            if(userBox.getName().equals(user.getName())){
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
            a.setTextFill(Color.WHITE);
            a.setFont(new Font(20));
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
        buttons = new VBox();
        buttons.getChildren().setAll(this.buttonsList);
        this.buttons.setOpacity(0);
        this.buttonsList.addListener((ListChangeListener<AnswerButton>) c -> {
            this.buttons.getChildren().setAll(this.buttonsList);
        });
        buttons.setAlignment(Pos.TOP_LEFT);
        buttons.setSpacing(40);
        buttons.setPrefHeight(80);

        
        BorderPane root = new BorderPane();
        
        this.start = new ReButton("");
        this.start.setBackground(ElementBackgroundCreator.createBackgroundImg("res/icons/icon_play.png"));
        root.setRight(start);
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
            AnswerButton ansBtn = new AnswerButton();
            buttonsList.add(ansBtn);
        }


        VBox vspacer = new VBox();
        vspacer.setMinWidth(100);
        vspacer.setMaxWidth(100);
        root.setLeft(vspacer);

        root.setCenter(buttons);


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
        private HBox hspacer;
        
        private GameBackground() {
            this.bottom = new StackPane();
            this.root = new BorderPane();
            this.hspacer = new HBox();
            hspacer.setMinHeight(100);
            hspacer.setMaxHeight(100);
            this.root.setTop(hspacer);
            this.root.setCenter(bottom);
            this.root.setBackground(ElementBackgroundCreator.getBackground(Colors.BG));
            this.scene = new Scene(this.root);
        }
        
    }
}
