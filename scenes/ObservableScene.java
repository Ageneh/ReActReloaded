package scenes;

import design.Colors;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

import java.util.Observable;
import java.util.Observer;

/**
 * All scenes have to extend this class so that they have all necessary methods and values
 * and can be treated/used correctly.
 */
public abstract class ObservableScene<T extends Region> extends Observable implements Observer {

    protected static SimpleIntegerProperty gen_width;
    protected static SimpleIntegerProperty gen_height;
    private T root;
    private Scene scene;

    protected ObservableScene(T root){
        gen_width = new SimpleIntegerProperty(Sizes.WIDTH.getInt());
        gen_height = new SimpleIntegerProperty(Sizes.HEIGHT.getInt());
        this.root = root;
        this.scene = new Scene(this.root);
        this.root.minWidthProperty().bind(gen_width);
        this.root.prefWidthProperty().bind(gen_width);
        this.root.maxWidthProperty().bind(gen_width);
        this.root.minHeightProperty().bind(gen_height);
        this.root.prefHeightProperty().bind(gen_height);
        this.root.maxHeightProperty().bind(gen_height);
    }

    protected ObservableScene(){
        gen_width = new SimpleIntegerProperty(Sizes.WIDTH.getInt());
        gen_height = new SimpleIntegerProperty(Sizes.HEIGHT.getInt());
        this.root = (T)(new Region());
        this.root.minWidthProperty().bind(gen_width);
        this.root.prefWidthProperty().bind(gen_width);
        this.root.maxWidthProperty().bind(gen_width);
        this.root.minHeightProperty().bind(gen_height);
        this.root.prefHeightProperty().bind(gen_height);
        this.root.maxHeightProperty().bind(gen_height);
        this.scene = new Scene(this.root);
    }

    public Scene getScene() {
        return this.scene;
    }

    public T getRoot(){
        return this.root;
    }

    /**
     * Changes the background color of the {@link #root} pane.
     * @param color A color which has to be defined in {@link Colors}
     */
    public void setBackground(Colors color){
        this.root.setBackground(color.getBackground());
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }

    private enum  Sizes{

        WIDTH(750),
        HEIGHT(500);

        private int val;

        Sizes(int val){
            this.val = val;
        }

        int getInt() {
            return val;
        }
    }

}
