package scenes.elements;

import javafx.scene.control.Button;

/**
 * @author Henock Arega
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
        this.setMaxSize(180, 75);
    }
}
