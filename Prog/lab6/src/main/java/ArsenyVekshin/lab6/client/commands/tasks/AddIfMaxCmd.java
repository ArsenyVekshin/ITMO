package ArsenyVekshin.lab6.client.commands.tasks;

import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.client.commands.tasks.parents.NewObjCmd;
import ArsenyVekshin.lab6.common.ui.InputHandler;
import ArsenyVekshin.lab6.common.ui.OutputHandler;
import ArsenyVekshin.lab6.common.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab6.client.utils.builder.Builder;
import ArsenyVekshin.lab6.client.utils.builder.ObjTree;

public class AddIfMaxCmd extends NewObjCmd {
    public AddIfMaxCmd(OutputHandler outputHandler, InputHandler inputHandler, ObjTree tree) {
        super("add_if_max", "add new element to collection if it greater than others",outputHandler, inputHandler, tree);
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
                > add_if_max
                   add new element to collection if it greater than others
                   default criteria for sorting: price
                   Calls creation-dialogue
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
