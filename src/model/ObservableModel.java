package model;

import java.util.Observer;

/**
 * An abstract class as a template for observable classes which are part of the {@link model} package.
 *
 * @author Henock Arega
 * @project ReActReloaded
 */
public abstract class ObservableModel extends Observable implements Close {
    
    public ObservableModel() {
    }
    
    //////////// OVERRIDES
    @Override
    public synchronized void addObserver(Observer o) {
        try {
            super.addObserver(o);
        } catch (NullPointerException ignored) {
        }
    }
    
    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }
    
    @Override
    public synchronized void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
    @Override
    public synchronized void addAllObserver(Observer observer, Observer... observers) {
        super.addAllObserver(observer, observers);
    }
}
