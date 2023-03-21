package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.console.ConsoleOutputHandler;

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
