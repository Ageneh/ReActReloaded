package design;

import javafx.scene.effect.BoxBlur;

public enum Effect {
    
    BLUR(new BoxBlur(
            Dimension.EFFECT_BLUR.intValue(),
            Dimension.EFFECT_BLUR.intValue(),
            3
    ));
    
    
    private javafx.scene.effect.Effect effect;
    
    Effect(javafx.scene.effect.Effect effect) {
        this.effect = effect;
    }
    
    public javafx.scene.effect.Effect getEffect() {
        return effect;
    }
}
