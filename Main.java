import design.Colors;
import design.Labels;
import functions.ImageCreator;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import scenes.ObservableScene;
import scenes.start.StageController;

import java.util.Observable;
import java.util.Observer;

public class Main extends Application implements Observer {

    private StageController stageController;
    private Scene scene;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    public Main(){
        this.stageController = new StageController(this);
    }

    @Override
    public void start(Stage primaryStage) {
//        StackPane s = new StackPane();
//        s.setMinSize(600, 400);
//
//        StackPane st = new StackPane();
//        st.setBackground(Colors.BASE_BG.getBackground());
//        st.setMinSize(500, 400);
//        st.setMaxSize(500, 400);
//
//        s.getChildren().addAll(st);
//        HBox h = new HBox();
//        h.getChildren().addAll(Labels.H1.getLabel("ReAct"), Labels.SMALL.getLabel("Reloaded"));
//        h.setAlignment(Pos.CENTER);
//        h.setBlendMode(BlendMode.SOFT_LIGHT);
//        st.getChildren().add(h);

        primaryStage.setScene(this.stageController.getCurrentScene());
        //this.test();
        this.stage = primaryStage;
        this.stage.show();
    }

    private void test(){
        AnchorPane root = new AnchorPane();
        this.scene = new Scene(root);

        ImageView playBtn = new ImageView(ImageCreator.getImage("/Users/HxA/Pictures/Unsplash/nathan-anderson-316188-unsplash.jpg"));
        root.getChildren().add(playBtn);
        playBtn.setPreserveRatio(true);

        Circle playBtn_mask = new Circle(50.0, Colors.BTN_BAD.getColor());
        playBtn_mask.setCenterX(300);
        playBtn_mask.setCenterY(300);

        playBtn.setClip(playBtn_mask);
        this.stage.setScene(scene);
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
        if(o != null && arg != null){
            if(o instanceof StageController){
                if(arg instanceof ObservableScene){
                    this.stage.setScene(((ObservableScene)arg).getScene());
                }
            }
        }
    }
}