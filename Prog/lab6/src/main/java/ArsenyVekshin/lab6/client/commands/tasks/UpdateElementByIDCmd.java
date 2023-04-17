package ArsenyVekshin.lab6.client.commands.tasks;

import ArsenyVekshin.lab6.general.CommandContainer;
import ArsenyVekshin.lab6.client.commands.tasks.parents.NewObjCmd;
import ArsenyVekshin.lab6.client.ui.InputHandler;
import ArsenyVekshin.lab6.client.ui.OutputHandler;
import ArsenyVekshin.lab6.client.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab6.client.utils.builder.Builder;
import ArsenyVekshin.lab6.client.utils.builder.ObjTree;
import ArsenyVekshin.lab6.general.collectionElems.data.Product;
import ArsenyVekshin.lab6.general.collectionElems.exceptions.WrongCmdParam;
import ArsenyVekshin.lab6.server.ui.file.FileInputHandler;

public class UpdateElementByIDCmd extends NewObjCmd {
    public UpdateElementByIDCmd(OutputHandler outputHandler, InputHandler inputHandler, ObjTree tree) {
        super("update", "update element with same id at collection", outputHandler, inputHandler, tree);
        this.tree = tree;
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        try {
            if(cmd.getArgs().size()==0) throw new WrongCmdParam("параметр не найден");

            if(inputStream.getClass() == FileInputHandler.class){
                Builder newElem = new Builder(inputStream, outputStream);
                cmd.setReturns(newElem.buildDialogue(tree));
            }
            else{
                if(cmd.getReturns() != null){
                    cmd.setNeedToRecall(false);
                    Builder newElem = new Builder(inputStream, outputStream);
                    cmd.setReturns(newElem.buildDialogue(tree, (Product) cmd.getReturns()));
                }
                else cmd.setNeedToRecall(true);
            }
        } catch (Exception e) {
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
