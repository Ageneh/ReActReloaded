package model;

/**
 * A marker interface signaling that a class depends on a *.ini file.
 * Each of these classes of course needs to be able to {@link #readINI() read} and
 * and also {@link #writeINI() write} an/its ini-file.
 *
 * @author Henock Arega
 * @author Michael Heide
 * @project ReActReloaded
 */
public interface WritesINI {
    
    /** Provides a method for reading an ini-file. */
    void readINI();
    
    /** Provides a method for writing to an ini-file. */
    void writeINI();
    
}
