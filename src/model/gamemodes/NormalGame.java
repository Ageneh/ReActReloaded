package model.gamemodes;

import functions.ANSI;
import model.Game;
import model.MusicPlayer;
import model.Song;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * @author Henock Arega
 * @author ${USER
 * @project ReActReloaded
 *
 * In the {@link GameMode#NORMAL} game mode the song will first play for the given amount
 * of seconds and its value will be increased every time the player clicks on replay. With each
 * click on replay the amount of possible points reachable decreases.
 */
public class NormalGame extends Game {

    public NormalGame(Observer o, Observer ... observers) {
        super(GameMode.NORMAL, o, observers);
    }

    public NormalGame(String name, Observer o, Observer ... observers) {
        super(GameMode.NORMAL, name, o, observers);
    }
    
    private void evalAnswer(Object arg){
    
    }
    
    public void test(){
        Scanner scan = new Scanner(System.in);
        String input;
        Song[] answers;
        boolean wrongAnswer;
        while(super.getGameStatus().mode() != GameMode.GAME_OVER){
            wrongAnswer = true;
            while (wrongAnswer) {
                input = scan.nextLine();
                switch (input) {
                    case "s":
                        super.start();
                        wrongAnswer = false;
                        break;
                    case "r":
                        super.replay();
                        wrongAnswer = false;
                        break;
                    case "q":
                        this.close(Code.GAME_OVER);
                        wrongAnswer = false;
                        break;
                    default:
                        try {
                            // if number
                            int input_num = Integer.parseInt(input);
                            answer(input_num);
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
    public boolean answer(Song answer) {
        boolean res = super.answer(answer);
        if(res){
            super.pause();
            super.addPoints();
            super.notifyOfGameStatus();
            super.next();
        } else{
            // TODO show screen overlay where name can be entered and one choses to go to home, play again etc.
            super.subtractPoints();
            super.close(Code.GAME_OVER);
            ANSI.RED.println("Wrong answer.");
            super.notifyOfGameStatus();
        }
        return res;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof MusicPlayer){
            this.evalAnswer(arg);
        }
    }

}
