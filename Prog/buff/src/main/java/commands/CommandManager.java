package commands;

import collection.Storage;
import commands.tasks.Command;
import ui.InputHandler;
import ui.OutputHandler;

import java.util.Map;

public class CommandManager {

    InputHandler inputHandler;
    OutputHandler outputHandler;

    private  Map<String, Command> commands;

    public CommandManager(InputHandler inputHandler, OutputHandler outputHandler, Storage storage){
        //commands.put("")
    }

}
