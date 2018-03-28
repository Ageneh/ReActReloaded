package model.gamemodes;

import functions.ANSI;
import model.GameMode;
import model.Song;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class NormalGameController extends GameController {
    
    private NormalGame ng;
    
    public NormalGameController(Observer o, Observer... observers) {
        super(o, observers);
        ng = new NormalGame(this);
    }
    
    public NormalGameController(String name, Observer o, Observer... observers) {
        super(o, observers);
        ng = new NormalGame(name, this);
    }
    
    //////////// METHODS
    public void startGame() {
        ng.start();
    }
    
    public void testGUI(Song answer) {
        ANSI.BLUE.println("=============");
        ANSI.BLUE.println("========  G O");
        ANSI.BLUE.println("=============");
        
        if (ng.getGameStatus().mode() != GameMode.GameModeProp.GAME_OVER) {
            ng.answer(answer);
        }
        ANSI.RED.println("GAME OVER!");
    }
    
    //////////// OVERRIDES
    @Override
    public void test() {
        Scanner scan = new Scanner(System.in);
        String input;
        Song[] answers;
        boolean wrongAnswer;
        
        String[][] instructions = new String[][]{
                {"Start", "s"},
                {"Replay", "r"},
                {"Quit", "q"},};
        
        ANSI.BLUE.println("Instructions:");
        for (int y = 0; y < instructions.length; y++) {
            ANSI.BLUE.println(instructions[y][0] + "\t:\t" + instructions[y][1]);
        }
        ANSI.BLUE.println("=============");
        ANSI.BLUE.println("========  G O");
        ANSI.BLUE.println("=============");
        
        while (ng.getGameStatus().mode() != GameMode.GameModeProp.GAME_OVER) {
            wrongAnswer = true;
            while (wrongAnswer) {
                ANSI.BLUE.print(">> ");
                input = scan.nextLine();
                switch (input) {
                    case "s":
                        ng.start();
                        wrongAnswer = false;
                        break;
                    case "r":
                        ng.replay();
                        wrongAnswer = false;
                        break;
                    case "q":
                        this.close(Code.GAME_OVER);
                        wrongAnswer = false;
                        break;
                    default:
                        try {
                            // if number
                            if (input.isEmpty()) ng.replay();
                            int input_num = Integer.parseInt(input);
                            ng.answer(input_num);
                            wrongAnswer = false;
                        } catch (NumberFormatException e) {
                            wrongAnswer = true;
                        }
                }
            }
        }
        ANSI.RED.println("GAME OVER!");
    }
    
    @Override
    public void replay() {
        ng.replay();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
    
    @Override
    public void close(Code code) {
        ng.close(code);
    }
    
}
