package ArsenyVekshin.lab6.client.ui;

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
     * close stream
     */
    void close();
}
