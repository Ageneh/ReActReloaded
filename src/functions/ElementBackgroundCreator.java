package functions;

import design.Colors;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.ByteArrayInputStream;
import java.io.File;

public abstract class ElementBackgroundCreator {

    public static Background getBackground(Color color){

        /*************************************************************
         *
         *
         *
         *
         * public String call() throws Exception {
         return String.format("-fx-body-color: rgb(%d, %d, %d);",
         (int) (256*color.get().getRed()),
         (int) (256*color.get().getGreen()),
         (int) (256*color.get().getBlue()));
         }
         *
         *
         *
         **************************************************************/

        return getBackground(color, color.getOpacity());
    }

    public static Background getBackground(Color color, double alpha){
        Background bg;
        Number r, g, b;
        r = color.getRed() * Colors.ColorMode.RGB.getMaxVal().intValue();
        g = color.getGreen() * Colors.ColorMode.RGB.getMaxVal().intValue();
        b = color.getBlue() * Colors.ColorMode.RGB.getMaxVal().intValue();
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

    public static Background getBackground(Colors color, double alpha){
        return getBackground(color.getColor(), alpha);
    }

    public static Background getBackground(Colors color){
        return getBackground(color.getColor());
    }

    public static Background createBackgroundImg(String path){
        return createBackgroundImg(new Image(new File(path).toURI().toString()));
    }

    public static Background createBackgroundImg(String path, double w, double h){
        return createBackgroundImg(new Image(new File(path).toURI().toString()), w, h);
    }

    public static Background createBackgroundImg(Image image){
        return createBackgroundImg(image, 100, 100);
    }

    public static Background createBackgroundImg(Image image, double w, double h){
        BackgroundSize backgroundSize = new BackgroundSize(w, h, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize);
        return new Background(backgroundImage);
    }

    public static Background createBackgroundImg(byte[] img){
        return createBackgroundImg(new Image(new ByteArrayInputStream(img)));
    }

}