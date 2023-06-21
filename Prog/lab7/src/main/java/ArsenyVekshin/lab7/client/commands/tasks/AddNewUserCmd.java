package ArsenyVekshin.lab7.client.commands.tasks;

import ArsenyVekshin.lab7.client.AuthManager;
import ArsenyVekshin.lab7.client.commands.tasks.parents.DialogueCmd;
import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.ui.InputHandler;
import ArsenyVekshin.lab7.common.ui.OutputHandler;
import ArsenyVekshin.lab7.common.ui.exeptions.StreamBrooked;

public class AddNewUserCmd extends DialogueCmd {
    AuthManager authManager;
    public AddNewUserCmd(OutputHandler outputHandler, InputHandler inputHandler, AuthManager authManager) {
        super("new_user", "add new user", outputHandler, inputHandler);
        this.authManager = authManager;
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        try {

            outputStream.println("Администратор:");
            cmd.setUser(authManager.genUser(inputStream, outputStream));

            outputStream.println("Новый пользователь:");
            cmd.setReturns(authManager.genUser(inputStream, outputStream));

        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());
        }


        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                > new_user
                   Command responsive for add new user to collection
                   Calls dialogue
                   Also needs admin login-pass
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
