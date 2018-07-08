package scenes;/**
 * @author Henock Arega
 */

import design.Colors;
import design.Labels;
import functions.ElementBackgroundCreator;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.isGame;
import scenes.elements.CustomActionBtn;
import scenes.elements.CustomMenuButton;
import scenes.gamemodes.NormalGameScene;

import java.util.Observable;
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

        CustomMenuButton menu1 = new CustomMenuButton("SchnellesSpiel");
        CustomMenuButton menu2 = new CustomMenuButton("Einstellungen");
        CustomMenuButton menu3 = new CustomMenuButton("Spielmodi");
        CustomMenuButton menu4 = new CustomMenuButton("Rangliste");
        grid.setAlignment(Pos.CENTER);
        grid.add(menu1.getBox(),0,3);
        grid.add(menu2.getBox(),1,3);
        grid.add(menu3.getBox(),0,4);
        grid.add(menu4.getBox(),1,4);

        menu1.getBox().setOnMouseClicked(event -> {
            setChanged();
            NormalGameScene ngs = new NormalGameScene(this);
            notifyObservers(ngs);
        });

        menu2.getBox().setOnMouseClicked(event -> {
            setChanged();
            notifyObservers(new SettingScene(this));
        });

        menu3.getBox().setOnMouseClicked(event -> {
            setChanged();
            notifyObservers(new GameModeScene(this));
        });

        menu4.getBox().setOnMouseClicked(event -> {
            setChanged();
            notifyObservers(isGame.Action.SHOW_RANKING);
        });
    }
    
    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
    }
}

