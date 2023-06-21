package ArsenyVekshin.lab7.client.commands.tasks;

import ArsenyVekshin.lab7.client.commands.tasks.parents.NewObjCmd;
import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.builder.Builder;
import ArsenyVekshin.lab7.common.builder.ObjTree;
import ArsenyVekshin.lab7.common.collectionElems.exceptions.WrongCmdParam;
import ArsenyVekshin.lab7.common.ui.InputHandler;
import ArsenyVekshin.lab7.common.ui.OutputHandler;
import ArsenyVekshin.lab7.common.ui.exeptions.StreamBrooked;

public class InsertElementOnCmd extends NewObjCmd {
    public InsertElementOnCmd(OutputHandler outputHandler, InputHandler inputHandler, ObjTree tree) {
        super("insert_at", "add new element at idx-position at collection", outputHandler, inputHandler, tree);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        try {
            if(cmd.getArgs().size()==0) throw new WrongCmdParam("параметр не найден");
            Builder newElem = new Builder(inputStream, outputStream);
            cmd.setReturns(newElem.buildDialogue(tree));
        } catch (WrongCmdParam | NumberFormatException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                > insert_at {idx}
                   Command responsive for add new element at idx-position at collection
                   If this position isn't empty, old value appends to the end 
                   -Calls creation dialogue-
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
