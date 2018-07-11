package scenes.elements;

import javafx.scene.control.Button;

/**
 * @author Henock Arega
 * @author Michael Heide
 */
public class ReButton extends Button {
    
    public ReButton() {
        super("Button");
    }

    public ReButton(String text) {
        super(text);
        this.init();
    }
    
    private void init() {
        this.setMaxSize(300, 80);
        this.setMinSize(300,80);
    }
}
