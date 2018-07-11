package scenes.gamemodes;

import design.Colors;
import design.Labels;
import functions.ElementBackgroundCreator;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;
import scenes.ObservableScene;
import scenes.elements.AnswerButton;
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
    protected VBox buttons;
    protected ObservableList<AnswerButton> buttonsList;
    protected ReButton start;
    protected SimpleBooleanProperty started;
    protected SimpleBooleanProperty answered;

    protected SimpleStringProperty pointProp;

    protected SimpleStringProperty nameProp;

    protected SimpleStringProperty multiProp;

    protected SimpleStringProperty roundProp;


    protected Label nameLabel;
    protected Label pointLabel;

    protected PauseTransition pauseTransition;
    protected FadeTransition fadeTransition;
    protected HBox top;
    protected GridPane gp;
    private GameBackground background;
    private String fxmlPath;

    GameScene(T game, Observer o) {
        super(o);
        this.game = game;
        this.game.addObserver(o);
        this.game.addObserver(this);
        addObserver(o);
        this.fxmlPath = fxmlPath;
        this.background = new GameBackground();

        this.userBoxes = new ArrayList<>();
        for (User user : game.getUsers()) {
            this.userBoxes.add(new UserBox(user.getName()));
        }

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
        this.gp = new GridPane();
        this.gp.setVgap(20);
        this.gp.setHgap(20);
        this.gp.setAlignment(Pos.TOP_RIGHT);
        this.gp.setPadding(new Insets(5, 100, 0, 0));


        this.init();


        this.pointProp = new SimpleStringProperty("0");
        this.nameProp = new SimpleStringProperty("");
        this.roundProp = new SimpleStringProperty("1");
        this.multiProp = new SimpleStringProperty("1x");



        Label name = LABEL_STYLE.getLabel("Unknown");
        this.top = new HBox();
        int r = 0;
        for (UserBox userBox : userBoxes) {
            name = LABEL_STYLE.getLabel(userBox.getName());
            this.nameProp.bind(userBox.getNameValue());
            this.pointProp.bind(SimpleStringProperty.stringExpression(userBox.getPointsIntValue()));
            this.multiProp.bind(SimpleStringProperty.stringExpression(game.getMultiPropProperty()));
            this.roundProp.bind(SimpleStringProperty.stringExpression(game.getGameRoundProperty()));
        }


        name.textProperty().bind(nameProp);
        this.gp.add(name, 1, 0);

        Label points = LABEL_STYLE.getLabel("0");
        points.textProperty().bind(pointProp);
        this.gp.add(points, 1, 1);

        Label multi = LABEL_STYLE.getLabel("1");
        multi.textProperty().bind(multiProp);
        this.gp.add(multi, 1, 2);

        Label round = LABEL_STYLE.getLabel("1");
        round.textProperty().bind(roundProp);
        this.gp.add(round, 1, 3);



        this.gp.add(LABEL_STYLE.getLabel("Player"), 0, 0);
        this.gp.add(LABEL_STYLE.getLabel("Points"), 0, 1);
        this.gp.add(LABEL_STYLE.getLabel("Multiplier"), 0, 2);
        this.gp.add(LABEL_STYLE.getLabel("Round"), 0, 3);


        top.setSpacing(20);
        top.setAlignment(Pos.CENTER);



        getRoot().getChildren().add(this.background.root);

        nameInput();
    }

    GameScene(T game, ObservableScene o) {
        super(o);
        this.game = game;
        this.game.addObserver(o);
        this.game.addObserver(this);
        addObserver(o);
        this.fxmlPath = fxmlPath;
        this.background = new GameBackground();

        this.userBoxes = new ArrayList<>();
        for (User user : game.getUsers()) {
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
        this.gp = new GridPane();
        this.gp.setVgap(20);
        this.gp.setHgap(20);
        this.gp.setAlignment(Pos.TOP_RIGHT);
        this.gp.setPadding(new Insets(5, 100, 0, 0));

        nameLabel = LABEL_STYLE.getLabel("");
        pointLabel = LABEL_STYLE.getLabel("");

        this.init();


        this.pointProp = new SimpleStringProperty("0");
        this.nameProp = new SimpleStringProperty("");
        this.roundProp = new SimpleStringProperty("1");
        this.multiProp = new SimpleStringProperty("1x");




        this.top = new HBox();
        int r = 0;
        for (UserBox userBox : userBoxes) {
            gp.add(userBox,1,r++);
        }

        this.gp.add(LABEL_STYLE.getLabel("Player:\n\nPoints:"),0,0);
        this.gp.add(LABEL_STYLE.getLabel("Player:\n\nPoints:"),0,1);



        top.setSpacing(20);
        top.setAlignment(Pos.CENTER);



        getRoot().getChildren().add(this.background.root);

    }

    protected abstract void evalAction(isGame.Action action);

    protected abstract void evalMode(GameMode.Mode mode);

    public void addPoints(int points) {
        User user = this.game.getUser();
        for (UserBox userBox : userBoxes) {
            if (userBox.getName().equals(user.getName())) {
                userBox.addPoints(points);
            }
        }
    }

    public BorderPane getBackground() {
        return background.root;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public ArrayList<User> getUsers() {
        return game.getUsers();
    }

    public void ready() {
        this.setAnswers(game.getAnswers());
    }

    public void setMulti(int multi) {
        this.multi.setText(String.valueOf(multi));
    }

    public void setPoints(int points) {
        User user = this.game.getUser();
        for (UserBox userBox : userBoxes) {
            if (userBox.getName().equals(user.getName())) {
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
//        this.background.root.setRight(start);
        this.start.setAlignment(Pos.TOP_RIGHT);
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
        this.background.root.setLeft(vspacer);

        VBox vspacer2 = new VBox();
        vspacer2.setMinWidth(150);
        vspacer2.setMaxWidth(150);
        this.background.root.setRight(vspacer2);

        HBox hspacer2 = new HBox();
        hspacer2.setMinHeight(100);
        hspacer2.setMaxHeight(100);
        this.background.root.setBottom(hspacer2);

        BorderPane center = new BorderPane();
        center.setLeft(buttons);
        center.setCenter(start);
        center.setBottom(gp);


        this.background.root.setCenter(center);


        addLayer(root);
    }

    private void nameInput() {
        Stage nameInput = new Stage();
        ArrayList<TextField> names = new ArrayList<>();

        FlowPane flowPane = new FlowPane();
        Scene scene = new Scene(flowPane);

        ReButton start = new ReButton("Start");
        start.setOnAction(event -> {
            nameInput.close();
        });

        int i = 1;
        String name;
        VBox verticalInput = new VBox();
        flowPane.getChildren().add(verticalInput);
        for (User u : game.getUsers()) {
            if (i > 1) name = User.STD_NAME + i;
            else name = User.STD_NAME;
            TextField textField = new TextField(name);
            names.add(textField);
        }
        verticalInput.getChildren().addAll(names);
        verticalInput.getChildren().add(start);

        Button close = new Button("Start");
        nameInput.setScene(scene);
        nameInput.setWidth(300);
        nameInput.setHeight(300);
        nameInput.centerOnScreen();
        nameInput.setAlwaysOnTop(true);
        nameInput.initStyle(StageStyle.UTILITY);
        nameInput.setResizable(false);
        nameInput.setTitle("Usernames");
        nameInput.showAndWait();

        ArrayList<String> usernames = new ArrayList<>();
        int f = 0;
        for (TextField field : names){
            usernames.add(field.getText());
            if(names.size() == 1){
                userBoxes.get(f).setUsername(field.getText());
            }
        }
        game.setUsers(usernames.toArray(new String[usernames.size()]));
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
