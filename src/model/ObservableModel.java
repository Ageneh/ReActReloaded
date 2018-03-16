package model;

import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 * <p>
 * An abstract class as a template for observable classes which are part of the {@link model} package.
 */
abstract class ObservableModel extends Observable implements Close {
    
    ObservableModel() {
    }
    
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
        super.notifyObservers(arg);
    }
    
    @Override
    synchronized void addAllObserver(Observer observer, Observer... observers) {
        super.addAllObserver(observer, observers);
    }
}
