package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.common.collectionElems.data.Product;
import ArsenyVekshin.lab6.common.collectionElems.exceptions.WrongCmdParam;
import ArsenyVekshin.lab6.server.commands.tasks.parents.DataCmd;

public class UpdateElementByIDCmd extends DataCmd {

    public UpdateElementByIDCmd(Storage collection) {
        super("update", "update element with same id at collection", collection);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        try {
            if(cmd.getArgs().size()==0) throw new WrongCmdParam("параметр не найден");
            long _id = Integer.parseInt(cmd.getArgs().get(0));

            if(cmd.getReturns() == null) cmd.setReturns(collection.getElemById((int)_id));
            else collection.update(_id, (Product) cmd.getReturns());
        } catch (Exception e) {
            cmd.setErrors(e.getMessage());
            return false;
        }
        return true;
    }
}
