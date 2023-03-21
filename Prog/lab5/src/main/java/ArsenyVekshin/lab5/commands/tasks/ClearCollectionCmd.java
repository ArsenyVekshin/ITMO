package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;

public class ClearCollectionCmd extends DataCmd{

    public ClearCollectionCmd(Storage collection) {
        super("clear", "clear collection", collection);
    }

    @Override
    public boolean execute(String arg) {
        if(checkHelpFlag(arg)) { help(); return true; }
        collection.clear();
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                Syntax:
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
