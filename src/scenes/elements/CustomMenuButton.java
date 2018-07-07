package scenes.elements;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import design.Dimension;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class CustomMenuButton extends HBox {
    private String name;
    private HBox box;



    public CustomMenuButton(String name){
        this.name = name;
        this.box = new HBox(10);
        this.box.setMinHeight(Dimension.CSTMENU_HEIGHT.intValue());
        this.box.setMaxHeight(Dimension.CSTMENU_HEIGHT.intValue());
        this.box.setMinWidth(Dimension.CSTMENU_WIDTH.intValue());
        this.box.setMaxWidth(Dimension.CSTMENU_WIDTH.intValue());
        this.box.setStyle("-fx-background-color: #ffffff");


        HBox innerbox = new HBox();
        innerbox.setStyle("-fx-background-color: #FFB312");
        innerbox.setMaxWidth(Dimension.CSTMENU_HEIGHT.intValue());
        innerbox.setMinWidth(Dimension.CSTMENU_HEIGHT.intValue());
        innerbox.setMaxHeight(Dimension.CSTMENU_HEIGHT.intValue());
        innerbox.setMinHeight(Dimension.CSTMENU_HEIGHT.intValue());

        Label label = new Label(this.name);
        label.setTextFill(Color.BLACK);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(new Font(20));
        label.setPadding(new Insets(13,20,15,10));


        box.getChildren().addAll(innerbox,label);
    }

    public HBox getBox() {
        return box;
    }
}
