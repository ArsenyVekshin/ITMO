package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.commands.CommandContainer;
import ArsenyVekshin.lab6.server.ui.InputHandler;
import ArsenyVekshin.lab6.server.ui.OutputHandler;
import ArsenyVekshin.lab6.server.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab6.server.utils.builder.Builder;
import ArsenyVekshin.lab6.server.utils.builder.ObjTree;

public class AddElementCmd extends DialogueCmd{
    ObjTree tree;
    public AddElementCmd(Storage collection, OutputHandler outputHandler, InputHandler inputHandler, ObjTree tree) {
        super("add", "add add new element to collection", collection, outputHandler, inputHandler);
        this.tree = tree;
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        Builder newElem = new Builder(inputStream, outputStream);
        cmd.setReturns(newElem.buildDialogue(tree));
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
            System.out.println(e.getMessage());//System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
