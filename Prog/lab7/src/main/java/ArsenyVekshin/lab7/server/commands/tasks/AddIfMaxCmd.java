package ArsenyVekshin.lab7.server.commands.tasks;

import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.collectionElems.data.Product;
import ArsenyVekshin.lab7.server.collection.Storage;
import ArsenyVekshin.lab7.server.commands.tasks.parents.DataCmd;


public class AddIfMaxCmd extends DataCmd {
    public AddIfMaxCmd(Storage collection) {
        super("add_if_max", "add new element to collection if it greater than others", collection);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getReturns()!=null) collection.addIfMax((Product) cmd.getReturns());
        collection.sort();
        collection.save();
        cmd.setReturns("done");
        return true;
    }

}
