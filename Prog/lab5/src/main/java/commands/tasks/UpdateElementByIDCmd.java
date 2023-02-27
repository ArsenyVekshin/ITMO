package commands.tasks;

import collection.Storage;
import collection.data.Product;
import collection.exceptions.WrongCmdParam;
import collection.exceptions.WrongID;
import ui.exeptions.StreamBrooked;
import utils.builder.Builder;
import utils.builder.ObjTree;

import java.util.Scanner;

public class UpdateElementByIDCmd extends DialogueCmd{

    public UpdateElementByIDCmd(Storage collection) {
        super("update", "update element with same id at collection", collection);
    }

    @Override
    public boolean execute(String arg) {
        if(checkHelpFlag(arg)) { help(); return true; }
        try {
            String[] args = arg.split(" ");
            if(args.length<2) throw new WrongCmdParam(arg);

            Builder newElem = new Builder(inputStream, outputStream);
            collection.update(Integer.parseInt(args[1]) ,newElem.build(new ObjTree(Product.class))) ;
        } catch (StreamBrooked | WrongID | WrongCmdParam | NumberFormatException e) {
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
