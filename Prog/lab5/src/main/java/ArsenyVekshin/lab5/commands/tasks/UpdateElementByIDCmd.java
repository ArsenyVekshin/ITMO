package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.collection.exceptions.WrongCmdParam;
import ArsenyVekshin.lab5.collection.exceptions.WrongID;
import ArsenyVekshin.lab5.ui.InputHandler;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab5.utils.builder.Builder;
import ArsenyVekshin.lab5.utils.builder.ObjTree;

public class UpdateElementByIDCmd extends DialogueCmd{
    ObjTree tree;
    public UpdateElementByIDCmd(Storage collection, OutputHandler outputHandler, InputHandler inputHandler, ObjTree tree) {
        super("update", "update element with same id at collection", collection, outputHandler, inputHandler);
        this.tree = tree;
    }

    @Override
    public boolean execute(String[] args) {
        if(checkHelpFlag(args)) { help(); return true; }
        try {
            if(args.length<2) throw new WrongCmdParam("параметр не найден");

            Builder newElem = new Builder(inputStream, outputStream);
            collection.update(Integer.parseInt(args[1]) ,newElem.buildDialogue(tree, collection.getElemById(Integer.parseInt(args[1])))) ;
        } catch (WrongID | WrongCmdParam | NumberFormatException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
