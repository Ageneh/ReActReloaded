package model;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

import java.util.Observer;
import java.util.concurrent.TimeUnit;

/**
 * @author Henock Arega
 * @project ReActReloaded
 *
 * The player, used for the gameplay.
 * Provides all necessary methods to control, play etc. a {@link Song} given by the {@link SongLibrary}.
 */
public class MusicPlayer extends ObservableModel {

    private final int LOOPCOUNT = 0;
    private final SimpleMinim MINIM;
    private volatile SimpleAudioPlayer audioPlayer;
    /** A thread which will handle {@link #control the controller}. */
    private Thread thread;
    /**
     * The control of the music player.
     * @see PlayerControl
     */
    private PlayerControl control;
    /** The gain value of the {@link #audioPlayer}. */
    private float volume;
    /** The current, loaded {@link Song}. */
    private Song currentSong;
    /**
     * A flag used to determine whether the player was stopped by answering or because the {@link #posB end of the
     * loop} was reached.
     */
    private volatile boolean stopByAnswer;
    /** The starting point of the loop. */
    private volatile int posA;
    /** The end point of the loop. */
    private volatile int posB;
    /** The duration for the total length of the loop. */
    private volatile int playtime;

    private MusicPlayer(){
        this.MINIM = new SimpleMinim(true);
        this.control = new PlayerControl();
        this.thread = new Thread(this.control);
        this.thread.setDaemon(true);
        this.thread.setName("Music_Control");
        this.thread.start();
    }

    MusicPlayer(Observer o, Observer ... observers) {
        this();
        super.addAllObserver(o, observers);
        this.audioPlayer = audioPlayer;
        this.setVolume(20);
    }

    MusicPlayer(String s) {
        this();
        this.audioPlayer = audioPlayer;
        this.setVolume(20);
    }

    void play(Song song, int timeMillis){
        this.stop();
        this.playtime = timeMillis;
        System.out.println(song.getTitle() + ", " + TimeUnit.MILLISECONDS.toSeconds(timeMillis) + "s");
        this.currentSong = song;
        this.setLoop();
        this.play();
    }
    
    private void play(){
        this.setVolume(this.volume);
        this.control.resume();
    }
    
    private void setLoop(){
        final int INSET = 60;
        long length = Math.toIntExact(this.currentSong.lengthMillis());
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(length) + " >> length");
        this.posA = (int) (Math.random() * (length - TimeUnit.SECONDS.toMillis(INSET))) + (INSET/2);
        this.posB = posA + this.playtime;
        this.audioPlayer = this.MINIM.loadMP3File(this.currentSong.getPath());
        this.audioPlayer.setLoopPoints(this.posA, this.posB);
    }
    
    void stop(){
//        synchronized (control) {
            if (this.audioPlayer != null) {
                this.stopByAnswer = true;
                this.pause();
                this.MINIM.stop();
                this.control.resume();
            }
//        }
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
    synchronized void replay(long additionalTime){
        // TODO: play longer with loop
        this.playtime += additionalTime;
        this.posB = this.posA + this.playtime;
        this.audioPlayer.setLoopPoints(this.posA, this.posB);
        this.play();
    }
    
    public Song currentSong() {
        return currentSong;
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
    
    private void notifyObservers(PlayerResult result){
        super.notifyObservers(result);
    }

    @Override
    public void close(Close.Code code) {
        this.control.runProgram(code);
    }
    
    private class PlayerControl implements Runnable{

        private boolean programIsRunning;

        private PlayerControl(){
            this.programIsRunning = true;
        }

        @Override
        public void run() {
            while(programIsRunning){
                this.t_wait();
                stopByAnswer = false;
                MusicPlayer.this.audioPlayer.loop(LOOPCOUNT);
                this.t_wait(MusicPlayer.this.playtime - 100); // wait for given amount of time
                
                while(audioPlayer.isPlaying() && !stopByAnswer) t_wait(10);
                
                MusicPlayer.this.setChanged();
                MusicPlayer.this.notifyObservers(new PlayerResult(
                        MusicPlayer.this.posB - MusicPlayer.this.posA,
                        MusicPlayer.this.stopByAnswer
                ));
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
        
        private synchronized void t_wait(long time){
            try {
                this.wait(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private synchronized void resume(){
            this.notify();
        }

    }
    
}
