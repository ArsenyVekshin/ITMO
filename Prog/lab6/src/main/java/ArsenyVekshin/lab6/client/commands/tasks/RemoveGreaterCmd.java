package ArsenyVekshin.lab6.client.commands.tasks;

import ArsenyVekshin.lab6.client.commands.CommandContainer;
import ArsenyVekshin.lab6.client.collection.Storage;
import ArsenyVekshin.lab6.client.collection.data.Product;
import ArsenyVekshin.lab6.client.ui.InputHandler;
import ArsenyVekshin.lab6.client.ui.OutputHandler;
import ArsenyVekshin.lab6.client.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab6.client.utils.builder.Builder;
import ArsenyVekshin.lab6.client.utils.builder.ObjTree;

public class RemoveGreaterCmd extends DialogueCmd {

    public RemoveGreaterCmd(Storage collection, OutputHandler outputHandler, InputHandler inputHandler) {
        super("remove_greater", "remove all element which are greater than this from collection", collection, outputHandler, inputHandler);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        Builder newElem = new Builder(inputStream, outputStream);
        cmd.setReturns(newElem.buildDialogue(new ObjTree(Product.class)));
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
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
