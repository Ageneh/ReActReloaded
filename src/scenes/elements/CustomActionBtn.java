package scenes.elements;

import design.Dimension;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class CustomActionBtn extends HBox {
    private String name;
    private HBox box;



    public CustomActionBtn(String name){
        this.name = name;
        this.box = new HBox(10);
        this.box.setMinHeight(Dimension.CSTMENU_HEIGHT.intValue());
        this.box.setMaxHeight(Dimension.CSTMENU_HEIGHT.intValue());
        this.box.setMinWidth(Dimension.CSTMENU_WIDTH.intValue());
        this.box.setMaxWidth(Dimension.CSTMENU_WIDTH.intValue());
        this.box.setStyle("-fx-background-color: #FFB312");
        this.box.setAlignment(Pos.CENTER);


        Label label = new Label(this.name);
        label.setTextFill(Color.WHITE);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(new Font(20));
        label.setPadding(new Insets(13,20,15,10));


        box.getChildren().addAll(label);
    }

    public HBox getBox() {
        return box;
    }
}
