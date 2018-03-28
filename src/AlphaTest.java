import functions.ANSI;
import model.GameMode;
import model.gamemodes.NormalGameController;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class AlphaTest implements Observer {
    
    private NormalGameController ng;
    
    public static void main(String[] args) {
        new AlphaTest();
    }
    
    public AlphaTest() {
        ng = new NormalGameController(this);
        ng.test();
    }
    
    //////////// OVERRIDES
    @Override
    public void update(Observable o, Object arg) {
        
        if(o.getClass().equals(ng.getClass())){
            GameMode.GameStatus gs = (GameMode.GameStatus) arg;
            ANSI.BLUE.println(gs.toString());
        }
    }
}
