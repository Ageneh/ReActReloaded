package scenes.fxml;

import design.Colors;
import design.Labels;
import functions.ANSI;
import functions.ElementBackgroundCreator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Close;
import model.GameMode;
import scenes.ObservableScene;
import scenes.StartScene;
import scenes.gamemodes.GameScene;
import scenes.start.StageController;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/*******************************************
 * TESTING TESTING TESTING TESTING TESTING *
 *******************************************/
public class ReAct extends Application implements Observer {
    
    private Scene scene;
    private Stage stage;
    private StartScene startScene;
    private ReActController controller;
    private NameSceneController nsc;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private BorderPane root;
    @FXML
    private HBox gameModeBtn;
    @FXML
    private HBox settingsBtn;
    @FXML
    private HBox quickGameBtn;
    @FXML
    private Pane rankingBtn;
    
    public ReAct() {
        this.controller = new ReActController(this);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    void initialize() {
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'ReAct.fxml'.";
        assert gameModeBtn != null : "fx:id=\"gameModeBtn\" was not injected: check your FXML file 'ReAct.fxml'.";
        assert settingsBtn != null : "fx:id=\"settingsBtn\" was not injected: check your FXML file 'ReAct.fxml'.";
        assert quickGameBtn != null : "fx:id=\"quickGameBtn\" was not injected: check your FXML file 'ReAct.fxml'.";
        assert rankingBtn != null : "fx:id=\"rankingBtn\" was not injected: check your FXML file 'ReAct.fxml'.";

    }

    @FXML
    void showGameModeSelection(ActionEvent event) {
        System.out.println("Mode selection");
    }

    @FXML
    void showRanking(ActionEvent event) {
        System.out.println("Ranking");
    }

    @FXML
    void showSettings(MouseEvent event) {
        System.out.println("Settigns");
    }

    @FXML
    void startNormalGame(MouseEvent event) throws IOException {
        System.out.println("Quick Maths");
        nsc = new NameSceneController(quickGameBtn.getScene());
//        Parent newScene = FXMLLoader.load(getClass().getResource("nameScene.fxml"));
//        quickGameBtn.getScene().setRoot(newScene);
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

    
    //////////// OVERRIDES
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = new Stage();

        this.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("ReAct.fxml"))));
        
        this.stage.setMinHeight(ObservableScene.Sizes.HEIGHT.getInt());
        this.stage.setMinWidth(ObservableScene.Sizes.WIDTH.getInt());
        this.stage.setResizable(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.show();
        this.stage.setOnCloseRequest(event -> {
            this.controller.close(Close.Code.CLOSE);
            System.exit(0);
        });
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