package ArsenyVekshin.lab7.server.commands.tasks;

import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.server.collection.Storage;
import ArsenyVekshin.lab7.server.commands.tasks.parents.DataCmd;


public class ClearCollectionCmd extends DataCmd {

    public ClearCollectionCmd(Storage collection) {
        super("clear", "clear collection", collection);
    }


    @Override
    public boolean execute(CommandContainer cmd) {
        collection.clear();
        cmd.setReturns("done");
        return true;
    }
}
