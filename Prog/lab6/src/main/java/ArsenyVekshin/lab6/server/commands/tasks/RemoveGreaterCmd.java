package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.common.collectionElems.data.Product;
import ArsenyVekshin.lab6.server.commands.tasks.parents.DataCmd;

public class RemoveGreaterCmd extends DataCmd {

    public RemoveGreaterCmd(Storage collection) {
        super("remove_greater", "remove all element which are greater than this from collection", collection);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        try{
            collection.removeGreater((Product) cmd.getReturns());
            collection.save();
            cmd.setReturns("done");
        } catch (Exception e) {
            cmd.setErrors(e.getMessage());
            return false;
        }

        return true;
    }
}
