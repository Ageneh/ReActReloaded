package scenes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import model.Observable;

import java.util.Observer;

/**
 * All scenes have to extend this class so that they have all necessary methods and values
 * and can be treated/used correctly.
 */
public abstract class ObservableScene extends Observable implements Observer {
    
    public final static SimpleIntegerProperty gen_width = new SimpleIntegerProperty(Sizes.WIDTH.getInt());
    public final static SimpleIntegerProperty gen_height = new SimpleIntegerProperty(Sizes.HEIGHT.getInt());
    private StackPane root;
    private Scene scene;
    
    protected ObservableScene() {
        this.root = new StackPane();
        this.root.setMinSize(gen_width.doubleValue(), gen_height.doubleValue());
        this.root.setPrefSize(gen_width.doubleValue(), gen_height.doubleValue());
        this.root.setMaxSize(gen_width.doubleValue(), gen_height.doubleValue());
        this.scene = new Scene(this.root);
        
        this.scene.setRoot(this.root);
    }
    
    public StackPane getRoot() {
        return root;
    }
    
    public Scene getScene() {
        return this.scene;
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
