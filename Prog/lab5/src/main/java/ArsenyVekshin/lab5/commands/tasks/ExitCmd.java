package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;

public class ExitCmd extends DataCmd{

    public ExitCmd(Storage collection, OutputHandler outputHandler) {
        super("exit", "terminate program without saving", collection, outputHandler);
    }

    @Override
    public boolean execute(String[] args) {
        if(checkHelpFlag(args)) { help(); return true; }
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
