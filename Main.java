import design.Colors;
import functions.ImageCreator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Settings;
import model.models.NormalGame;
import scenes.ObservableScene;
import scenes.start.StageController;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

/*******************************************
 * TESTING TESTING TESTING TESTING TESTING *
 *******************************************/
public class Main extends Application implements Observer {

    private StageController stageController;
    private Scene scene;
    private Stage stage;
    private NormalGame model;
    private Settings settings;

    // FOR TESTING PURPOSES

    public static void main(String[] args) {
        launch(args);
    }

    public Main(){
        this.stageController = new StageController(this);
        this.settings = new Settings();
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

        //primaryStage.setScene(this.stageController.getCurrentScene());
        this.stage = primaryStage;
        this.test();
        this.stage.show();
    }

    private void test(){
        StackPane root = new StackPane();
        this.scene = new Scene(root);
        this.stage = new Stage();

        Button bStart = new Button("Start");
        Button bEnd = new Button("End");
        bStart.setOnMouseClicked(event -> {
            this.model.start();
        });
        bEnd.setOnMouseClicked(event -> {
            this.model.end();
        });
        HBox box = new HBox();
        box.getChildren().addAll(bStart, bEnd);

        VBox v = new VBox();
        v.getChildren().add(box);
        Button bSelectF = new Button("Select a Music-File");
        bSelectF.setOnAction(event -> {
            DirectoryChooser dc = new DirectoryChooser();
            dc.setInitialDirectory(new File("/Users/"));
//            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Music", "*.mp3");
//            dc.getExtensionFilters().addAll(filter);
            File f = dc.showDialog(this.stage);
            if(f != null) {
                System.out.println(f.getName());
                this.settings.addToLib(f.getAbsolutePath());
            }
        });
        v.getChildren().add(bSelectF);
        root.getChildren().add(v);

        this.stage.setScene(this.scene);
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
        if(o != null && arg != null){
            if(o instanceof StageController){
                if(arg instanceof ObservableScene){
                    this.stage.setScene(((ObservableScene)arg).getScene());
                }
            }
        }
    }
}