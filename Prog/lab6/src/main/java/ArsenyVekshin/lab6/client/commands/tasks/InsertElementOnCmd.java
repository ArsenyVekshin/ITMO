package ArsenyVekshin.lab6.client.commands.tasks;

import ArsenyVekshin.lab6.client.commands.CommandContainer;
import ArsenyVekshin.lab6.client.ui.InputHandler;
import ArsenyVekshin.lab6.client.ui.OutputHandler;
import ArsenyVekshin.lab6.client.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab6.client.utils.builder.Builder;
import ArsenyVekshin.lab6.client.utils.builder.ObjTree;
import ArsenyVekshin.lab6.server.collection.exceptions.WrongCmdParam;

public class InsertElementOnCmd extends DialogueCmd {
    ObjTree tree;
    public InsertElementOnCmd(OutputHandler outputHandler, InputHandler inputHandler, ObjTree tree) {
        super("insert_at", "add new element at idx-position at collection", outputHandler, inputHandler);
        this.tree = tree;
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
