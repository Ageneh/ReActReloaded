package scenes;

import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import model.Observable;

import java.util.Observer;

/**
 * All scenes have to extend this class so that they have all necessary methods and values
 * and can be treated/used correctly.
 */
public abstract class ObservableScene extends Observable implements Observer {
    
    public final static SimpleIntegerProperty gen_width = new SimpleIntegerProperty(Sizes.WIDTH.getInt());
    public final static SimpleIntegerProperty gen_height = new SimpleIntegerProperty(Sizes.HEIGHT.getInt());
    protected StackPane root;
    protected Scene scene;
    
    private ObservableScene previousScene;
    
    protected ObservableScene(Observer o) {
        this();
        this.addObserver(o);
//        this.previousScene = o;
    }
    
    protected ObservableScene(ObservableScene observableScene, Observer o) {
        this();
        this.setPreviousScene(observableScene);
        this.addObserver(o);
//        this.previousScene = o;
    }
    
    protected ObservableScene(ObservableScene observableScene) {
        this();
        this.setPreviousScene(observableScene);
        this.addObserver(observableScene);
//        this.previousScene = o;
    }
    
    protected ObservableScene() {
        this.root = new StackPane();
        this.root.setMinSize(gen_width.doubleValue(), gen_height.doubleValue());
        this.root.setPrefSize(gen_width.doubleValue(), gen_height.doubleValue());
        this.root.setMaxSize(gen_width.doubleValue(), gen_height.doubleValue());
        this.scene = new Scene(this.root);
    }
    
    public StackPane getRoot() {
        return root;
    }
    
    public Scene getScene() {
        return this.scene;
    }
    
    protected Stage getStage(){
        Window window = this.getScene().getWindow();
        return (Stage) window;
    }
    
    public void setPreviousScene(ObservableScene previousScene) {
        this.previousScene = previousScene;
    }
    
    public ObservableScene getPreviousScene(){
        return this.previousScene;
    }
    
    protected void fadeInNode(Node node) {
        this.fadeNode(node, node.getOpacity(), 1.0);
    }
    
    protected void fadeOutNode(Node node) {
        this.fadeNode(node, node.getOpacity(), 0.0);
    }
    
    protected void fadeNode(Node node, double from, double to) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), node);
        fadeTransition.setByValue(0.1);
        fadeTransition.setFromValue(from);
        fadeTransition.setToValue(to);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }
    
    @Override
    public void update(java.util.Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
    
    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }
    
    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }
    
    public enum Sizes {
        
        WIDTH(1275),
        HEIGHT(850);
        
        private int val;
        
        Sizes(int val) {
            this.val = val;
        }
        
        public int getInt() {
            return val;
        }
    }
    
}
