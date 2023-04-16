package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.commands.CommandContainer;
import ArsenyVekshin.lab6.server.ui.OutputHandler;
import ArsenyVekshin.lab6.server.ui.exeptions.StreamBrooked;

public class CollectionInfoCmd extends DataCmd{
    public CollectionInfoCmd(Storage collection, OutputHandler outputHandler) {
        super("info", "meta-inf about collection", collection, outputHandler);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        cmd.setReturns(collection.info());
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
