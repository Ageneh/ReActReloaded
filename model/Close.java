package model;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public interface Close {

    /**
     * A method which will be called when the process is to be closed.
     * @param code The type of code which identifying the reason why the process is closed.
     */
    void close(Code code);

    enum Code{

        CLOSE, CONTINUE, START

    }

}
