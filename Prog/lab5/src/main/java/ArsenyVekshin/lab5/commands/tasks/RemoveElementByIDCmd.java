package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.collection.exceptions.WrongCmdParam;
import ArsenyVekshin.lab5.collection.exceptions.WrongID;
import ArsenyVekshin.lab5.ui.InputHandler;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;

public class RemoveElementByIDCmd extends DialogueCmd{

    public RemoveElementByIDCmd(Storage collection, OutputHandler outputHandler, InputHandler inputHandler) {
        super("remove_by_id", "remove element with same id at collection", collection, outputHandler, inputHandler);
    }

    @Override
    public boolean execute(String[] args) {
        if(checkHelpFlag(args)) { help(); return true; }
        try {
            if(args.length<2) throw new WrongCmdParam("параметр не найден");
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
