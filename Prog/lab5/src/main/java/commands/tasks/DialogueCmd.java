package commands.tasks;

import collection.Storage;
import ui.InputHandler;
import ui.console.ConsoleInputHandler;

public abstract class DialogueCmd extends DataCmd{
    protected InputHandler inputStream = new ConsoleInputHandler();

    public DialogueCmd(String name, String description, Storage collection) {
        super(name, description, collection);
    }

    public void setInputStream(InputHandler inputStream) {
        this.inputStream = inputStream;
    }
}
