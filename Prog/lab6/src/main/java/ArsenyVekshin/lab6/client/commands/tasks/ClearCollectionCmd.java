package ArsenyVekshin.lab6.client.commands.tasks;

import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.client.commands.tasks.parents.DataCmd;
import ArsenyVekshin.lab6.common.ui.OutputHandler;
import ArsenyVekshin.lab6.common.ui.exeptions.StreamBrooked;

public class ClearCollectionCmd extends DataCmd {

    public ClearCollectionCmd(OutputHandler outputHandler) {
        super("clear", "clear collection", outputHandler);
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
                > clear
                   Command responsive for clear collection
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
