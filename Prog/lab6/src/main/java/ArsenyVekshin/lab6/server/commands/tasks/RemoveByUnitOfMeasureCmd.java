package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.general.CommandContainer;
import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.general.collectionElems.data.UnitOfMeasure;
import ArsenyVekshin.lab6.general.collectionElems.exceptions.WrongCmdParam;
import ArsenyVekshin.lab6.server.commands.tasks.parents.DataCmd;

public class RemoveByUnitOfMeasureCmd extends DataCmd {

    public RemoveByUnitOfMeasureCmd(Storage collection) {
        super("remove_all_by_unit_of_measure", "remove all element with same unitOfMeasure from collection", collection);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        try {
            if(cmd.getArgs().size()==0) throw new WrongCmdParam("параметр не найден");
            collection.removeSameUnitOfMeasure(UnitOfMeasure.valueOf(cmd.getArgs().get(0)));
            cmd.setReturns("done");
        } catch (Exception  e) {
            cmd.setErrors(e.getMessage());
            return false;
        }
        return true;
    }

}
