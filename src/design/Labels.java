package design;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * An enum containing all {@link javafx.scene.text.Font fonts} used for the game and to
 * keep a consistency in design.
 */
public enum Labels {
    
    H1(44, 700),
    MASSIVE(72, 700),
    M_SMALL(22, 200),
    SMALL(10, 200),;
    
    private int size;
    private int weight;
    private FontFamily fontFamily;
    
    Labels(int size, int weight) {
        this(size, weight, FontFamily.BASE);
    }
    
    Labels(int size, int weight, FontFamily fontFamily) {
        this.size = size;
        this.weight = weight;
        this.fontFamily = fontFamily;
    }
    
    //////////// METHODS
    public Label getLabel(Object text, Colors color) {
        return this.getLabel(text, color.getColor());
    }
    
    public Label getLabel(Object text, Color color) {
        Label label = new Label(text.toString());
        label.setFont(Font.font(
                this.fontFamily.getFontfamily(),
                FontWeight.findByWeight(this.weight),
                this.size));
        label.setTextFill(color);
        return label;
    }
    
    public Label getLabel(Colors color) {
        return this.getLabel("", color);
    }
    
    public Label getLabel(Object text) {
        return this.getLabel(text.toString(), Colors.WHITE);
    }
    
    public Label getLabel() {
        return this.getLabel("", Colors.WHITE);
    }
    
    /**
     * Contains all available font families used for the total design.
     */
    private enum FontFamily {
        
        BASE("Roboto Condensed");
        
        String fontfamily;
        
        FontFamily(String fontfamily) {
            this.fontfamily = fontfamily;
        }
        
        public String getFontfamily() {
            return fontfamily;
        }
    }
    
}
