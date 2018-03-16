package model;

import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public abstract class Observable extends java.util.Observable {
    
    /**
     * @param observers An array of multiple observers.
     * @author Henock Arega
     * Allows to add multiple {@link Observer Observers} at once.
     */
    synchronized void addAllObserver(Observer observer, Observer... observers) {
        try {
            this.addObserver(observer);
            for (Observer o : observers) {
                this.addObserver(o);
            }
        } catch (NullPointerException ignored) {
        }
    }
    
}
