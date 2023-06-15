package ArsenyVekshin.lab7.common.ui;

import java.io.IOException;

/**
 * Input-stream handler
 */
public interface InputHandler {
    /**
     * get line from stream
     * @return line
     */
    String get();

    /**
     * check is new line available
     * @return boolean
     */
    boolean hasNextLine();

    /**
     * check is input-stream not null
     * @return boolean
     */
    boolean available() throws IOException;

    /**
     * close stream
     */
    void close();

}
