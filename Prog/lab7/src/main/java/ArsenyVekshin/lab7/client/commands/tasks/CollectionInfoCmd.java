package ArsenyVekshin.lab7.client.commands.tasks;

import ArsenyVekshin.lab7.client.commands.tasks.parents.DataCmd;
import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.ui.OutputHandler;
import ArsenyVekshin.lab7.common.ui.exeptions.StreamBrooked;

public class CollectionInfoCmd extends DataCmd {
    public CollectionInfoCmd(OutputHandler outputHandler) {
        super("info", "meta-inf about collection", outputHandler);
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
                > info
                   Command responsive for print collection meta-inf
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
