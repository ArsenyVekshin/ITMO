package ArsenyVekshin.lab7.client.commands.tasks;

import ArsenyVekshin.lab7.client.commands.tasks.parents.NewObjCmd;
import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.builder.Builder;
import ArsenyVekshin.lab7.common.builder.ObjTree;
import ArsenyVekshin.lab7.common.collectionElems.data.Product;
import ArsenyVekshin.lab7.common.collectionElems.exceptions.WrongCmdParam;
import ArsenyVekshin.lab7.common.ui.InputHandler;
import ArsenyVekshin.lab7.common.ui.OutputHandler;
import ArsenyVekshin.lab7.common.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab7.common.ui.file.FileInputHandler;

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

            if(this.inputStream.getClass().getName() == FileInputHandler.class.getName()){
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
