package ui;

import ui.exeptions.StreamBrooked;

public interface OutputHandler {

    void print(String data) throws StreamBrooked;

    void println(String data) throws StreamBrooked;

    default void printErr(String data) throws StreamBrooked {
        println("ERROR: " + data);
    }
}
