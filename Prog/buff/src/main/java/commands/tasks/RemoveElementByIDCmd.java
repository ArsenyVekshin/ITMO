package commands.tasks;

import collection.Storage;
import collection.data.Product;
import collection.exceptions.WrongCmdParam;
import collection.exceptions.WrongID;
import ui.exeptions.StreamBrooked;
import utils.builder.Builder;
import utils.builder.ObjTree;

public class RemoveElementByIDCmd extends DialogueCmd{

    public RemoveElementByIDCmd(Storage collection) {
        super("remove_by_id {id}", "remove element with same id at collection", collection);
    }

    @Override
    public boolean execute(String arg) {
        if(checkHelpFlag(arg)) { help(); return true; }
        try {
            String[] args = arg.split(" ");
            if(args.length<2) throw new WrongCmdParam(arg);
            collection.remove(Integer.parseInt(args[1]));
        } catch ( WrongID | WrongCmdParam | NumberFormatException e) {
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
                > remove_by_id {id}
                Command responsive for remove element with same id at collection
                PARAMS:
                -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            e.printStackTrace();
        }
    }
}
