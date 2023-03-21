package ArsenyVekshin.lab5;//Вариант 3116003

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.commands.CommandManager;
import ArsenyVekshin.lab5.ui.console.ConsoleInputHandler;
import ArsenyVekshin.lab5.ui.console.ConsoleOutputHandler;
import ArsenyVekshin.lab5.utils.builder.ObjTree;

public class Main {
    public static void main(String[] args) {
        System.out.println("Программа запущена");
        Storage collection = new Storage();
        CommandManager commandManager = new CommandManager(new ConsoleInputHandler(), new ConsoleOutputHandler(), collection);
        commandManager.startExecuting();
    }
}