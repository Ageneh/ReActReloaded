package scenes;

import design.Labels;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import scenes.elements.ReButton;

import java.util.Observer;

/**
 * @author Henock Arega
 */
public class GameOverScene extends ObservableScene {
    
    private Label title;
    private HBox buttons;
    private ReButton home;
    private ReButton replay;
    
    public GameOverScene(Observer o) {
        super(o);
        this.init();
    }
    
    public GameOverScene() {
        this.init();
    }
    
    private void init(){
        this.title = Labels.MASSIVE.getLabel("Game Over");
       
        this.home = new ReButton("Home");
        this.replay = new ReButton("Play again");
        
        this.buttons = new HBox(home, replay);
        root.getChildren().addAll(new AnchorPane(this.buttons));
    }
}
