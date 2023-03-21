package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.collection.data.Product;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab5.utils.builder.Builder;
import ArsenyVekshin.lab5.utils.builder.ObjTree;

public class RemoveGreaterCmd extends DialogueCmd{

    public RemoveGreaterCmd(Storage collection) {
        super("remove_greater", "remove all element which are greater than this from collection", collection);
    }

    @Override
    public boolean execute(String arg) {
        if(checkHelpFlag(arg)) { help(); return true; }
        try {
            Builder newElem = new Builder(inputStream, outputStream);
            collection.removeGreater(newElem.build(new ObjTree(Product.class)));
        } catch (StreamBrooked | NoSuchFieldException | IllegalAccessException e) {
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
                > remove_greater
                remove all element which are greater than this from collection
                Calls creation-dialogue
                PARAMS:
                -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            e.printStackTrace();
        }
    }
}
