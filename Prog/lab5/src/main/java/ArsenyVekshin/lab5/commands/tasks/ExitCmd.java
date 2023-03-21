package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;

public class ExitCmd extends DataCmd{

    public ExitCmd(Storage collection) {
        super("exit", "terminate program without saving", collection);
    }

    @Override
    public boolean execute(String arg) {
        if(checkHelpFlag(arg)) { help(); return true; }
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                Syntax:
                > exit
                terminate program without saving
                WARNING!! don't use this cmd without save cmd
                """);
        } catch (StreamBrooked e) {
            e.printStackTrace();
        }
    }
}
