import scenes.Controller;
import scenes.SettingScene;
import scenes.StartScene;

import java.util.Observer;

public class MainController extends Controller {
    
    private StartScene startScene;
    
    public MainController(Observer observer) {
        super();
        this.addObserver(observer);
        this.startScene = new StartScene(this);
    }
    
    public void init(){
        setChanged();
        notifyObservers(this.startScene);
    }
    
}
