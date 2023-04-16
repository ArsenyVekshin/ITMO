package ArsenyVekshin.lab6.server.commands.tasks;

import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.collection.exceptions.WrongCmdParam;
import ArsenyVekshin.lab6.server.collection.exceptions.WrongID;
import ArsenyVekshin.lab6.server.ui.InputHandler;
import ArsenyVekshin.lab6.server.ui.OutputHandler;
import ArsenyVekshin.lab6.server.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab6.server.utils.builder.Builder;
import ArsenyVekshin.lab6.server.utils.builder.ObjTree;

public class UpdateElementByIDCmd extends DialogueCmd{
    ObjTree tree;
    public UpdateElementByIDCmd(Storage collection, OutputHandler outputHandler, InputHandler inputHandler, ObjTree tree) {
        super("update", "update element with same id at collection", collection, outputHandler, inputHandler);
        this.tree = tree;
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        try {
            if(args.length<2) throw new WrongCmdParam("параметр не найден");

            Builder newElem = new Builder(inputStream, outputStream);
            collection.update(Integer.parseInt(args[1]) ,newElem.buildDialogue(tree, collection.getElemById(Integer.parseInt(args[1])))) ;
        } catch (WrongID | WrongCmdParam | NumberFormatException e) {
            System.out.println(e.getMessage());//System.out.println(e.getMessage());//e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                > update {id}
                   Command responsive for update element with same id at collection
                   Calls creation dialogue
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
