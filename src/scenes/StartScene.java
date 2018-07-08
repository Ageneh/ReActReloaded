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
import scenes.elements.CustomActionBtn;
import scenes.gamemodes.NormalGameScene;

import java.util.Observer;

public class StartScene extends BaseReactScene {
    
    private Button toGameMode;
    private Button toSettings;
    
    public StartScene(Observer o) {
        super("", o);
        this.init();
    }
    
    public StartScene(ObservableScene observableScene, Observer o) {
        super("", observableScene);
        this.init();
    }
    
    private void init1() {
//        this.title = Labels.H1.getLabel("ReAct v2");
//        this.title.setTextFill(Colors.BLACK.getColor());
//
//        this.grid = new GridPane();
//        this.grid.setAlignment(Pos.CENTER);
//        this.grid.setHgap(20);
//        this.grid.setVgap(10);
//        this.grid.setMinWidth(100);
//        getRoot().setBackground(ElementBackgroundCreator.getBackground(Colors.BTN_BAD));
//
//        this.grid.addRow(0, this.title);
//        Button b = new Button("Hello");
//        b.setOnAction(event -> {
//            setChanged();
//            notifyObservers(new NormalGameScene(this));
//        });
//        this.grid.addRow(1, b);
//
//        getRoot().getChildren().addAll(this.grid);
    }
    
    private void init() {
        GridPane grid = getGrid();
        
        toGameMode = new Button("Spielmodi");
        grid.add(toGameMode,0,1);
        toGameMode.setOnMouseClicked(event -> {
            setChanged();
            notifyObservers(new GameModeScene(this));
        });
        
        toSettings = new Button("Einstellungen");
        grid.add(toSettings,0,2);
        toSettings.setOnMouseClicked(event -> {
            setChanged();
            notifyObservers(new SettingScene(this));
        });
    }
}
