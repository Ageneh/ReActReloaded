package scenes.elements;

import javafx.scene.control.Button;
import scenes.start.StageController;

public class BackButton extends Button {
    
    public BackButton() {
        this("Zurück");
    }
    
    public BackButton(String text) {
        super(text);
        this.init();
    }
    
    private void init() {
        this.setOnMouseClicked(event -> {
            new StageController().toPrevious();
        });
    }
    
}