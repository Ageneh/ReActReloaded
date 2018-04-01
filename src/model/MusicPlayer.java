package model;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import functions.ANSI;
import functions.INIReader;
import org.ini4j.Ini;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

/**
 * @author Henock Arega
 * @project ReActReloaded
 * <p>
 * The player, used for the gameplay.
 * Provides all necessary methods to control, play etc. a {@link Song} given by the {@link SongLibrary}.
 */
public class MusicPlayer extends ObservableModel implements WritesINI {
    
    private volatile SimpleAudioPlayer audioPlayer;
    /** The duration for the total length of the loop. */
    private volatile int playtime;
    /** The starting point of the loop. */
    private volatile int posA;
    /** The end point of the loop. */
    private volatile int posB;
    /**
     * A flag used to determine whether the player was stopped by answering or because the {@link #posB end of the
     * loop} was reached.
     */
    private volatile boolean stopByAnswer;
    private final String KEY_VOL = "VOL";
    /** A constant for the amount of times the player will loop the snippet. */
    private final int LOOPCOUNT = 0;
    private final SimpleMinim MINIM;
    private final String SECTION = "MUSIC_PLAYER";
    private final float STD_VOL = 50.0f;
    /**
     * The control of the music player.
     *
     * @see PlayerControl
     */
    private PlayerControl control;
    /** The current, loaded {@link Song}. */
    private Song currentSong;
    /** A thread which will handle {@link #control the controller}. */
    private Thread thread;
    /** The gain of the {@link #audioPlayer} in {@code [0, 100]}. */
    private float volume;
    
    //////////// CONSTRUCTORS
    private MusicPlayer() {
        this.MINIM = new SimpleMinim(true);
        this.control = new PlayerControl();
        this.thread = new Thread(this.control);
        this.thread.setDaemon(true);
        this.thread.setName("Music_Control");
        this.thread.start();
        this.readINI();
    }
    
    MusicPlayer(Observer o, Observer... observers) {
        this();
        super.addAllObserver(o, observers);
        this.audioPlayer = audioPlayer;
    }
    
    /** FOR TESTING **/
    MusicPlayer(String s) {
        /********************************/
        /********************************/
        /******* ONLY FOR TESTING *******/
        /********************************/
        /********************************/
        this();
        ANSI.RED.println("This.constructor is only made for testing purposes!".toUpperCase());
        this.audioPlayer = audioPlayer;
        this.setVolume(20);
        /********************************/
        /********************************/
        /******* ONLY FOR TESTING *******/
        /********************************/
        /********************************/
    }
    
    //////////// METHODS
    
    /**
     * @param additionalTime The amount of time which is to be added to the normal
     *                       playback time.
     * @author Henock Arega
     * Replays the current song but a for a greater amount of time.
     */
    synchronized void replay(long additionalTime) {
        this.playtime += additionalTime;
        this.posB = this.posA + this.playtime;
        this.audioPlayer.setLoopPoints(this.posA, this.posB);
        this.play();
    }
    
    public Song currentSong() {
        return currentSong;
    }
    
    public int getPlaytime() {
        return playtime;
    }
    
    void play(Song song, int timeMillis) {
        this.stop();
        this.playtime = timeMillis;
        System.out.println(song.getTitle() + ", " + TimeUnit.MILLISECONDS.toSeconds(timeMillis) + "s");
        this.currentSong = song;
        this.setLoop();
        this.stopByAnswer = false;
        this.play();
    }
    
    void play(Song song, Number timeMillis) {
        this.play(song, timeMillis.intValue());
    }
    
    void stop() {
        if (this.audioPlayer != null && this.audioPlayer.isPlaying()) {
            this.stopByAnswer = true;
            this.control.resume();
            this.pause();
            this.MINIM.stop();
        }
    }
    
    void pause() {
        if (this.audioPlayer != null) {
            this.audioPlayer.pause();
        }
    }
    
    void setVolume(float val) {
        final float MIN_VOL = 20;
        
        if (val < MIN_VOL) this.volume = MIN_VOL;
        else this.volume = val;
        try {
            this.audioPlayer.setGain(this.linearToDB(this.volume));
        } catch (NullPointerException ignored) {
        }
    }
    
