package ArsenyVekshin.lab5.commands;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.commands.tasks.Command;
import ArsenyVekshin.lab5.ui.InputHandler;
import ArsenyVekshin.lab5.ui.OutputHandler;

import java.util.Map;

public class CommandManager {

    InputHandler inputHandler;
    OutputHandler outputHandler;

    private  Map<String, Command> commands;

    public CommandManager(InputHandler inputHandler, OutputHandler outputHandler, Storage storage){
        //commands.put("")
    }

}
