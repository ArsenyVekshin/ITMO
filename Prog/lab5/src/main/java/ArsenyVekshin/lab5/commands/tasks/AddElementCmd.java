package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.collection.data.Product;
import ArsenyVekshin.lab5.ui.InputHandler;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab5.utils.builder.Builder;
import ArsenyVekshin.lab5.utils.builder.ObjTree;

public class AddElementCmd extends DialogueCmd{
    ObjTree tree;
    public AddElementCmd(Storage collection, OutputHandler outputHandler, InputHandler inputHandler, ObjTree tree) {
        super("add", "add add new element to collection", collection, outputHandler, inputHandler);
        this.tree = tree;
    }

    @Override
    public boolean execute(String[] args) {
        if(checkHelpFlag(args)) { help(); return true; }
        Builder newElem = new Builder(inputStream, outputStream);
        collection.addNew(newElem.build(tree));
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
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