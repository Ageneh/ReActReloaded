package scenes.elements;

import design.Dimension;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import scenes.ObservableScene;
import scenes.StartScene;
import scenes.start.StageController;

public class BackButton extends Button {
    
    private ObservableScene observableScene;
    
    public BackButton(ObservableScene observableScene) {
        super("Zurück");
        this.observableScene = observableScene;
        this.init();
    }
    
    private void init() {
        this.setOnMouseClicked(event -> {
            Window window = observableScene.getScene().getWindow();
            Stage stage = (Stage) window;
            stage.setScene(observableScene.getPreviousScene().getScene());
        });
        this.setStyle("-fx-background-color: #ffffff");
        this.setMaxHeight(Dimension.CSTMENU_HEIGHT.intValue());
        this.setMinHeight(Dimension.CSTMENU_HEIGHT.intValue());
        this.setMaxWidth(Dimension.CSTMENU_WIDTH.intValue());
        this.setMinWidth(Dimension.CSTMENU_WIDTH.intValue());
    }
    
}