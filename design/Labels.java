package design;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * An enum containing all {@link javafx.scene.text.Font fonts} used for the game and to
 * keep a consistency in design.
 */
public enum Labels {

    H1(44, 700),
    M_SMALL(15, 200),
    SMALL(10, 200),
    ;

    private int size;
    private int weight;
    private FontFamily fontFamily;

    Labels(int size, int weight){
        this(size, weight, FontFamily.BASE);
    }

    Labels(int size, int weight, FontFamily fontFamily){
        this.size = size;
        this.weight = weight;
        this.fontFamily = fontFamily;
    }

    public Label getLabel(String text, Colors color){
        Label label = new Label(text);
        label.setFont(Font.font(
                this.fontFamily.getFontfamily(),
                FontWeight.findByWeight(this.weight),
                this.size));
        label.setTextFill(color.getColor());
        return label;
    }

    public Label getLabel(Colors color){
        return this.getLabel("", color);
    }

    public Label getLabel(String text){
        return this.getLabel(text, Colors.WHITE);
    }

    public Label getLabel(){
        return this.getLabel("", Colors.WHITE);
    }

    /**
     * Contains all available font families used for the total design.
     */
    private enum FontFamily{

        BASE("Roboto Condensed");

        String fontfamily;

        FontFamily(String fontfamily){
            this.fontfamily = fontfamily;
        }

        public String getFontfamily() {
            return fontfamily;
        }
    }

}
