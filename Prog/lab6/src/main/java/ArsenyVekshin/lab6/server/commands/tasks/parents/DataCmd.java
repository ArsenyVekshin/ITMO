package ArsenyVekshin.lab6.server.commands.tasks.parents;

import ArsenyVekshin.lab6.server.collection.Storage;

public abstract class DataCmd extends Command {
    protected final Storage collection;

    public DataCmd(String name, String description, Storage collection) {
        super(name, description);
        this.collection = collection;
    }
}
