package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.commands.CommandContainer;
import ArsenyVekshin.lab6.server.ui.OutputHandler;
import ArsenyVekshin.lab6.server.ui.exeptions.StreamBrooked;

public class LoadCollectionCmd extends DataCmd{

    public LoadCollectionCmd(Storage collection, OutputHandler outputHandler) {
        super("load", "load collection from .csv file", collection, outputHandler);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
               > load
                  Command responsive for load collection from .csv file
                  PARAMS:
                  -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
