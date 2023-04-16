package ArsenyVekshin.lab6.client.ui.console;

import ArsenyVekshin.lab6.client.ui.OutputHandler;

public class ConsoleOutputHandler implements OutputHandler {
    @Override
    public void print(String data) {
        System.out.print(data);
    }

    @Override
    public void println(String data) {
        System.out.println(data);
    }
}
