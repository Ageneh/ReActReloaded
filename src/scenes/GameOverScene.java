package scenes;

import design.Labels;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.User;
import model.isGame;
import scenes.elements.ReButton;
import scenes.gamemodes.GameScene;

import java.util.ArrayList;
import java.util.Observer;

/**
 * @author Henock Arega
 */
public class GameOverScene extends BaseReactScene {
    
    private Label title;
    private HBox buttons;
    private ReButton home;
    private ReButton ranking;
    
    public GameOverScene(GameScene o) {
        super("Game Over", o);
        final String s = "%s hat %d Punkte erreicht.\n";
        String fin = "";
        for(User u : (ArrayList<User>) o.getUsers()){
            fin += String.format(s, u.getName(), u.getPoints());
        }
        setSubTitle(fin);
        this.init();
    }
    
    private void init(){
        removeBackButton();
        GridPane gridPane = getGrid();
        
        this.title = Labels.MASSIVE.getLabel("Game Over");
       
        this.home = new ReButton("Home");
        home.setOnAction(event -> {
            setChanged();
            notifyObservers(isGame.Action.SHOW_HOME);
        });
        this.ranking = new ReButton("Rangliste");
        ranking.setOnAction(event -> {
            setChanged();
            notifyObservers(isGame.Action.SHOW_RANKING);
        });
        
        this.buttons = new HBox(home, ranking);
        
        gridPane.add(buttons, 0, 0);
    }
}
