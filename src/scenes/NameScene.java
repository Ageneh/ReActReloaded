package scenes;

import functions.ElementBackgroundCreator;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.User;
import scenes.elements.CustomActionBtn;
import scenes.gamemodes.NormalGameScene;

public class NameScene extends ObservableScene {

    private BorderPane bp;
    private GridPane grid;
    private CustomActionBtn start;
    private User user;

    public NameScene(){
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
        Label subTitle = new Label("Spielnamen eintragen");
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


        TextField tf = new TextField( );
        tf.setPromptText("Einmal den Namen eingeben");

        this.start = new CustomActionBtn("Start");

        this.grid.add(tf,0,0);
        this.grid.add(this.start.getBox(),0,1);

        this.start.getBox().setOnMouseClicked(event -> {
            this.user = new User(tf.getText());
            setChanged();
            notifyObservers(new NormalGameScene(this));
        });


        getRoot().setBackground(ElementBackgroundCreator.getBackground(Color.color(0.74,0.78,0.82)));
        getRoot().getChildren().addAll(this.bp);
    }
}
