package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.collection.data.Product;
import ArsenyVekshin.lab5.collection.data.UnitOfMeasure;
import ArsenyVekshin.lab5.collection.exceptions.WrongCmdParam;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab5.utils.builder.Builder;
import ArsenyVekshin.lab5.utils.builder.ObjTree;

public class RemoveByUnitOfMeasureCmd extends DataCmd{

    public RemoveByUnitOfMeasureCmd(Storage collection) {
        super("remove_all_by_unit_of_measure", "remove all element with same unitOfMeasure from collection", collection);
    }

    @Override
    public boolean execute(String arg) {
        if(checkHelpFlag(arg)) { help(); return true; }
        try {

            String[] args = arg.split(" ");
            if(args.length<2) throw new WrongCmdParam(arg);
            collection.removeSameUnitOfMeasure(UnitOfMeasure.valueOf(args[1]));

        } catch (WrongCmdParam e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                Syntax:
                > remove_all_by_unit_of_measure {unitOfMeasure}
                remove all element with same unitOfMeasure from collection
                PARAMS:
                -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            e.printStackTrace();
        }
    }
}
