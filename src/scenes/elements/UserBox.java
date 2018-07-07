package scenes.elements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.User;
import scenes.gamemodes.GameScene;

/**
 * @author Henock Arega
 */
public class UserBox extends VBox {
    
    protected Label points;
    protected Label username;
    protected User user;
    
    public UserBox(User user) {
        this.user = user;
        this.username = GameScene.LABEL_STYLE.getLabel(user.getName());
        this.points = GameScene.LABEL_STYLE.getLabel(0);
        
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setPrefWidth(150);
        
        this.getChildren().add(username);
        this.getChildren().add(points);
    }
    
    public void setPoints(int points) {
        this.points.setText(String.valueOf(points));
    }
    
    public User getUser() {
        return user;
    }
}
