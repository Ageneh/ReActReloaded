package scenes.elements;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.User;
import scenes.gamemodes.GameScene;

/**
 * @author Henock Arega
 */
public class UserBox extends VBox {
    
    private Label points;
    private Label username;
    private int pointsProp;
    
    public UserBox(String username) {
        this.pointsProp = 0;
        
        this.username = GameScene.LABEL_STYLE.getLabel(username);
        this.points = GameScene.LABEL_STYLE.getLabel(0);
        
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);
        this.setPrefWidth(150);
        
        this.getChildren().add(this.username);
        this.getChildren().add(points);
    }
    
    public void addPoints(int points) {
        this.pointsProp += points;
        this.points.setText(String.valueOf(pointsProp));
    }

    public String getName() {
        return username.getText();
    }

    public String getPoints() {
        return points.getText();
    }

    public void setPoints(int points) {
        this.pointsProp = points;
        this.points.setText(String.valueOf(pointsProp));
    }
}
