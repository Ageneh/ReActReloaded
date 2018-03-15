package design;

/**
 * All dimensions of each element and view - at least their min and max values - will be defined in this enum.
 */
public enum Dimension {

    INPUTFIELD_W(250),
    INPUTFIELD_H(100),

    MIN_SCREEN_W(1100),
    MIN_SCREEN_H(750),

    MENU_PAD_ITEM(15),
    MENU_PAD(5),
    MENU_SPACE(20),
    GAME_INSET(50),

    COVER_SIZE(250),

    CONTROL_BUTTON_SIZE(100),

    MAX_PLAYVIEW_W(MIN_SCREEN_W.intValue() - 175),
    MAX_PLAYVIEW_H(MIN_SCREEN_H.intValue() - 150),

    MIN_Z(-10),
    MAX_Z(10),

    BIG_BUTTON_W(225),
    BIG_BUTTON_H(BIG_BUTTON_W.intValue()),

    HOVER_TRANSITION_MILLIS(400),

    GAMEPLAY_TOP(30),
    GAMEPLAY_LEFT(GAMEPLAY_TOP.intValue()),
    GAMEPLAY_BOTTOM(GAMEPLAY_TOP.intValue()),
    GAMEPLAY_RIGHT(200),

    BTN_OPACITY(0.8),
    MIN_BTN_RAD(15),
    MAX_BTN_RAD(25),

    STROKE_iBTN(6),

    EFFECT_BLUR(15),
    EFFECT_BLUR_XY(10),
    EFFECT_DROPSHADOW_X(0),
    EFFECT_DROPSHADOW_Y(8),
    EFFECT_DROPSHADOW_RAD(20),

    CHECKBOX_SIZE(35),

    MENU_MAX_WIDTH(300),
    MENU_MAX_HEIGHT(450)
    ;


    private Number val;


    //// CONSTRUCTOR

    Dimension(Number val){
        this.val = val;
    }


    //// GETTERS

    public int intValue() {
        return val.intValue();
    }

    public double doubleValue() {
        return val.doubleValue();
    }

}
