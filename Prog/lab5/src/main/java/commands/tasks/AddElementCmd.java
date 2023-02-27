package commands.tasks;

import collection.Storage;
import collection.data.Product;
import ui.exeptions.StreamBrooked;
import utils.builder.Builder;
import utils.builder.ObjTree;

public class AddElementCmd extends DialogueCmd{

    public AddElementCmd(Storage collection) {
        super("add", "add add new element to collection", collection);
    }

    @Override
    public boolean execute(String arg) {
        if(checkHelpFlag(arg)) { help(); return true; }
        try {
            Builder newElem = new Builder(inputStream, outputStream);
            collection.add(newElem.build(new ObjTree(Product.class)));
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
                > add
                Command responsive for add new element to collection
                Calls creation-dialogue
                PARAMS:
                -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            e.printStackTrace();
        }
    }
}
