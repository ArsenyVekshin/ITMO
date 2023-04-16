package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.ui.OutputHandler;
import ArsenyVekshin.lab6.server.ui.console.ConsoleOutputHandler;

public abstract class DataCmd extends Command {
    protected OutputHandler outputStream = new ConsoleOutputHandler();
    protected final Storage collection;

    public DataCmd(String name, String description, Storage collection, OutputHandler outputHandler) {
        super(name, description);
        this.collection = collection;
        this.outputStream = outputHandler;
    }

    public void setOutputStream(OutputHandler outputStream){this.outputStream = outputStream;}

}
