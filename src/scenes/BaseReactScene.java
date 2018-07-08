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
import scenes.elements.BackButton;

import java.util.Observer;

/**
 * @author Henock Arega
 */
public class BaseReactScene extends ObservableScene {
    
    private BorderPane bp;
    private GridPane grid;
    private Label subTitle;
    private Label titleLabel;
    private BackButton backButton;
    
    public BaseReactScene(ObservableScene o) {
        super(o);
        titleLabel = new Label("ReaAct");
        subTitle = new Label("");
        this.backButton = new BackButton(o);
        init();
    }
    
    public BaseReactScene(String subtitle, ObservableScene o) {
        this(o);
        subTitle = new Label(subtitle);
        init();
    }
    
    public BaseReactScene(String subtitle, Observer o) {
        this(o);
        subTitle = new Label(subtitle);
    }
    
    public BaseReactScene(Observer o) {
        super(o);
        titleLabel = new Label("ReaAct");
        subTitle = new Label("");
        init();
    }
    
    private void init() {
            this.backButton = new BackButton(this);

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
        titleLabel.setFont(new Font(150));
        titleLabel.setTextFill(Color.WHITE);
        subTitle.setFont(new Font(30));
        subTitle.setTextFill(Color.WHITE);
        
        
        vbox.getChildren().addAll(titleLabel, subTitle);
        hboxTop.getChildren().addAll(vbox);
        bp.setTop(hboxTop);
        
        /*
         * BorderPane bottom
         *
         */
        
        HBox hboxBottom = new HBox();
        if(getPreviousScene() != null){
            hboxBottom.getChildren().add(backButton);
        }
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
        
        getRoot().setBackground(ElementBackgroundCreator.getBackground(Color.color(0.74, 0.78, 0.82)));
        getRoot().getChildren().addAll(this.bp);
    }
    
    public GridPane getGrid() {
        return grid;
    }
    
    public BorderPane getBp() {
        return bp;
    }
}
