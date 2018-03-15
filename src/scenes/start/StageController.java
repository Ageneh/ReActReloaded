package scenes.start;

import javafx.scene.Scene;
import scenes.BackButton;
import scenes.Controller;
import scenes.ObservableScene;

import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

/**
 * Controls all scenes and will notify the main stage when another scene is
 * requested.
 */
public class StageController extends Controller {

    private SceneContainer sceneContainer;

    public StageController(Observer o){
        this(new StartScene(), o);
    }

    public StageController(ObservableScene startScene, Observer o){
        this.sceneContainer = new SceneContainer(startScene);
        this.addObserver(o);
    }

    public Scene getCurrentScene(){
        return this.sceneContainer.peek().getScene();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o != null){
            if(o instanceof ObservableScene) {
                if(arg instanceof BackButton){
                    // if previous scene is requested notify observers
                    setChanged();
                    try {
                        notifyObservers(this.sceneContainer.pop());
                    }
                    catch (AlreadyShowingException e){
                        // do not notify observer but uncheck changed flag
                        clearChanged();
                    }
                }
            }
        }
    }

    private class SceneContainer extends Stack<ObservableScene> {

        private final int MIN = 0;

        private int pos;

        private SceneContainer() {
            this.pos = -1;
        }

        private SceneContainer(ObservableScene startScene) {
            this();
            this.push(startScene);
        }

        @Override
        public ObservableScene push(ObservableScene item) {
            this.pos++;
            return super.push(item);
        }

        @Override
        public synchronized ObservableScene pop() {
            if (super.isEmpty()) {
                return null;
            }
            if (pos == MIN) {
                throw new AlreadyShowingException("Current scene is already first.");
            } else {
                pos--;
                return super.pop();
            }
        }

        @Override
        public synchronized ObservableScene peek() {
            if(super.isEmpty()) return null;
            else return super.peek();
        }
    }

    private class AlreadyShowingException extends RuntimeException{
        private AlreadyShowingException(){
            super();
        }

        private AlreadyShowingException(String msg){
            super(msg);
        }
    }

}
