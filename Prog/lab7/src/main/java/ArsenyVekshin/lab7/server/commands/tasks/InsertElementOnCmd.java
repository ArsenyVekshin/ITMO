package ArsenyVekshin.lab7.server.commands.tasks;

import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.collectionElems.data.Product;
import ArsenyVekshin.lab7.server.collection.Storage;
import ArsenyVekshin.lab7.server.commands.tasks.parents.DataCmd;

public class InsertElementOnCmd extends DataCmd {
    public InsertElementOnCmd(Storage collection) {
        super("insert_at", "add new element at idx-position at collection", collection);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        try{
            collection.insertToPosition(
                    Integer.valueOf(cmd.getArgs().get(0)),
                    (Product) cmd.getReturns());
            cmd.setReturns("done");

            collection.sort();
        } catch (Exception e) {
            cmd.setErrors(e.getMessage());
        }
        return true;
    }

}
