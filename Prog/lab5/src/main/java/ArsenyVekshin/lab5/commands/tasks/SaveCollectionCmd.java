package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;

public class SaveCollectionCmd extends DataCmd{

    public SaveCollectionCmd(Storage collection, OutputHandler outputHandler) {
        super("save", "save collection to .csv file", collection, outputHandler);
    }

    @Override
    public boolean execute(String[] args) {
        if(checkHelpFlag(args)) { help(); return true; }
        collection.save();
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
               > save
                  Command responsive for save collection to .csv file
                  file path:  """ + collection.fileName + """ 
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
