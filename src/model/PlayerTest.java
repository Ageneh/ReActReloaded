package model;

import functions.ANSI;
import model.gamemodes.NormalGame;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public class PlayerTest implements Observer {
    
    NormalGame ng;
    
    public PlayerTest() {
        synchronized (Thread.currentThread()) {
            ng = new NormalGame("TestUser", this);
            boolean running = true;
            Scanner scan = new Scanner(System.in);
            String str;
            do {
                str = scan.nextLine();
                switch (str) {
                    case "s":
                        ng.start();
                        break;
                    case "a":
                        // BUGFIX needed
                        ng.answer(ng.song());
                        break;
                    case "w":
                    case "wrong":
                        ng.answer(new Song("/Users/HxA/Music/iTunes/iTunes Media/Music/Various Artists/Winter In Jakarta/01 Like - Glory 1.mp3"));
                        break;
                    case "r":
                        ng.replay();
                        break;
                    case "q":
                        ng.close(Close.Code.GAME_OVER);
                        running = false;
                        break;
                }
            } while (running);
        }
    }
    
    private void test1() {
        SongLibrary lib = new SongLibrary();
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        
        System.out.println("END");
        lib.close(Close.Code.CLOSE);
        musicPlayer.close(Close.Code.CLOSE);
    }
    
    public static void main(String[] args) {
        new PlayerTest();
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
        
        if (o.getClass().equals(ng.getClass())) {
            Game.GameStatus gs = (Game.GameStatus) arg;
            
            ANSI.BLUE.println(gs.toString());
            
            if (gs.mode() == Game.GameMode.GAME_OVER) {
                ANSI.MAGENTA.println("GAME ENDED");
                ANSI.MAGENTA.println("GAME ENDED");
                ANSI.MAGENTA.println("GAME ENDED");
                ANSI.MAGENTA.println("GAME ENDED");
                System.exit(Close.Code.CLOSE.code());
            }
        }
        
    }
}
