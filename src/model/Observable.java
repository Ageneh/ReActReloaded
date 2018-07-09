package model;

import java.util.Observer;

/**
 * @author Henock Arega
 * @author Michael Heide
 * @project ReActReloaded
 */
public abstract class Observable extends java.util.Observable {

//////////// METHODS
    
    /**
     * @param observers An array of multiple observers.
     * @author Henock Arega
     * Allows to add multiple {@link Observer Observers} at once.
     */
    public synchronized void addAllObserver(Observer observer, Observer... observers) {
        try {
            this.addObserver(observer);
            for (Observer o : observers) {
                this.addObserver(o);
            }
        } catch (NullPointerException ignored) {
        }
    }
    
    public synchronized void addAllObserver(Observer... observers) {
        try {
            for (Observer o : observers) {
                this.addObserver(o);
            }
        } catch (NullPointerException ignored) {
        }
    }
    
}
