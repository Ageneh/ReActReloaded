package model;

import functions.ANSI;

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
    
    public PlayerTest(){
    
        MusicPlayer musicPlayer = new MusicPlayer(this);
        SongLibrary lib = new SongLibrary();
        Playlist playlist = new Playlist(lib.getSongs());
        
        musicPlayer.play(playlist.getNext(), 10000);
        
    
        Scanner scan = new Scanner(System.in);
        String s;
        try {
            while (true) {
                s = scan.nextLine();
                if (s.contains("s")) {
                    musicPlayer.stop();
                } else if (s.contains("p")) {
                    musicPlayer.play(playlist.getNext(), 10000);
                } else if (s.contains("q")) {
                    System.exit(0);
                }
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
        PlayerResult obs = ((PlayerResult) arg);
        
        System.out.println();
        System.out.println(obs.getTimeMillis());
        System.out.println(obs.isAnswered());
        
        if(!obs.isAnswered()){
            ANSI.RED.println("WAITED TOOOOOOO LONG");
        }
        
    }
}
