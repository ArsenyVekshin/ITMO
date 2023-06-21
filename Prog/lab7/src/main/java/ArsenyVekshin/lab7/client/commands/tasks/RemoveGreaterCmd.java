package ArsenyVekshin.lab7.client.commands.tasks;

import ArsenyVekshin.lab7.client.commands.tasks.parents.NewObjCmd;
import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.builder.Builder;
import ArsenyVekshin.lab7.common.builder.ObjTree;
import ArsenyVekshin.lab7.common.ui.InputHandler;
import ArsenyVekshin.lab7.common.ui.OutputHandler;
import ArsenyVekshin.lab7.common.ui.exeptions.StreamBrooked;

public class RemoveGreaterCmd extends NewObjCmd {

    public RemoveGreaterCmd(OutputHandler outputHandler, InputHandler inputHandler, ObjTree tree) {
        super("remove_greater", "remove all element which are greater than this from collection",outputHandler, inputHandler, tree);
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
