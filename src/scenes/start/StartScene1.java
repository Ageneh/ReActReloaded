package scenes.start;


import design.Colors;
import design.Labels;
import functions.ElementBackgroundCreator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import scenes.ObservableScene;

import java.util.Observable;

public class StartScene1 extends ObservableScene {
    
    private HBox secondaryMenu;
    private VBox primaryMenu;
    private AnchorPane base;
    private StackPane root;
    
    public StartScene1() {
        super();
        root = new StackPane();
        root.setBackground(ElementBackgroundCreator.getBackground(Colors.BTN_GOOD));
        
        base = new AnchorPane();
        secondaryMenu = new HBox();
        secondaryMenu.getChildren().add(Labels.M_SMALL.getLabel("Song Library"));
        secondaryMenu.getChildren().add(Labels.M_SMALL.getLabel("Ranking"));
        secondaryMenu.getChildren().add(Labels.M_SMALL.getLabel("Einstellungen"));
        this.secondaryMenu.setSpacing(20);
        base.getChildren().add(secondaryMenu);
        AnchorPane.setRightAnchor(secondaryMenu, 20.0);
        AnchorPane.setTopAnchor(secondaryMenu, 10.0);
        base.setBackground(Colors.GAMEPLAY_INTERACTIVE.getBackground());
        this.getRoot().getChildren().add(base);
    }
    
    public StackPane getRoot() {
        return root;
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
}
