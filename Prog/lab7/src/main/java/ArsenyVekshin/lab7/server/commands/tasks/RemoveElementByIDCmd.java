package ArsenyVekshin.lab7.server.commands.tasks;

import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.collectionElems.exceptions.WrongCmdParam;
import ArsenyVekshin.lab7.server.collection.Storage;
import ArsenyVekshin.lab7.server.commands.tasks.parents.DataCmd;

public class RemoveElementByIDCmd extends DataCmd {

    public RemoveElementByIDCmd(Storage collection) {
        super("remove_by_id", "remove element with same id at collection", collection);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        try {
            if(cmd.getArgs().size()==0) throw new WrongCmdParam("параметр не найден");
            collection.remove(Integer.parseInt(cmd.getArgs().get(0)));
            collection.save();
            cmd.setReturns("done");
        } catch (Exception e) {
            cmd.setErrors(e.getMessage());
            return false;
        }
        return true;
    }
}
