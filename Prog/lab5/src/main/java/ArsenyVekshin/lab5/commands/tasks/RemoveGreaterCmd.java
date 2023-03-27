package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.collection.data.Product;
import ArsenyVekshin.lab5.ui.InputHandler;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab5.utils.builder.Builder;
import ArsenyVekshin.lab5.utils.builder.ObjTree;

public class RemoveGreaterCmd extends DialogueCmd{

    public RemoveGreaterCmd(Storage collection, OutputHandler outputHandler, InputHandler inputHandler) {
        super("remove_greater", "remove all element which are greater than this from collection", collection, outputHandler, inputHandler);
    }

    @Override
    public boolean execute(String[] args) {
        if(checkHelpFlag(args)) { help(); return true; }
        try {
            Builder newElem = new Builder(inputStream, outputStream);
            collection.removeGreater(newElem.buildDialogue(new ObjTree(Product.class)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
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
