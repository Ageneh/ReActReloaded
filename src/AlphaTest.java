import functions.ANSI;
import model.Game;
import model.gamemodes.NormalGame;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class AlphaTest implements Observer {
    
    private NormalGame ng;
    
    public static void main(String[] args) {
        new AlphaTest();
    }
    
    public AlphaTest() {
        ng = new NormalGame(this);
        ng.test();
    }
    
    @Override
    public void update(Observable o, Object arg) {
    
        if(o.getClass().equals(ng.getClass())){
            Game.GameStatus gs = (Game.GameStatus) arg;
            ANSI.BLUE.println(gs.toString());
        }
    }
}
