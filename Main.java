import design.Colors;
import design.Labels;
import functions.ImageCreator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Close;
import model.Song;
import model.gamemodes.GameController;
import model.gamemodes.NormalGameController;
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
    Button answer1, answer2, answer3;

    public static void main(String[] args) {
        launch(args);
    }
    
    ArrayList<Song> answers;
    HBox answerButtons;
    private NormalGameController ngc;
    public Main(){
        this.stageController = new StageController(this);
        ngc = new NormalGameController("Mee", this);
    }
    
    private void test() {
//        StackPane root = new StackPane();
//        this.scene = new Scene(root);
//        this.stage = new Stage();
//
//        Button bStart = new Button("Start");
//        Button bEnd = new Button("End");
//        bStart.setOnMouseClicked(event -> {
//            this.model.start();
//        });
//        bEnd.setOnMouseClicked(event -> {
//            this.model.end();
//        });
//        HBox box = new HBox();
//        box.getChildren().addAll(bStart, bEnd);
//
//        VBox v = new VBox();
//        v.getChildren().add(box);
//        Button bSelectF = new Button("Select a Music-File");
//        bSelectF.setOnAction(event -> {
//            DirectoryChooser dc = new DirectoryChooser();
//            dc.setInitialDirectory(new File("/Users/"));
////            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Music", "*.mp3");
////            dc.getExtensionFilters().addAll(filter);
//            File f = dc.showDialog(this.stage);
//            if(f != null) {
//                System.out.println(f.getName());
//                this.settings.addToLib(f.getAbsolutePath());
//            }
//        });
//        TextField textIn = new TextField();
//        textIn.setOnKeyTyped((KeyEvent event) -> {
//            if(event.getCode() == (KeyCode.ENTER) || event.getCharacter().getBytes()[0] == '\n' || event.getCharacter().getBytes()[0] == '\r'){
//                NormalGameScene normalGameScene = new NormalGameScene(textIn.getText(), this);
//                this.stage.setScene(normalGameScene.getScene());
//            }
//        });
//        v.getChildren().add(bSelectF);
//        v.getChildren().addAll(textIn);
//        root.getChildren().add(v);
//        this.stage.setScene(this.scene);
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
        
        answer1 = new Button("answer1");
        answer2 = new Button("answer2");
        answer3 = new Button("answer3");
        
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(675, 400);
        
        answerButtons = new HBox();
        answerButtons.getChildren().addAll(answer1, answer2, answer3);
        VBox vBox = new VBox();
        Button start = new Button("Start");
        start.setOnMouseClicked(event -> {
            ngc.startGame();
            start.setDisable(true);
        });
        vBox.getChildren().addAll(start, answerButtons);
        
        Button replay = new Button("Replay");
        vBox.getChildren().add(replay);
        replay.setOnMouseClicked(event -> {
            ngc.replay();
        });
        
        Button stopGame = new Button("Stop");
        vBox.getChildren().add(stopGame);
        stopGame.setOnMouseClicked(event -> {
            ngc.close(Close.Code.CLOSE);
        });
        
        stackPane.getChildren().addAll(vBox);
        
        //primaryStage.setScene(this.stageController.getCurrentScene());
        this.stage = primaryStage;
        scene = new Scene(stackPane);
        this.stage.setScene(scene);
        this.stage.show();
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
                if (o instanceof StageController) {
                    if (arg instanceof ObservableScene) {
                        stage.setScene(((ObservableScene) arg).getScene());
                    }
                } else if (o instanceof GameController) {
//                        ANSI.YELLOW.println("____----____----");
                    if (arg instanceof Song[]) {
                        Song[] as = (Song[]) arg;
                        ArrayList<Button> btns = new ArrayList<>();
                        int i = 0;
                        for (Song s : as) {
                            btns.add(new Button(s.getTitle()));
                            btns.get(i++).setOnMouseClicked(event -> {
                                ngc.testGUI(s);
                            });
                        }
                        answerButtons.getChildren().setAll(btns);
                    }
                }
            }
        });
    }
    
}