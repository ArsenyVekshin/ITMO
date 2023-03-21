package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.ui.InputHandler;
import ArsenyVekshin.lab5.ui.console.ConsoleInputHandler;

public abstract class DialogueCmd extends DataCmd{
    protected InputHandler inputStream = new ConsoleInputHandler();

    public DialogueCmd(String name, String description, Storage collection) {
        super(name, description, collection);
    }

    public void setInputStream(InputHandler inputStream) {
        this.inputStream = inputStream;
    }
}
