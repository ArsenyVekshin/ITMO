package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.collection.data.UnitOfMeasure;
import ArsenyVekshin.lab6.server.collection.exceptions.WrongCmdParam;
import ArsenyVekshin.lab6.server.commands.CommandContainer;
import ArsenyVekshin.lab6.server.ui.OutputHandler;
import ArsenyVekshin.lab6.server.ui.exeptions.StreamBrooked;

public class RemoveByUnitOfMeasureCmd extends DataCmd{

    public RemoveByUnitOfMeasureCmd(Storage collection, OutputHandler outputHandler) {
        super("remove_all_by_unit_of_measure", "remove all element with same unitOfMeasure from collection", collection, outputHandler);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        try {
            if(cmd.getArgs().size()==0) throw new WrongCmdParam("параметр не найден");
            cmd.setReturns(UnitOfMeasure.valueOf(cmd.getArgs().get(0)));
        } catch (Exception  e) {
            System.out.println(e.getMessage());//e.printStackTrace();
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
