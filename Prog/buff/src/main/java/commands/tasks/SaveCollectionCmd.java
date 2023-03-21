package commands.tasks;

import collection.Storage;
import ui.exeptions.StreamBrooked;

public class SaveCollectionCmd extends DataCmd{

    public SaveCollectionCmd(Storage collection) {
        super("save", "save collection to .csv file", collection);
    }

    @Override
    public boolean execute(String arg) {
        if(checkHelpFlag(arg)) { help(); return true; }
        collection.save();
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                Syntax:
                > save
                Command responsive for save collection to .csv file
                file path: """ + collection.fileName + """
               PARAMS:
                -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            e.printStackTrace();
        }
    }
}
