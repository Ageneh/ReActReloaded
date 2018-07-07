package model;

/**
 * Marks any class which is part of a game.
 *
 * @author Henock Arega
 * @project ReActReloaded
 */
public interface isGame {
    
    //////////// METHODS
    long getCurrentPlayTime();
    
    enum Action {
        CURRENT_SONG,
        ANSWERS,
        NEW_MULTIPLIER,
        ANSWER_CORRECT,
        ANSWER_INCORRECT,
        ANSWER,
        LIFECOUNT,
        
        POINTS;
        
        private Object val;
        
        Action() {
            this.val = new Object();
        }
        
        public Object getVal() {
            return val;
        }
        
        public Action setVal(Object val) {
            this.val = val;
            return this;
        }
    }
    
}


