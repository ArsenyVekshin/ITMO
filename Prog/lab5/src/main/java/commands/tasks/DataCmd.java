package commands.tasks;

import collection.Storage;
import ui.OutputHandler;
import ui.console.ConsoleOutputHandler;

public abstract class DataCmd extends Command {
    protected OutputHandler outputStream = new ConsoleOutputHandler();
    protected final Storage collection;

    public DataCmd(String name, String description, Storage collection) {
        super(name, description);
        this.collection = collection;
    }

    public void setOutputStream(OutputHandler outputStream){this.outputStream = outputStream;}

}
