package ArsenyVekshin.lab6.client.ui;

import ArsenyVekshin.lab6.client.ui.exeptions.StreamBrooked;

/**
 * output stream handler
 */
public interface OutputHandler {

    /**
     * print value to current line
     * @param data value to print
     * @throws StreamBrooked
     */
    void print(String data) throws StreamBrooked;

    /**
     * print value to current line + set new line
     * @param data value to print
     * @throws StreamBrooked
     */
    void println(String data) throws StreamBrooked;

    /**
     * print err with flag to stream
     * @param data description
     * @throws StreamBrooked
     */
    default void printErr(String data) throws StreamBrooked {
        println("ERROR: " + data);
    }
}