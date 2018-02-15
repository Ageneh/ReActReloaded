/**
 * Contains specific ranges which are made available globally.
 */
public enum Range {

    VOL_MAX(100.0), VOL_MIN(0.0), GAIN_MIN(-80);

    /**
     * The value of range.
     * <br>Because outside classes which use this enum may need different data types each value is saved in a
     * {@link Number number object}.</br>
     */
    private Number val;

    Range(Number val){
        this.val = val;
    }

    public int intValue() {
        return val.intValue();
    }

    public double doubleValue() {
        return val.doubleValue();
    }

    public float floatValue() {
        return val.floatValue();
    }
}
