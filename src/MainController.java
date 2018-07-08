import model.Close;
import model.Ranking;
import model.isGame;
import scenes.*;

import java.util.Observable;
import java.util.Observer;

public class MainController extends Controller {
    
    private StartScene startScene;
    private Ranking ranking;
    private RanglistScene rankingScene;
    
    public MainController(Observer observer) {
        super();
        this.addObserver(observer);
        this.startScene = new StartScene(this);
        this.ranking = new Ranking();
    }
    
    public void init(){
        setChanged();
        notifyObservers(this.startScene);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        try {
            if(arg instanceof ObservableScene){
                setChanged();
                notifyObservers(arg);
            }
            else if (arg instanceof isGame.Action) {
                isGame.Action action = (isGame.Action) arg;
                switch (action) {
                    case SHOW_HOME:
                        setChanged();
                        notifyObservers(this.startScene);
                        break;
                    case CURRENT_SONG:
                        break;
                    case ANSWERS:
                        break;
                    case NEW_MULTIPLIER:
                        break;
                    case ANSWER_CORRECT:
                        break;
                    case ANSWER_INCORRECT:
                        break;
                    case ANSWER:
                        break;
                    case LIFECOUNT:
                        break;
                    case ACTIVE_USER:
                        break;
                    case RANK:
                        ranking.add(action.getVal());break;
                    case SHOW_RANKING:
                        setChanged();
                        notifyObservers(new RanglistScene(this.ranking, startScene));
                        break;
                    case POINTS:
                        break;
                    case REPLAY:
                        break;
                }
            }
        }
        catch (NullPointerException e){
        
        }
    }
}
