/**
 * @author Henock Arega
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import scenes.gamemodes.ContinuousGameScene;
import scenes.gamemodes.GameScene;
import scenes.gamemodes.NormalGameScene;

import java.util.Observable;
import java.util.Observer;

public class ReActTest extends Application implements Observer {
    
    GameScene gc = new NormalGameScene(this);
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setFillWidth(false);
        buttons.setSpacing(30);
        Button a = new Button("Normal Game");
    
        Alert popup = new Alert(Alert.AlertType.INFORMATION);
        popup.getDialogPane().setContent(new StackPane(buttons));
        popup.setWidth(300);
        Button s = new Button("Select");
        s.setOnAction(event -> {
            popup.showAndWait();
        });
        
        a.setMinSize(100, 40);
        a.setOnAction(event -> {
            primaryStage.setScene(new NormalGameScene(this).getScene());
            primaryStage.show();
            primaryStage.centerOnScreen();
            popup.close();
        });
        Button b = new Button("Continuous Game");
        b.setMinSize(100, 40);
        b.setOnAction(event -> {
            primaryStage.setScene(new ContinuousGameScene(this).getScene());
            primaryStage.show();
            popup.close();
            primaryStage.centerOnScreen();
        });
        buttons.getChildren().addAll(a, b);
        
        primaryStage.setScene(new Scene(s));
        primaryStage.show();
        
    }
}
