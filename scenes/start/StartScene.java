package scenes.start;


import design.Colors;
import design.Labels;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import scenes.ObservableScene;

public class StartScene extends ObservableScene<StackPane> {

    private HBox secondaryMenu;
    private VBox primaryMenu;
    private AnchorPane base;

    public StartScene(){
        super(new StackPane());
        super.setBackground(Colors.BTN_GOOD);

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

    @Override
    public StackPane getRoot() {
        return super.getRoot();
    }
}
