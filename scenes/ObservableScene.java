package scenes;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.Observable;
import java.util.Observer;

/**
 * All scenes have to extend this class so that they have all necessary methods and values
 * and can be treated/used correctly.
 */
public abstract class ObservableScene extends Observable implements Observer {

    protected static SimpleIntegerProperty gen_width;
    protected static SimpleIntegerProperty gen_height;

    protected ObservableScene(){
        gen_width = new SimpleIntegerProperty(Sizes.WIDTH.getInt());
        gen_height = new SimpleIntegerProperty(Sizes.HEIGHT.getInt());
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
