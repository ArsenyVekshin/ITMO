package ArsenyVekshin.lab5;//Вариант 3116003

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.collection.exceptions.InvalidValueEntered;
import ArsenyVekshin.lab5.commands.CommandManager;
import ArsenyVekshin.lab5.ui.console.ConsoleInputHandler;
import ArsenyVekshin.lab5.ui.console.ConsoleOutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab5.utils.builder.ObjTree;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Программа запущена");
            Storage collection = new Storage();
            collection.init();
            //collection.fillRandom();
            CommandManager commandManager = new CommandManager(collection, new ConsoleInputHandler(), new ConsoleOutputHandler());
            commandManager.startExecuting();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}