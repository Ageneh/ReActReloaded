/**
 * @author Henock Arega
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import scenes.elements.CustomMenuButton;
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
         * BorderPane left & right
         *
         */

        VBox vbox = new VBox();
        vbox.setMinWidth(100);
        vbox.setMaxWidth(100);
        VBox vbox2 = new VBox();
        vbox.setMinWidth(100);
        vbox.setMaxWidth(100);

        bp.setLeft(vbox);
        bp.setRight(vbox2);

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
        gp.setAlignment(Pos.CENTER);
        gp.add(menu1.getBox(),0,3);
        gp.add(menu2.getBox(),1,3);
        gp.add(menu3.getBox(),0,4);
        gp.add(menu4.getBox(),1,4);


        gp.setGridLinesVisible(true);
        bp.setCenter(gp);






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
