package scenes.elements;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CustomMenuButton extends HBox {
    private String name;
    private HBox box;

    public CustomMenuButton(String name){
        this.name = name;
        this.box = new HBox(10);

        HBox innerbox = new HBox();

        Label label = new Label(this.name);

        box.getChildren().addAll(innerbox,label);
    }

    public HBox getBox() {
        return box;
    }
}
