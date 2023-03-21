package commands.tasks;

import collection.Storage;
import ui.exeptions.StreamBrooked;

public class ShowCollectionCmd extends DataCmd{

    public ShowCollectionCmd(Storage collection) {
        super("show", "print collection content", collection);
    }

    @Override
    public boolean execute(String arg) {
        if(checkHelpFlag(arg)) { help(); return true; }
        try {
            outputStream.println(collection.show());
        } catch (StreamBrooked e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                Syntax:
                > show
                Command responsive for print collection content
                PARAMS:
                -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            e.printStackTrace();
        }
    }
}
