package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.collection.data.Product;
import ArsenyVekshin.lab5.collection.exceptions.WrongCmdParam;
import ArsenyVekshin.lab5.collection.exceptions.WrongID;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab5.utils.builder.Builder;
import ArsenyVekshin.lab5.utils.builder.ObjTree;

public class InsertElementOnCmd extends DialogueCmd{

    public InsertElementOnCmd(Storage collection) {
        super("insert_at {idx}", "add new element at idx-position at collection", collection);
    }

    @Override
    public boolean execute(String arg) {
        if(checkHelpFlag(arg)) { help(); return true; }
        try {
            String[] args = arg.split(" ");
            if(args.length<2) throw new WrongCmdParam(arg);

            Builder newElem = new Builder(inputStream, outputStream);
            collection.insertToPosition(Integer.parseInt(args[1]) ,newElem.build(new ObjTree(Product.class))) ;
        } catch (StreamBrooked | WrongID | WrongCmdParam | NumberFormatException | CloneNotSupportedException e) {
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
