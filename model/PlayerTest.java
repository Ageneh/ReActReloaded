package model;

import model.gamemodes.NormalGame;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class PlayerTest implements Observer {
    
    public static void main(String[] args) {
         new PlayerTest();
    }
    
        SongLibrary lib;
        NormalGame ng;
    public PlayerTest(){
        synchronized (Thread.currentThread()) {
            ng = new NormalGame("Henock", this);
            boolean running = true;
            Scanner scan = new Scanner(System.in);
            String str;
            while(running){
                str = scan.nextLine();
                switch (str){
                    case "s":
                        ng.start();
                        break;
                    case "a":
                        ng.answer(ng.song());
                        break;
                    case "r":
                        ng.replay();
                        break;
                    case "q":
                        ng.end();
                        break;
                }
            }
        }
    }
    
    private void test1(){
        MusicPlayer musicPlayer = new MusicPlayer(this);
        lib = new SongLibrary();
        Playlist playlist = new Playlist(lib.getSongs());
        musicPlayer.play(playlist.getNext(), 10000);
    
        Scanner scan = new Scanner(System.in);
        String s;
        try {
            while (true) {
                s = scan.nextLine();
                if (s.contains("s")) {
                    musicPlayer.play(playlist.getNext(), 10000);
                } else {
                    break;
                }
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    
        System.out.println("END");
        lib.close(Close.Code.CLOSE);
        musicPlayer.close(Close.Code.CLOSE);
    }
    
    @Override
    public void update(Observable o, Object arg) {
//        PlayerResult obs = ((PlayerResult) arg);
//
//        System.out.println();
//        System.out.println(obs.getTimeMillis());
//        System.out.println(obs.isAnswered());
//
//        if(!obs.isAnswered()){
//            ANSI.RED.println("WAITED TOOOOOOO LONG");
//        }
//
//        lib.close(Close.Code.CLOSE);
        
        if(o.getClass().equals(ng.getClass())){
            Game.GameStatus gs = (Game.GameStatus) arg;
    
            System.out.println(gs.points());
            System.out.println(gs.time());
            System.out.println(gs.user().getName());
            
            if(gs.isAnswered() && gs.correctAnswer()){
                ng.next();
            }
        }
        
    }
}
