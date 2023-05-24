package ArsenyVekshin.lab6.common.ui.console;

import ArsenyVekshin.lab6.common.ui.InputHandler;

import java.io.IOException;
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
    public boolean available() throws IOException {
        return (System.in.available())>0;
    }

    @Override
    public void close() {
        scanner.close();
    }

}
