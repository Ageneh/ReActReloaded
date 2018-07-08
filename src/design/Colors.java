package design;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * All colors of each element and view, which are not images, will be defined in this enum.
 */
public enum Colors {
    
    /**
     * The standard background color of basically every or at least most frames.
     */
    BASE_BG(69, 84, 102, ColorMode.RGB),
    BG(188, 199, 211, ColorMode.RGB),
    BLACK(Color.BLACK),
    WHITE(Color.WHITE),
    BLACK_TRNS(0, 0, 0, 0.6, ColorMode.RGB),
    LINE_BG(78, 94, 115, ColorMode.RGB),
    LINE_BG_TRNS(78, 94, 115, 0.6, ColorMode.RGB),
    LINE_BG_LIGHT(104, 125, 153, ColorMode.RGB),
    LINE_BG_LIGHT_TRNS(104, 125, 153, 0.6, ColorMode.RGB),
    LABEL_MENU(255, ColorMode.RGB),
    TRANSPARENT(0, 0, 0, 0.0, ColorMode.RGB),
    GAMEPLAY_INTERACTIVE(34, 45, 59, 1.0, ColorMode.RGB),
    ANSWER_BTN(255,179,18, ColorMode.RGB),
    BTN_GOOD(126, 211, 33, ColorMode.RGB),
    BTN_GOOD_STROKE(91, 135, 64, ColorMode.RGB),
    BTN_GOOD_STROKE_TRNS(125, 213, 7, 0.4, ColorMode.RGB),
    BTN_BAD(208, 70, 87, ColorMode.RGB),
    BTN_BAD_STROKE(124, 78, 96, ColorMode.RGB),
    BTN_BAD_STROKE_TRNS(208, 70, 87, 0.4, ColorMode.RGB),
    PINK(Color.PINK),
    ACC_RED(Color.RED.brighter());;
    
    private final double ALPHA_MAX = 1.0;
    
    private Color color;
    private Number red, green, blue;
    
    
    //// CONSTRUCTORS

//////////// CONSTRUCTORS
    
    /**
     * @param color The {@link Color} value for the {@link #color}.
     * @see Colors#Colors(Number, Number, Number, double, ColorMode)
     */
    Colors(Color color) {
        this.color = color;
    }
    
    /** @see Colors#Colors(Number, double, ColorMode) */
    Colors(Number gray, ColorMode colorMode) {
        this(gray, 1, colorMode);
    }
    
    /**
     * Creates a color object. Depending on the color interpretation each component of the {@link #color} will be
     * processed accordingly and recalculated to fit into its given range.
     *
     * @param gray      The parameter for the grayscale value.
     * @param alpha     The parameter for the alpha or opacity of the {@link #color}.
     * @param colorMode Will determine how the given values are to be interpreted. <br>If it is to be interpreted as a RGB
     *                  color for instance, nothing will be readjusted and {@link Color#color(double, double, double,
     *                  double)} will be called. But if is to be fitted into the {@link Color JavaFX color range}
     *                  {@code [0, 1]}, its {@link ColorMode#multiplier} will be used to fit the rgb values into the
     *                  given range (if those are out of bounds) or just set to the {@link ColorMode#maxVal}.
     */
    Colors(Number gray, double alpha, ColorMode colorMode) {
        final double min = colorMode.getMinVal().doubleValue();
        final double max = colorMode.getMaxVal().doubleValue();
        
        if (gray.doubleValue() / ColorMode.RGB.getMaxVal().doubleValue() >= min && alpha >= 0) {
            if (gray.doubleValue() / ColorMode.RGB.getMaxVal().doubleValue() > max) {
                gray = colorMode.getMaxVal().doubleValue() / ColorMode.RGB.getMaxVal().doubleValue();
            }
            if (alpha > ALPHA_MAX) {
                alpha = 1;
            }
        }
        this.color = Color.gray(
                gray.doubleValue() / ColorMode.RGB.getMaxVal().doubleValue(),
                alpha
        );
    }
    
    /**
     * @see Colors#Colors(Number, Number, Number, double, ColorMode)
     */
    Colors(Number red, Number green, Number blue, ColorMode colorMode) {
        this(red, green, blue, 1, colorMode);
    }
    
