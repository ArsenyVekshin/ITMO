package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.collection.data.Product;
import ArsenyVekshin.lab5.ui.InputHandler;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab5.utils.builder.Builder;
import ArsenyVekshin.lab5.utils.builder.ObjTree;

public class AddIfMaxCmd extends DialogueCmd{

    public AddIfMaxCmd(Storage collection, OutputHandler outputHandler, InputHandler inputHandler) {
        super("add_if_max", "add new element to collection if it greater than others", collection, outputHandler, inputHandler);
    }

    @Override
    public boolean execute(String[] args) {
        if(checkHelpFlag(args)) { help(); return true; }
        try {
            Builder newElem = new Builder(inputStream, outputStream);
            collection.addIfMax(newElem.build(new ObjTree(Product.class)));
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
                > add_if_max
                   add new element to collection if it greater than others
                   default criteria for sorting: price
                   Calls creation-dialogue
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            e.printStackTrace();
        }
    }
}
