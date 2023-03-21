package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.ui.InputHandler;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.console.ConsoleInputHandler;

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
