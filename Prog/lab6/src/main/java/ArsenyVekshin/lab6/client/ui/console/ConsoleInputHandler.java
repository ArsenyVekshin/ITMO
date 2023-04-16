package ArsenyVekshin.lab6.client.ui.console;

import ArsenyVekshin.lab6.client.ui.InputHandler;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String get() {
        return scanner.nextLine();
    }

    @Override
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
