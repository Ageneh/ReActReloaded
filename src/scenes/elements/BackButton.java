package scenes.elements;

import javafx.scene.Scene;
import javafx.scene.control.Button;
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
            stage.setScene(new Scene(observableScene.getPreviousScene().getRoot()));
        });
    }
    
}