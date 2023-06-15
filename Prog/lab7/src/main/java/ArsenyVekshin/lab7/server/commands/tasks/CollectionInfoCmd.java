package ArsenyVekshin.lab7.server.commands.tasks;

import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.server.collection.Storage;
import ArsenyVekshin.lab7.server.commands.tasks.parents.DataCmd;

public class CollectionInfoCmd extends DataCmd {
    public CollectionInfoCmd(Storage collection) {
        super("info", "meta-inf about collection", collection);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        cmd.setReturns(collection.info());
        return true;
    }

}
