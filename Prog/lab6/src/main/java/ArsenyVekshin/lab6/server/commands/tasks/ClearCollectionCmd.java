package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.server.collection.Storage;

import ArsenyVekshin.lab6.server.commands.tasks.parents.DataCmd;


public class ClearCollectionCmd extends DataCmd {

    public ClearCollectionCmd(Storage collection) {
        super("clear", "clear collection", collection);
    }


    @Override
    public boolean execute(CommandContainer cmd) {
        collection.clear();
        collection.save();
        cmd.setReturns("done");
        return true;
    }
}
