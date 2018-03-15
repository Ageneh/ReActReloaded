package model;

/**
 * @author Henock Arega
 * @project ReActReloaded
 *
 * An object class which is used as an argument when the {@link model.MusicPlayer.PlayerControl} notifies its observers.
 */
public class PlayerResult {
    
    /** The amount of time the {@link MusicPlayer} played its {@link MusicPlayer#currentSong}. */
    private long timeMillis;
    /** A flag which tells whether the {@link MusicPlayer} was stopped by an answer or the loop got to its
     * {@link MusicPlayer#posB end point} and stopped on its own. */
    private boolean answered;
    
    public PlayerResult(long timeMillis, boolean answered) {
        this.timeMillis = timeMillis;
        this.answered = answered;
    }
    
    /** @return {@link #timeMillis} */
    public long getTimeMillis() {
        return timeMillis;
    }
    
    /** @return {@link #answered} */
    public boolean isAnswered() {
        return answered;
    }
    
}
