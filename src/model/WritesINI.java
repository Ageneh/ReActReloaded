package model;

/**
 * @author Henock Arega
 * @project ReActReloaded
 *
 * A marker interface signaling that a class depends on a *.ini file.
 * Each of these classes of course needs to be able to {@link #readINI() read} and
 * and also {@link #writeINI() write} an/its ini-file.
 */
public interface WritesINI {
    
    /** Provides a method for writing to an ini-file. */
    void writeINI();
    /** Provides a method for reading an ini-file. */
    void readINI();
    
}