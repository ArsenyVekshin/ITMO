package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;

public class LoadCollectionCmd extends DataCmd{

    public LoadCollectionCmd(Storage collection, OutputHandler outputHandler) {
        super("load", "load collection from .csv file", collection, outputHandler);
    }

    @Override
    public boolean execute(String[] args) {
        if(checkHelpFlag(args)) { help(); return true; }
        if(args.length > 1 && !args[1].isEmpty()) collection.load(args[1]);
        else collection.load();
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
               > load
                  Command responsive for load collection from .csv file
                  file path:  """ + collection.path + """ 
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            e.printStackTrace();
        }
    }
}