    /**
     * Creates a color object. Depending on the color interpretation each component of the {@link #color} will be
     * processed accordingly and recalculated to fit into its given range.
     *
     * @param red       The parameter for the amount of red of the {@link #color}.
     * @param green     The parameter for the amount of green of the {@link #color}.
     * @param blue      The parameter for the amount of blue of the {@link #color}.
     * @param alpha     The parameter for the alpha or opacity of the {@link #color}.
     * @param colorMode Will determine how the given values are to be interpreted. <br>If it is to be interpreted as a RGB
     *                  color for instance, nothing will be readjusted and {@link Color#color(double, double, double,
     *                  double)} will be called. But if is to be fitted into the {@link Color JavaFX color range}
     *                  {@code [0, 1]}, its {@link ColorMode#multiplier} will be used to fit the rgb values into the
     *                  given range (if those are out of bounds) or just set to the {@link ColorMode#maxVal}.
     */
    Colors(Number red, Number green, Number blue, double alpha, ColorMode colorMode) {
        final double min = colorMode.getMinVal().doubleValue();
        final double max = colorMode.getMaxVal().doubleValue();
        
        this.red = red;
        this.green = green;
        this.blue = blue;
        
        if (red.doubleValue() >= min && green.doubleValue() >= min && blue.doubleValue() >= min && alpha >= 0) {
            if (red.doubleValue() > max) {
                this.red = colorMode.getMaxVal();
            }
            if (green.doubleValue() > max) {
                this.green = colorMode.getMaxVal();
            }
            if (blue.doubleValue() > max) {
                this.blue = colorMode.getMaxVal();
            }
            if (alpha > ALPHA_MAX) {
                alpha = 1;
            }
        }
        
        if (colorMode == ColorMode.RGB) {
            this.color = Color.rgb(
                    this.red.intValue(),
                    this.green.intValue(),
                    this.blue.intValue(),
                    alpha
            );
        } else if (colorMode == ColorMode.JFX_COL) {
            this.color = Color.color(
                    this.red.doubleValue(),
                    this.green.doubleValue(),
                    this.blue.doubleValue(),
                    alpha
            );
        }
    }
    
    
    //// GETTERS
    
    public Color brighter() {
        return this.color.brighter();
    }
    
    public Color darker() {
        return this.color.darker();
    }
    
    public Background getBackground() {
        return ElementBackgroundCreator.getBackground(this.color);
    }
    
    /**
     * @return Returns a {@link Color color object} which may be used for any view/frame.
     */
    public Color getColor(double alpha) {
        return Color.rgb(
                this.red.intValue(),
                this.green.intValue(),
                this.blue.intValue(),
                alpha
        );
    }
    
    /**
     * @return Returns a {@link Color color object} which may be used for any view/frame.
     */
    public Color getColor() {
        return color;
    }
    
    @Override
    public String toString() {
        return "rgb(" + red + ", " + green + ", " + blue + ")";
    }
    
    public enum ColorMode {
        
        RGB(0, 255, 255),
        JFX_COL(0, 1, 1.0 / 255.0);
        
        private Number multiplier;
        private Number maxVal;
        private Number minVal;
        
        ColorMode(Number min, Number max, Number mult) {
            this.minVal = min;
            this.maxVal = max;
            this.multiplier = mult;
        }
        
        public Number getMaxVal() {
            return maxVal;
        }
        
        public Number getMinVal() {
            return minVal;
        }
        
        public Number getRgbMult() {
            return multiplier;
        }
    }
    
    /**
     * Used to convert any {@link Color} or image into a fill to be used as backgrounds of
     * {@link Region regions}.
     */
    static abstract class ElementBackgroundCreator {
        
        public static Background createBackgroundImg(String path) {
            return createBackgroundImg(new Image(new File(path).toURI().toString()));
        }
        
        public static Background createBackgroundImg(String path, double w, double h) {
            return createBackgroundImg(new Image(new File(path).toURI().toString()), w, h);
        }
        
        public static Background createBackgroundImg(Image image) {
            return createBackgroundImg(image, 100, 100);
        }
        
        public static Background createBackgroundImg(Image image, double w, double h) {
            BackgroundSize backgroundSize = new BackgroundSize(w, h, true, true, true, false);
            BackgroundImage backgroundImage = new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    backgroundSize);
            return new Background(backgroundImage);
        }
        
        public static Background createBackgroundImg(byte[] img) {
            return createBackgroundImg(new Image(new ByteArrayInputStream(img)));
        }
        
        static Background getBackground(Color color) {
            return getBackground(color, color.getOpacity());
        }
        
        static Background getBackground(Color color, double alpha) {
            Background bg;
            Number r, g, b;
            r = color.getRed() * ColorMode.RGB.getMaxVal().intValue();
            g = color.getGreen() * ColorMode.RGB.getMaxVal().intValue();
            b = color.getBlue() * ColorMode.RGB.getMaxVal().intValue();
            BackgroundFill bf = new BackgroundFill(
                    Color.rgb(
                            r.intValue(),
                            g.intValue(),
                            b.intValue(),
                            alpha
                    ),
                    new CornerRadii(0),
                    new Insets(0)
            );
            
            bg = new Background(
                    bf
            );
            
            return bg;
        }
        
        static Background getBackground(Colors color, double alpha) {
            return getBackground(color.getColor(), alpha);
        }
        
        static Background getBackground(Colors color) {
            return getBackground(color.getColor());
        }
        
    }
    
}
