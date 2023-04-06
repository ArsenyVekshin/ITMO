package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.collection.data.UnitOfMeasure;
import ArsenyVekshin.lab5.collection.exceptions.WrongCmdParam;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;

public class RemoveByUnitOfMeasureCmd extends DataCmd{

    public RemoveByUnitOfMeasureCmd(Storage collection, OutputHandler outputHandler) {
        super("remove_all_by_unit_of_measure", "remove all element with same unitOfMeasure from collection", collection, outputHandler);
    }

    @Override
    public boolean execute(String[] args) {
        if(checkHelpFlag(args)) { help(); return true; }
        try {
            if(args.length<2) throw new WrongCmdParam("параметр не найден");
            collection.removeSameUnitOfMeasure(UnitOfMeasure.valueOf(args[1]));

        } catch (WrongCmdParam e) {
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
