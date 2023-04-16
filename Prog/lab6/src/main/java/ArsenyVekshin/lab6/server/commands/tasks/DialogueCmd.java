package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.ui.InputHandler;
import ArsenyVekshin.lab6.server.ui.OutputHandler;
import ArsenyVekshin.lab6.server.ui.console.ConsoleInputHandler;

public abstract class DialogueCmd extends DataCmd{
    protected InputHandler inputStream = new ConsoleInputHandler();

    public DialogueCmd(String name, String description, Storage collection, OutputHandler outputHandler, InputHandler inputHandler) {
        super(name, description, collection, outputHandler);
        this.inputStream = inputHandler;
    }

    public void setInputStream(InputHandler inputStream) {
        this.inputStream = inputStream;
    }
}
