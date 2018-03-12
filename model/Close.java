package model;

/**
 * @author Henock Arega
 * @project ReActReloaded
 */
public interface Close {

    void close(Code code);

    enum Code{

        CLOSE, CONTINUE, START

    }

}
