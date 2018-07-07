package scenes.start;

import javafx.scene.Scene;
import scenes.Controller;
import scenes.ObservableScene;
import scenes.elements.BackButton;

import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

/**
 * Controls all scenes and will notify the main stage when another scene is
 * requested.
 */
public class StageController extends Controller {
    
    private static final SceneContainer sceneContainer = new SceneContainer();
    private static Observer o;
    private ObservableScene current;
    
    public StageController() {
    }
    
    public StageController(Observer o) {
        this(new StartScene1(), o);
    }
    
    public StageController(ObservableScene startScene, Observer observer) {
        startScene.addObserver(this);
        sceneContainer.push(startScene);
        StageController.o = observer;
        this.addObserver(observer);
    }
    
    public static ObservableScene getPrevious() {
        return sceneContainer.pop();
    }
    
    public Scene getCurrentScene() {
        return sceneContainer.peek().getScene();
    }
    
    public void newScene(ObservableScene gameScene) {
        setChanged();
        gameScene.addObserver(this);
        sceneContainer.push(this.current);
        this.current = gameScene;
        notifyObservers(gameScene);
    }
    
    public void pushScene(ObservableScene scene) {
        StageController.sceneContainer.push(scene);
    }
    
    public void toPrevious() {
        setChanged();
        notifyObservers(StageController.getPrevious());
    }
    
    @Override
    public void update(Observable o, Object arg) {
        try {
            if (o instanceof ObservableScene) {
                try {
                    setChanged();
                    if (arg instanceof BackButton) {
                        // if previous scene is requested notify observers
                        try {
                            notifyObservers(this.sceneContainer.pop());
                        } catch (AlreadyShowingException e) {
                            // do not notify observer but uncheck changed flag
                            clearChanged();
                        }
                    } else {
                        this.newScene((ObservableScene) arg);
                    }
                } catch (NullPointerException e) {
                    // arg is null
                }
            }
        } catch (NullPointerException e) {
            // o is null
        }
    }
    
    private static class SceneContainer extends Stack<ObservableScene> {
        
        private final int MIN = 0;
        
        private int pos;
        
        private SceneContainer() {
            this.pos = - 1;
        }
        
        /**
         * @param rootScene the first scene which will be shown on program launch. Cannot be popped from
         *                  {@link SceneContainer}.
         * @see #pop()
         */
        private SceneContainer(ObservableScene rootScene) {
            this();
            this.push(rootScene);
        }
        
        @Override
        public ObservableScene push(ObservableScene item) {
            this.pos++;
            return super.push(item);
        }
        
        @Override
        public synchronized ObservableScene pop() {
            if (this.isEmpty()) {
                return null;
            }
            if (pos == MIN) {
                return this.peek();
            } else {
                pos--;
                return super.pop();
            }
        }
        
        @Override
        public synchronized ObservableScene peek() {
            if (super.isEmpty()) return null;
            else return super.peek();
        }
    }
    
    private class AlreadyShowingException extends RuntimeException {
        
        private AlreadyShowingException() {
            super();
        }
        
        private AlreadyShowingException(String msg) {
            super(msg);
        }
    }
    
}
