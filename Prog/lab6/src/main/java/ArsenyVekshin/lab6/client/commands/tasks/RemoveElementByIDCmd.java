package ArsenyVekshin.lab6.client.commands.tasks;

import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.client.commands.tasks.parents.DialogueCmd;
import ArsenyVekshin.lab6.common.ui.InputHandler;
import ArsenyVekshin.lab6.common.ui.OutputHandler;
import ArsenyVekshin.lab6.common.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab6.common.collectionElems.exceptions.WrongCmdParam;

public class RemoveElementByIDCmd extends DialogueCmd {

    public RemoveElementByIDCmd(OutputHandler outputHandler, InputHandler inputHandler) {
        super("remove_by_id", "remove element with same id at collection", outputHandler, inputHandler);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        try {
            if(cmd.getArgs().size()==0) throw new WrongCmdParam("параметр не найден");
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
                > remove_by_id {id}
                   Command responsive for remove element with same id at collection
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
