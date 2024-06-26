package ArsenyVekshin.lab7.client.commands.tasks;

import ArsenyVekshin.lab7.client.commands.tasks.parents.DataCmd;
import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.ui.OutputHandler;
import ArsenyVekshin.lab7.common.ui.exeptions.StreamBrooked;

public class LoadCollectionCmd extends DataCmd {

    public LoadCollectionCmd(OutputHandler outputHandler) {
        super("load", "load collection from .csv file", outputHandler);
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
                  file path supports only paths in server space
                  PARAMS:
                  -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
