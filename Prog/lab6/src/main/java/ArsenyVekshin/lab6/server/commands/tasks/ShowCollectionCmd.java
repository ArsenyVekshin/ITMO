package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.general.CommandContainer;
import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.commands.tasks.parents.DataCmd;

public class ShowCollectionCmd extends DataCmd {

    public ShowCollectionCmd(Storage collection) {
        super("show", "print collection content", collection);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        cmd.setReturns(collection.show());
        return true;
    }
}
