package ArsenyVekshin.lab6.client.commands.tasks;

import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.client.commands.tasks.parents.DataCmd;
import ArsenyVekshin.lab6.client.ui.OutputHandler;
import ArsenyVekshin.lab6.client.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab6.common.collectionElems.data.UnitOfMeasure;
import ArsenyVekshin.lab6.common.collectionElems.exceptions.WrongCmdParam;

public class RemoveByUnitOfMeasureCmd extends DataCmd {

    public RemoveByUnitOfMeasureCmd(OutputHandler outputHandler) {
        super("remove_all_by_unit_of_measure", "remove all element with same unitOfMeasure from collection", outputHandler);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        try {
            if(cmd.getArgs().size()==0) throw new WrongCmdParam("параметр не найден");
            cmd.setReturns(UnitOfMeasure.valueOf(cmd.getArgs().get(0)));
        } catch (Exception  e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                > remove_all_by_unit_of_measure {unitOfMeasure}
                   remove all element with same unitOfMeasure from collection
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
