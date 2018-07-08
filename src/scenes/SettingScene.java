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

import java.util.Observer;

public class SettingScene extends BaseReactScene {

    private BorderPane bp;
    private GridPane grid;

    public SettingScene(Observer o){
        super("Einstellungen", o);
    }

    public SettingScene(ObservableScene observableScene){
        super("Einstellungen", observableScene);
    }


    public SettingScene(ObservableScene observableScene, Observer o){
        super("Einstellungen", observableScene);
    }

}
