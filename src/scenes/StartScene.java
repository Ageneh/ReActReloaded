package scenes;/**
 * @author Henock Arega
 */

import design.Colors;
import design.Labels;
import functions.ElementBackgroundCreator;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import scenes.gamemodes.NormalGameScene;

public class StartScene extends ObservableScene {
    
    private Label title;
    private GridPane grid;
    
    public StartScene() {
        super();
        this.init();
    }
    
    private void init() {
        this.title = Labels.H1.getLabel("ReAct v2");
        this.title.setTextFill(Colors.BLACK.getColor());
        
        this.grid = new GridPane();
        this.grid.setAlignment(Pos.CENTER);
        this.grid.setHgap(20);
        this.grid.setVgap(10);
        this.grid.setMinWidth(100);
        getRoot().setBackground(ElementBackgroundCreator.getBackground(Colors.BTN_BAD));
        
        this.grid.addRow(0, this.title);
        Button b = new Button("Hello");
        b.setOnAction(event -> {
            setChanged();
            notifyObservers(new NormalGameScene(this));
        });
        this.grid.addRow(1, b);
        
        getRoot().getChildren().addAll(this.grid);
    }
}
