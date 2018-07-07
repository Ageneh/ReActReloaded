/**
 * @author Henock Arega
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Close;
import model.Ranking;
import model.isGame;
import scenes.gamemodes.CoOpGameScene;
import scenes.gamemodes.ContinuousGameScene;
import scenes.gamemodes.GameScene;
import scenes.gamemodes.NormalGameScene;

import java.util.Observable;
import java.util.Observer;

public class ReActTest extends Application implements Observer {
    
    GameScene gc = new NormalGameScene(this);
    Ranking ranking = new Ranking();
    Stage stage;
    private Alert popup;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        try{
            if(arg instanceof isGame.Action){
                isGame.Action action = (isGame.Action) arg;
                switch (action){
                    case CURRENT_SONG:
                        break;
                    case ANSWERS:
                        break;
                    case NEW_MULTIPLIER:
                        break;
                    case ANSWER_CORRECT:
                        break;
                    case ANSWER_INCORRECT:
                        break;
                    case ANSWER:
                        break;
                    case LIFECOUNT:
                        break;
                    case ACTIVE_USER:
                        break;
                    case RANK:
                        ranking.add(action.getVal());
                        System.out.println("Added to ranking");
                        ranking.close(Close.Code.CLOSE);
                        System.out.println(ranking.getRanking());
                        break;
                    case POINTS:
                        break;
                }
            }
            else if(arg instanceof Close.Code){
                stage.close();
                stage.centerOnScreen();
                popup.showAndWait();
            }
            
        }catch (NullPointerException e){
        
        }
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        this.stage = primaryStage;
        
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setFillWidth(false);
        buttons.setSpacing(30);
        Button a = new Button("Normal Game");
        Button b = new Button("Continuous Game");
        Button c = new Button("CoOp Game");
    
        popup = new Alert(Alert.AlertType.INFORMATION, "Confirm Delete",
                ButtonType.CANCEL);
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
        b.setMinSize(100, 40);
        b.setOnAction(event -> {
            primaryStage.setScene(new ContinuousGameScene(this).getScene());
            primaryStage.show();
            popup.close();
            primaryStage.centerOnScreen();
        });
        
        c.setMinSize(100, 40);
        c.setOnAction(event -> {
            primaryStage.setScene(new CoOpGameScene("User 1", "User 2", this).getScene());
            primaryStage.show();
            popup.close();
            primaryStage.centerOnScreen();
        });
        buttons.getChildren().addAll(a, b, c);
        primaryStage.setScene(new Scene(s));
        primaryStage.show();
        
    }
    
    
    
}
