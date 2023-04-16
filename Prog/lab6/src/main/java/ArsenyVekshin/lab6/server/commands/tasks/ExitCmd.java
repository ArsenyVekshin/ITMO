package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.commands.CommandContainer;
import ArsenyVekshin.lab6.server.ui.OutputHandler;
import ArsenyVekshin.lab6.server.ui.exeptions.StreamBrooked;

public class ExitCmd extends DataCmd{

    public ExitCmd(Storage collection, OutputHandler outputHandler) {
        super("exit", "terminate program without saving", collection, outputHandler);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        System.exit(0);
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                > exit
                   terminate program without saving
                   WARNING!! don't use this cmd without save cmd
                """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
