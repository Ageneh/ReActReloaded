/**
 * @author Henock Arega
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Close;
import model.Ranking;
import model.isGame;
import scenes.gamemodes.CoOpGameScene;
import scenes.gamemodes.ContinuousGameScene;
import scenes.GameModeScene;
import scenes.NameScene;
import scenes.RanglistScene;
import scenes.SettingScene;
import scenes.elements.CustomMenuButton;
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

        primaryStage.setMinHeight(Sizes.HEIGHT.getInt());
        primaryStage.setMaxHeight(Sizes.HEIGHT.getInt());

        primaryStage.setMinWidth(Sizes.WIDTH.getInt());
        primaryStage.setMaxWidth(Sizes.WIDTH.getInt());

        primaryStage.setResizable(false);

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: #BCC7D3");


        /*
        * BorderPane Top
        *
         */
        HBox hboxTop = new HBox();
        hboxTop.setStyle("-fx-background-color: #15292F");
        hboxTop.setMinHeight(250);
        hboxTop.setMaxHeight(250);
        hboxTop.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("ReaAct");
        titleLabel.setFont(new Font(200));
        titleLabel.setTextFill(Color.WHITE);
        hboxTop.getChildren().add(titleLabel);
        bp.setTop(hboxTop);
        /*
         * BorderPane bottom
         *
         */

        HBox hboxBottom = new HBox();
        hboxBottom.setMinHeight(100);
        hboxBottom.setMaxHeight(100);

        bp.setBottom(hboxBottom);


        /*
         *  BorderPane Center
         *
         */

        GridPane gp = new GridPane();

        CustomMenuButton menu1 = new CustomMenuButton("SchnellesSpiel");
        CustomMenuButton menu2 = new CustomMenuButton("Einstellungen");
        CustomMenuButton menu3 = new CustomMenuButton("Spielmodi");
        CustomMenuButton menu4 = new CustomMenuButton("Rangliste");




        menu1.getBox().setOnMouseClicked(event -> {
            primaryStage.setScene(new NameScene().getScene());
            primaryStage.show();
        });

        menu2.getBox().setOnMouseClicked(event -> {
            primaryStage.setScene(new SettingScene().getScene());
            primaryStage.show();
        });
        b.setMinSize(100, 40);
        b.setOnAction(event -> {
            primaryStage.setScene(new ContinuousGameScene(this).getScene());

        menu3.getBox().setOnMouseClicked(event -> {
            primaryStage.setScene(new GameModeScene().getScene());
            primaryStage.show();
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
        

        menu4.getBox().setOnMouseClicked(event -> {
            primaryStage.setScene(new RanglistScene().getScene());
            primaryStage.show();
        });



        gp.setAlignment(Pos.CENTER);
        gp.setVgap(50);
        gp.setHgap(50);
        gp.setPadding(new Insets(100, 100, 109, 100));
        gp.add(menu1.getBox(),0,1);
        gp.add(menu2.getBox(),1,1);
        gp.add(menu3.getBox(),0,2);
        gp.add(menu4.getBox(),1,2);

        gp.setGridLinesVisible(false);
        bp.setCenter(gp);






//
//
//
//        VBox buttons = new VBox();
//        buttons.setAlignment(Pos.CENTER);
//        buttons.setFillWidth(false);
//        buttons.setSpacing(30);
//        Button a = new Button("Normal Game");
//
//        Alert popup = new Alert(Alert.AlertType.INFORMATION);
//        popup.getDialogPane().setContent(new StackPane(buttons));
//        popup.setWidth(300);
//        Button s = new Button("Select");
//        s.setOnAction(event -> {
//            popup.showAndWait();
//        });
//
//        a.setMinSize(100, 40);
//        a.setOnAction(event -> {
//            primaryStage.setScene(new NormalGameScene(this).getScene());
//            primaryStage.show();
//            primaryStage.centerOnScreen();
//            popup.close();
//        });
//        Button b = new Button("Continuous Game");
//        b.setMinSize(100, 40);
//        b.setOnAction(event -> {
//            primaryStage.setScene(new ContinuousGameScene(this).getScene());
//            primaryStage.show();
//            popup.close();
//            primaryStage.centerOnScreen();
//        });
//        buttons.getChildren().addAll(a, b);
//
        primaryStage.setScene(new Scene(bp));
        primaryStage.show();

    }

    public enum Sizes {

        WIDTH(1275),
        HEIGHT(850);

        private int val;

        Sizes(int val) {
            this.val = val;
        }

        public int getInt() {
            return val;
        }
    }
    
    
    
}
