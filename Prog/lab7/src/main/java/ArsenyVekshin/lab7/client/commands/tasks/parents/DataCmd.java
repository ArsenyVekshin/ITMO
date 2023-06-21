package ArsenyVekshin.lab7.client.commands.tasks.parents;

import ArsenyVekshin.lab7.common.ui.OutputHandler;
import ArsenyVekshin.lab7.common.ui.console.ConsoleOutputHandler;

public abstract class DataCmd extends Command {
    protected OutputHandler outputStream = new ConsoleOutputHandler();

    public DataCmd(String name, String description, OutputHandler outputHandler) {
        super(name, description);
        this.outputStream = outputHandler;
    }

    public void setOutputStream(OutputHandler outputStream){this.outputStream = outputStream;}

}
