package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;

public class ClearCollectionCmd extends DataCmd{

    public ClearCollectionCmd(Storage collection, OutputHandler outputHandler) {
        super("clear", "clear collection", collection, outputHandler);
    }

    @Override
    public boolean execute(String[] args) {
        if(checkHelpFlag(args)) { help(); return true; }
        collection.clear();
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                > clear
                   Command responsive for clear collection
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            e.printStackTrace();
        }
    }
}
