package model;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 *
 * An abstract class as a template for observable classes which are part of the {@link model} package.
 */
abstract class ObservableModel extends Observable implements Close {

    private SongLibrary songLibrary;

    ObservableModel(){
        this.songLibrary = new SongLibrary();
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }

    @Override
    public synchronized void addObserver(Observer o) {
        try {
            super.addObserver(o);
        }
        catch (NullPointerException ignored){}
    }

    /**
     * @author Henock Arega
     * Allows to add multiple {@link Observer Observers} at once.
     * @param observers An array of multiple observers.
     */
    synchronized void addAllObserver(Observer observer, Observer ... observers) {
        try {
            this.addObserver(observer);
            for(Observer o : observers){
                this.addObserver(o);
            }
        }
        catch (NullPointerException ignored){}
    }

    public SongLibrary getSongLibrary() {
        return songLibrary;
    }
}
