package scenes.elements;

import design.Colors;
import functions.ElementBackgroundCreator;
import javafx.animation.PauseTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Song;

/**
 * @author Henock Arega
 * @author Michael Heide
 */
public class AnswerButton extends ReButton {
    
    private Song song;
    private Colors color;
    
    public AnswerButton() {
        super("empty answer");
        this.init();
    }
    
    public AnswerButton(Song song) {
        super(song.getTitle());
        this.song = song;
        this.init();
    }
    
    public Song getSong() {
        return song;
    }
    
    public void setSong(Song song) {
        this.song = song;
    }
    
    public void setColor(boolean correct) {
        Color color;
        if (correct) {
            color = Colors.BTN_GOOD.getColor();
        } else {
            color = Colors.BTN_BAD.getColor();
        }
        
        this.setBackground(ElementBackgroundCreator.getBackground(color));
        PauseTransition p = new PauseTransition(Duration.millis(1000));
        p.setOnFinished(event -> {
            this.setBackground(ElementBackgroundCreator.getBackground(this.color));
        });
        p.play();
    }
    
    private void init() {
        this.color = Colors.ANSWER_BTN;
        this.setBackground(ElementBackgroundCreator.getBackground(this.color));
    }
    
}
