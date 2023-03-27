package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.collection.exceptions.WrongCmdParam;
import ArsenyVekshin.lab5.collection.exceptions.WrongID;
import ArsenyVekshin.lab5.ui.InputHandler;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab5.utils.builder.Builder;
import ArsenyVekshin.lab5.utils.builder.ObjTree;

public class InsertElementOnCmd extends DialogueCmd{
    ObjTree tree;
    public InsertElementOnCmd(Storage collection, OutputHandler outputHandler, InputHandler inputHandler, ObjTree tree) {
        super("insert_at", "add new element at idx-position at collection", collection, outputHandler, inputHandler);
        this.tree = tree;
    }

    @Override
    public boolean execute(String[] args) {
        if(checkHelpFlag(args)) { help(); return true; }
        try {
            if(args.length<2) throw new WrongCmdParam("параметр не найден");

            Builder newElem = new Builder(inputStream, outputStream);
            collection.insertToPosition(Integer.parseInt(args[1]) ,newElem.buildDialogue(tree)) ;
        } catch (WrongID | WrongCmdParam | NumberFormatException | CloneNotSupportedException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
