package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.common.collectionElems.data.Product;
import ArsenyVekshin.lab6.server.commands.tasks.parents.DataCmd;

public class AddElementCmd extends DataCmd {
    public AddElementCmd(Storage collection) {
        super("add", "add add new element to collection", collection);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getReturns()!=null) collection.addNew((Product) cmd.getReturns());
        cmd.setReturns("done");
        return true;
    }

}
