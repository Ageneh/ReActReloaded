package model;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

import java.util.Observer;

/**
 * @author Henock Arega
 * @project ReActReloaded
 *
 * The player, used for the gameplay.
 * Provides all necessary methods to control, play etc. a {@link Song} given by the {@link SongLibrary}.
 */
public class MusicPlayer extends ObservableModel {

    private final SimpleMinim MINIM;
    private volatile SimpleAudioPlayer audioPlayer;
    private Thread thread;
    private PlayerControl control;
    private float volume;
    private Song currentSong;

    private MusicPlayer(){
        this.MINIM = new SimpleMinim(true);
    }

    public MusicPlayer(Observer o, Observer ... observers) {
        this();
        super.addAllObserver(o, observers);
        this.audioPlayer = audioPlayer;
        this.setVolume(20);
    }

    void play(Song song){
        this.stop();
        this.currentSong = song;
        this.audioPlayer = this.MINIM.loadMP3File(this.currentSong.getPath());
        this.setVolume(this.volume);
        this.audioPlayer.play();
    }

    void stop(){
        this.pause();
        this.MINIM.dispose();
    }

    void pause(){
        try {
            this.audioPlayer.pause();
        }
        catch (NullPointerException e){
            return;
        }
    }

    void setVolume(float val){
        this.volume = val;
        try {
            this.audioPlayer.setGain(this.linearToDB(this.volume));
        }catch (NullPointerException e){

        }
    }

   /** * @author Henock Arega
     * Replays the current song but a for a greater amount of time.
     * @param additionalTime The amount of time which is to be added to the normal
     *                       playback time.
     */
    void replay(long additionalTime){
        // TODO: play longer with loop
    }

   /** * @author Henock Arega
     * Will convert any given dB-Value to its linear value.
     * @param x
     * @return
     */
    private float dBToLinear(float x){
        return (float) Math.pow(10, (x / 20));
    }

   /** * @author Henock Arega
     * Will convert any given linear value to its corresponding dB-Value value.
     * @param x The value in the {@code interval [0, 100]} which is to be converted into its corresponding dB value.
     * @return Returns a float value, which represents the converted dB value.
     */
    private float linearToDB(float x){
        return (float) (Math.log10(x / 50) * 20);
    }

    @Override
    public void close(Close.Code code) {
        this.control.runProgram(code);
    }

    private abstract class PlayerControl implements Runnable{

        private boolean programIsRunning;

        private PlayerControl(){
            this.programIsRunning = true;
        }

        @Override
        public void run() {
            while(programIsRunning){
                // TODO
            }
        }

        private synchronized void runProgram(Close.Code code){
            if(code == Close.Code.CLOSE){
                this.programIsRunning = false;
            }
            else{
                this.programIsRunning = true;
            }
        }

        private synchronized void t_wait(){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private synchronized void resume(){
            this.notify();
        }

    }

}
