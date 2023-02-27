package commands.tasks;

import collection.Storage;
import collection.data.Product;
import collection.data.UnitOfMeasure;
import collection.exceptions.WrongCmdParam;
import ui.exeptions.StreamBrooked;
import utils.builder.Builder;
import utils.builder.ObjTree;

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
