package scenes.start;

import design.Colors;
import design.Labels;
import functions.ANSI;
import functions.ElementBackgroundCreator;
import functions.ImageCreator;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.GameMode;
import scenes.ObservableScene;
import scenes.gamemodes.GameScene;

import java.util.Observable;
import java.util.Observer;

/*******************************************
 * TESTING TESTING TESTING TESTING TESTING *
 *******************************************/
public class ReAct extends Application implements Observer {
    
    private Scene scene;
    private Stage stage;
    private SimpleBooleanProperty ready;
    //    private StartScene startScene;
    private StageController stageController;

//    public ReAct() {
////        gameScene = new NormalGameScene("", this);
//        this.startScene = new StartScene();
//        this.stageController = new StageController(startScene, this);
//    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
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
    
    //////////// METHODS
    
    private void testImg(AnchorPane root) {
        ImageView playBtn = new ImageView(ImageCreator.getImage("/Users/HxA/Pictures/Unsplash/nathan-anderson-316188-unsplash.jpg"));
        root.getChildren().add(playBtn);
        playBtn.setPreserveRatio(true);
        
        Circle playBtn_mask = new Circle(50.0, Colors.BTN_BAD.getColor());
        playBtn_mask.setCenterX(300);
        playBtn_mask.setCenterY(300);
        playBtn.setClip(playBtn_mask);
    }
    
    //////////// OVERRIDES
    @Override
    public void start(Stage primaryStage) {
        //primaryStage.setScene(this.stageController.getCurrentScene());
        this.stage = new Stage();
        this.scene = stageController.getCurrentScene();
        this.stage.setScene(this.scene);
        this.stage.setMinHeight(ObservableScene.Sizes.HEIGHT.getInt());
        this.stage.setMinWidth(ObservableScene.Sizes.WIDTH.getInt());
        this.stage.setResizable(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.show();

//        this.stage.setOnCloseRequest(event -> {
//            this.gameScene.close(Close.Code.CLOSE);
//            System.exit(0);
//        });
//        this.ready.set(true);
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
        
        if (o != null && arg != null) {
            if (arg instanceof GameMode.GameStatus) {
                this.evalGameStatus((GameMode.GameStatus) arg);
            }
            if (arg instanceof GameScene && o instanceof StageController) {
                this.scene = ((GameScene) arg).getScene();
            }
            if (o instanceof StageController) {
                if (arg instanceof ObservableScene) {
                    this.stage.setScene(((ObservableScene) arg).getScene());
                }
            }
//                if (arg instanceof PlayerResult) {
//                    isPlaying.set(false);
//                }
        }
    }
    
}