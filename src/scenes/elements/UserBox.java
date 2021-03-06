package scenes.elements;

import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.User;
import scenes.gamemodes.GameScene;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 * @author Henock Arega
 * @author Michael Heide
 */
public class UserBox extends VBox {
    
    private Label points;
    private Label username;
    private int pointsProp;
    private SimpleIntegerProperty pointsIntValue;
    private SimpleStringProperty nameValue;

    public UserBox(String username) {
        this.pointsProp = 0;
        this.pointsIntValue = new SimpleIntegerProperty(pointsProp);
        this.username = GameScene.LABEL_STYLE.getLabel(username);
        this.points = GameScene.LABEL_STYLE.getLabel(0);
        this.nameValue = new SimpleStringProperty(username);

        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);
        this.setPrefWidth(150);

        this.setMinHeight(75);

        this.getChildren().add(this.username);
        this.getChildren().add(points);
    }
    
    public void addPoints(int points) {
        this.pointsProp += points;
        this.pointsIntValue.set(pointsProp);
        this.points.setText(String.valueOf(pointsProp));
    }

    public String getName() {
        return username.getText();
    }

    public String getPoints() {
        return this.points.getText();
    }

    public void setUsername(String username){
        this.username.setText(username);
        this.nameValue.set(username);
    }

    public void setPoints(int points) {
        this.pointsProp = points;
        this.pointsIntValue.set(points);
        this.points.setText(String.valueOf(pointsProp));
    }

    public int getIntPoint(){
        return this.pointsProp;
    }

    public SimpleIntegerProperty getPointsIntValue(){
        return this.pointsIntValue;
    }

    public SimpleStringProperty getNameValue(){
        return this.nameValue;
    }
}
