/**
 * @author Henock Arega
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scenes.ObservableScene;
import scenes.gamemodes.GameScene;

import java.util.Observable;
import java.util.Observer;

public class ReactMain extends Application implements Observer {
    
    private Stage stage;
    private MainController controller;
    
    public ReactMain() {
        this.controller = new MainController(this);
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        this.stage.setResizable(false);
        this.stage.centerOnScreen();
        this.controller.init();
        this.stage.show();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof MainController){
            if(arg instanceof ObservableScene){
                this.stage.setScene(((ObservableScene) arg).getScene());
            }
        }
    }
    
}