    private void play() {
        this.setVolume(this.volume);
        this.control.resume();
    }
    
    private void setLoop() {
        final int INSET = 60;
        long length = Math.toIntExact(this.currentSong.lengthMillis());
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(length) + " >> length");
        long a = length - TimeUnit.SECONDS.toMillis(INSET);
        this.posA = (int) (Math.random() * Math.abs(a)) + (int) TimeUnit.SECONDS.toMillis(INSET / 2);
        this.posB = posA + this.playtime;
        this.audioPlayer = this.MINIM.loadMP3File(this.currentSong.getPath());
        this.audioPlayer.setLoopPoints(this.posA, this.posB);
    }
    
    /**
     * @param x
     * @return
     * @author Henock Arega
     * Will convert any given dB-Value to its linear value.
     */
    private float dBToLinear(float x) {
        return (float) Math.pow(10, (x / 20));
    }
    
    /**
     * @param x The value in the {@code interval [0, 100]} which is to be converted into its corresponding dB value.
     * @return Returns a float value, which represents the converted dB value.
     * @author Henock Arega
     * Will convert any given linear value to its corresponding dB-Value value.
     */
    private float linearToDB(float x) {
        return (float) (Math.log10(x / 50) * 20);
    }
    
    private void notifyObservers(PlayerResult result) {
        super.notifyObservers(result);
    }
    
    //////////// OVERRIDES
    @Override
    public void writeINI() {
        final String INI_PATH = Filepaths.INI_MUSICPLAYER.getFile().getAbsolutePath();
        try {
            File destFile = new File(INI_PATH);
            Ini ini = INIReader.getIni(INI_PATH);
            BufferedWriter br = new BufferedWriter(new FileWriter(INI_PATH));
            
            ini.load(destFile);
            ini.clear();
            
            ini.put(SECTION, KEY_VOL, this.volume);
            ini.store(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void readINI() {
        final String INI_PATH = Filepaths.INI_MUSICPLAYER.getFile().getAbsolutePath();
        try {
            Ini ini = INIReader.getIni(INI_PATH);
            
            String ini_vol_val = ini.get(SECTION, KEY_VOL);
            try {
                this.setVolume(Float.parseFloat(ini_vol_val));
            } catch (NumberFormatException ignored) {
                this.setVolume(this.STD_VOL);
            } catch (NullPointerException ignored) {
                this.setVolume(this.STD_VOL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public synchronized void close(Close.Code code) {
        this.control.runProgram(code);
        Thread.currentThread().interrupt();
        this.MINIM.dispose();
        this.writeINI();
    }
    
    private class PlayerControl implements Runnable {
        
        private boolean programIsRunning;
    
        //////////// CONSTRUCTORS
        private PlayerControl() {
            this.programIsRunning = true;
        }
    
        //////////// METHODS
        private synchronized void runProgram(Close.Code code) {
            if (code != Code.START || code != Code.CONTINUE) {
                this.programIsRunning = false;
                this.resume();
            } else {
                this.programIsRunning = true;
            }
        }
        
        private synchronized void t_wait() {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        private synchronized void t_wait(long time) {
            try {
                this.wait(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        private synchronized void resume() {
            this.notify();
        }
    
        //////////// OVERRIDES
        @Override
        public void run() {
            while (programIsRunning) {
                this.t_wait();
                if (programIsRunning)
                    stopByAnswer = false;
                MusicPlayer.this.audioPlayer.loop(LOOPCOUNT);
                this.t_wait(MusicPlayer.this.playtime /*- 10*/); // wait for given amount of time
//                while (audioPlayer.isPlaying() && !stopByAnswer && programIsRunning) t_wait(10);
                
                MusicPlayer.this.setChanged();
                MusicPlayer.this.notifyObservers(new PlayerResult(
                        MusicPlayer.this.posB - MusicPlayer.this.posA,
                        MusicPlayer.this.stopByAnswer
                ));
            }
            MusicPlayer.this.stop();
        }
        
    }
    
}