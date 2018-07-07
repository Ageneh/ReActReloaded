package scenes.gamemodes;

import design.Colors;
import design.Labels;
import functions.ElementBackgroundCreator;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import model.Close;
import model.User;
import model.isGame;
import scenes.ObservableScene;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public abstract class GameScene extends ObservableScene implements Observer, Close {
    
    private GameBackground background;
    private Label points;
    private Label username;
    private Label multi;
    private Label round;
    private User user;
    private HBox top;
    private String fxmlPath;
    
    GameScene(String fxmlPath, Observer... observers) {
        super();
        this.fxmlPath = fxmlPath;
        this.background = new GameBackground();
        addAllObserver(observers);
        
        Labels label = Labels.M_SMALL;
        this.points = label.getLabel(0);
        this.username = label.getLabel("Player");
        this.multi = label.getLabel("1x");
        this.round = label.getLabel("1");
        
        this.top = new HBox();
        top.getChildren().addAll(username, points, multi, round);
        top.setSpacing(20);
        top.setAlignment(Pos.CENTER);
        this.background.root.setTop(this.top);
        getRoot().getChildren().add(this.background.root);
    }
    
    public abstract void start();
    
    public BorderPane getBackground() {
        return background.root;
    }
    
    public String getFxmlPath() {
        return fxmlPath;
    }
    
    protected void addLayer(Node node) {
        this.addLayers(node);
    }
    
    protected void addLayers(Node... node) {
        if (node == null) return;
        for (Node n : node) {
            if (n == null) continue;
            this.background.bottom.getChildren().add(n);
        }
    }
    
    private void setUser(User user) {
        if (user != null) return;
        
        this.user = user;
        
        this.points.setText("0");
        this.username.setText(this.user.getName());
        this.multi.setText("1x");
        this.round.setText("1");
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
        if (arg instanceof isGame.Action) {
            isGame.Action action = (isGame.Action) arg;
            switch (action) {
                case POINTS:
                    this.points.setText(action.getVal().toString());
                    break;
                case NEW_MULTIPLIER:
                    break;
                case ANSWER:
                    break;
            }
        }
    }
    
    private class GameBackground {
    
        private BorderPane root;
        private StackPane bottom;
        private Scene scene;
    
        private GameBackground() {
            this.bottom = new StackPane();
            this.root = new BorderPane();
            this.root.setCenter(bottom);
            this.root.setBackground(ElementBackgroundCreator.getBackground(Colors.BASE_BG));
            this.scene = new Scene(this.root);
        }
        
    }
}
