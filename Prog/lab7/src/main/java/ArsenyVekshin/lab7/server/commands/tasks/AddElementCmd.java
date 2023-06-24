package ArsenyVekshin.lab7.server.commands.tasks;

import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.collectionElems.data.Product;

import ArsenyVekshin.lab7.server.collection.Storage;
import ArsenyVekshin.lab7.server.commands.tasks.parents.DataCmd;

public class AddElementCmd extends DataCmd {
    public AddElementCmd(Storage collection) {
        super("add", "add add new element to collection", collection);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getReturns()!=null) collection.addNew((Product) cmd.getReturns());
        System.out.println(collection.info());
        collection.sort();
        cmd.setReturns("done");
        return true;
    }

}
