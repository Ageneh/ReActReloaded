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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Henock Arega
 * @project ReActReloaded
 * <p>
 * The player, used for the gameplay.
 * Provides all necessary methods to control, play etc. a {@link Song} given by the {@link SongLibrary}.
 */
public class MusicPlayer extends ObservableModel implements WritesINI {
    
    private final String KEY_VOL = "VOL";
    /** A constant for the amount of times the player will loop the snippet. */
    private final int LOOPCOUNT = 0;
    private final SimpleMinim MINIM;
    private final String SECTION = "MUSIC_PLAYER";
    private final float STD_VOL = 50.0f;
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
    /** The current, loaded {@link Song}. */
    private Song currentSong;
    /** The gain of the {@link #audioPlayer} in {@code [0, 100]}. */
    private float volume;
    
    private volatile boolean isTasked;
    private Timer timer;
    private TimerTask timerTask;
    
    private MusicPlayer() {
        this.MINIM = new SimpleMinim(true);
        this.readINI();
    }
    
    MusicPlayer(Observer o, Observer... observers) {
        this();
        super.addAllObserver(o, observers);
        this.audioPlayer = audioPlayer;
    }
    
    synchronized void play(Song song, Number timeMillis) {
        this.isTasked = false;
        this.play(song, timeMillis.intValue());
    }
    
    /**
     * @param additionalTime The amount of time which is to be added to the normal playback time.
     * @author Henock Arega Replays the current song but a for a greater amount of time.
     */
    synchronized void replay(long additionalTime) {
        this.playtime += additionalTime;
        this.posB = this.posA + this.playtime;
        this.audioPlayer.setLoopPoints(this.posA, this.posB);
        this.play();
    }
    
    private synchronized void play() {
        this.setVolume(this.volume);
        
        if (! isTasked) reschedule();
        else {
            synchronized (timerTask) {
                timerTask.notify();
            }
        }

//        this.control.resume();
    }
    
    private synchronized void reschedule() {
        this.timerTask = new TimerTask() {
            //////////// OVERRIDES
            @Override
            public void run() {
                if (! isTasked) {
                    MusicPlayer.this.audioPlayer.loop(LOOPCOUNT);
                    isTasked = true;
                }
                if (! MusicPlayer.this.audioPlayer.isPlaying() ||
                        MusicPlayer.this.audioPlayer.position() >= MusicPlayer.this.posB) {
        
                    synchronized (timerTask) {
                        try {
                            timerTask.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    isTasked = false;
                }
            }
        };
        this.timer = new Timer("PlaybackTask");
        this.isTasked = false;
        
        this.timer.scheduleAtFixedRate(timerTask, 0, 200);
    }
    
    public Song currentSong() {
        return currentSong;
    }
    
    public int getPlaytime() {
        return playtime;
    }
    
    //////////// CONSTRUCTORS
    //////////// METHODS
    
    public void pause() {
        if (this.audioPlayer != null) {
            this.audioPlayer.pause();
        }
    }
    
    public void play(Song song, int timeMillis) {
        this.stop();
        this.playtime = timeMillis;
        ANSI.YELLOW.println(song.getTitle() + ", " + TimeUnit.MILLISECONDS.toSeconds(timeMillis) + "s");
        this.currentSong = song;
        this.setLoop();
        this.stopByAnswer = false;
        this.play();
    }
    
    public void setVolume(float val) {
        final float MIN_VOL = 20;
        
        if (val < MIN_VOL) this.volume = MIN_VOL;
        else this.volume = val;
        try {
            this.audioPlayer.setGain(this.linearToDB(this.volume));
        } catch (NullPointerException ignored) {
        }
    }
    
    public void stop() {
        if (this.audioPlayer != null) {
            this.stopByAnswer = true;
//            this.control.resume();
            MusicPlayer.this.setChanged();
            MusicPlayer.this.notifyObservers(new PlayerResult(
                    MusicPlayer.this.posB - MusicPlayer.this.posA,
                    MusicPlayer.this.stopByAnswer
            ));
            this.pause();
            try {
                this.timer.cancel();
                this.timer.purge();
            } catch (NullPointerException e) {
            }
            this.MINIM.stop();
        }
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
    public synchronized void close(Close.Code code) {
        Thread.currentThread().interrupt();
        this.MINIM.dispose();
        this.writeINI();
    }
    
}