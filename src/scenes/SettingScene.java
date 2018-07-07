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

public class SettingScene extends ObservableScene {

    private BorderPane bp;
    private GridPane grid;


    public SettingScene(){
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
        Label subTitle = new Label("Einstellungen");
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





        getRoot().setBackground(ElementBackgroundCreator.getBackground(Color.color(0.74,0.78,0.82)));
        getRoot().getChildren().addAll(this.bp);

    }

}
