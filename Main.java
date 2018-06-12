import design.Colors;
import design.Labels;
import functions.ANSI;
import functions.ElementBackgroundCreator;
import functions.ImageCreator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.*;
import model.gamemodes.NormalGame;
import scenes.ObservableScene;
import scenes.start.StageController;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/*******************************************
 * TESTING TESTING TESTING TESTING TESTING *
 *******************************************/
public class Main extends Application implements Observer {

    private StageController stageController;
    private Scene scene;
    private Stage stage;
    private Button answer1, answer2, answer3;
    private HBox answerButtons;
    private ArrayList<Song> answers;
    private NormalGame cg;
    private SimpleBooleanProperty isPlaying;
    private Label currentTitle;
    private StackPane root;
    
    public Main() {
        this.stageController = new StageController(this);
        cg = new NormalGame("HELLO", this);
    }
    public static void main(String[] args) {
        launch(args);
    }
    
    
    private void testImg(AnchorPane root){
        ImageView playBtn = new ImageView(ImageCreator.getImage("/Users/HxA/Pictures/Unsplash/nathan-anderson-316188-unsplash.jpg"));
        root.getChildren().add(playBtn);
        playBtn.setPreserveRatio(true);
        
        Circle playBtn_mask = new Circle(50.0, Colors.BTN_BAD.getColor());
        playBtn_mask.setCenterX(300);
        playBtn_mask.setCenterY(300);
        playBtn.setClip(playBtn_mask);
    }
    
    //////////// METHODS
    
    private void evalGameStatus(GameMode.GameStatus arg) {
        if (arg.mode() == GameMode.Mode.GAME_OVER) {
            // TODO show game over screen
            StackPane root = new StackPane();
            AnchorPane ap = new AnchorPane();
            VBox elems = new VBox();
            ap.getChildren().add(elems);
            root.setBackground(ElementBackgroundCreator.getBackground(Color.RED));
            
            elems.getChildren().add(Labels.H1.getLabel(
                    "GAME OVER!", Colors.WHITE
            ));
            root.getChildren().add(ap);
            elems.setMaxWidth(200);
            
            this.scene.setRoot(root);
            
            ANSI.BLUE.println(arg.toString());
        }
    }
    
    //////////// OVERRIDES
    @Override
    public void start(Stage primaryStage) {
        if (System.currentTimeMillis() == 0) {
            StackPane s = new StackPane();
            s.setMinSize(600, 400);
            
            StackPane st = new StackPane();
            st.setBackground(Colors.BASE_BG.getBackground());
            st.setMinSize(500, 400);
            st.setMaxSize(500, 400);
            
            s.getChildren().addAll(st);
            HBox h = new HBox();
            h.getChildren().addAll(Labels.H1.getLabel("ReAct"), Labels.SMALL.getLabel("Reloaded"));
            h.setAlignment(Pos.CENTER);
            h.setBlendMode(BlendMode.SOFT_LIGHT);
            st.getChildren().add(h);
        }
    
        this.currentTitle = new Label("CurrentSong for testing.");
        
        answer1 = new Button("answer1");
        answer2 = new Button("answer2");
        answer3 = new Button("answer3");
    
        root = new StackPane();
        root.setPrefSize(675, 400);
        
        answerButtons = new HBox();
        answerButtons.getChildren().addAll(answer1, answer2, answer3);
        VBox vBox = new VBox();
        Button start = new Button("Start");
        start.setOnMouseClicked(event -> {
            cg.start();
            start.setDisable(true);
            isPlaying.set(true);
        });
        vBox.getChildren().addAll(currentTitle, start, answerButtons);
        
        Button replay = new Button("Replay");
        vBox.getChildren().add(replay);
        replay.setOnMouseClicked(event -> {
            cg.replay();
            isPlaying.set(true);
        });
        
        isPlaying = new SimpleBooleanProperty(false);
        isPlaying.addListener((observable, oldValue, newValue) -> {
            replay.setDisable(newValue);
        });
        
        Button stopGame = new Button("Stop");
        vBox.getChildren().add(stopGame);
        stopGame.setOnMouseClicked(event -> {
            cg.close(Close.Code.CLOSE);
        });
    
        root.getChildren().addAll(vBox);
        
        //primaryStage.setScene(this.stageController.getCurrentScene());
        this.stage = primaryStage;
        scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }
    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(() -> {
            if (o != null && arg != null) {
                if (o instanceof GameMode && arg instanceof String) {
                    this.currentTitle.setText((String) arg);
                }
                if (arg instanceof GameMode.GameStatus) {
                    this.evalGameStatus((GameMode.GameStatus) arg);
                }
                if (o instanceof StageController) {
                    if (arg instanceof ObservableScene) {
                        stage.setScene(((ObservableScene) arg).getScene());
                    }
                } else if (o instanceof isGame) {
                    if (arg instanceof Song[]) {
                        Song[] as = (Song[]) arg;
                        ArrayList<Button> btns = new ArrayList<>();
                        int i = 0;
                        for (Song s : as) {
                            btns.add(new Button(s.getTitle()));
                            btns.get(i++).setOnMouseClicked(event -> {
//                                ngc.testGUI(s);
                                cg.answer(s);
                            });
                        }
                        answerButtons.getChildren().setAll(btns);
                    }
                }
                if (arg instanceof PlayerResult) {
                    isPlaying.set(false);
                }
            }
        });
    }
    
}