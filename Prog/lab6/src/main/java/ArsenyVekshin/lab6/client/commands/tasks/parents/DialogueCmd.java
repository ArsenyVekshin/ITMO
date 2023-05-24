package ArsenyVekshin.lab6.client.commands.tasks.parents;

import ArsenyVekshin.lab6.common.ui.InputHandler;
import ArsenyVekshin.lab6.common.ui.OutputHandler;
import ArsenyVekshin.lab6.common.ui.console.ConsoleInputHandler;

public abstract class DialogueCmd extends DataCmd {
    protected InputHandler inputStream = new ConsoleInputHandler();

    public DialogueCmd(String name, String description, OutputHandler outputHandler, InputHandler inputHandler) {
        super(name, description, outputHandler);
        this.inputStream = inputHandler;
    }

    public void setInputStream(InputHandler inputStream) {
        this.inputStream = inputStream;
    }
}
