package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;

public class ShowCollectionCmd extends DataCmd{

    public ShowCollectionCmd(Storage collection, OutputHandler outputHandler) {
        super("show", "print collection content", collection, outputHandler);
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if(checkHelpFlag(args)) { help(); return true; }
            outputStream.println(collection.show());
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                > show
                   Command responsive for print collection content
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
