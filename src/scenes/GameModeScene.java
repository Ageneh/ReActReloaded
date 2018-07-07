package scenes;

import functions.ElementBackgroundCreator;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import scenes.elements.CustomActionBtn;

public class GameModeScene extends ObservableScene {

    private BorderPane bp;
    private GridPane grid;
    private CustomActionBtn normalGame;
    private CustomActionBtn timedGame;
    private CustomActionBtn continuousGame;
    private CustomActionBtn multiGame;


    public GameModeScene(){
        super();
        this.init();
    }

    private void init() {
        bp = new BorderPane();

        /*
         * BorderPane Top
         *
         */
        HBox hboxTop = new HBox();
        hboxTop.setStyle("-fx-background-color: #15292F");
        hboxTop.setMinHeight(250);
        hboxTop.setMaxHeight(250);
        hboxTop.setAlignment(Pos.CENTER);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("ReaAct");
        titleLabel.setFont(new Font(150));
        titleLabel.setTextFill(Color.WHITE);
        Label subTitle = new Label("Spielmodi auswählen:");
        subTitle.setFont(new Font(30));
        subTitle.setTextFill(Color.WHITE);


        vbox.getChildren().addAll(titleLabel,subTitle);
        hboxTop.getChildren().addAll(vbox);
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
         *
         * BorderPane Center
         *
         *
         */
        this.grid = new GridPane();
        this.grid.setAlignment(Pos.CENTER);
        this.grid.setHgap(20);
        this.grid.setVgap(20);

        bp.setCenter(this.grid);




        normalGame = new CustomActionBtn("Normales Spiel");
        timedGame = new CustomActionBtn("Zeit Spiel");
        continuousGame = new CustomActionBtn("Endlos Spiel");
        multiGame = new CustomActionBtn("Multiplayer");


        this.grid.add(normalGame.getBox(),0,1);
        this.grid.add(timedGame.getBox(),0,2);
        this.grid.add(continuousGame.getBox(),0,3);
        this.grid.add(multiGame.getBox(),0,4);


        normalGame.getBox().setOnMouseClicked(event -> {

        });

        timedGame.getBox().setOnMouseClicked(event -> {

        });

        continuousGame.getBox().setOnMouseClicked(event -> {

        });

        multiGame.getBox().setOnMouseClicked(event -> {

        });




        getRoot().setBackground(ElementBackgroundCreator.getBackground(Color.color(0.74,0.78,0.82)));
        getRoot().getChildren().addAll(this.bp);
    }

}
