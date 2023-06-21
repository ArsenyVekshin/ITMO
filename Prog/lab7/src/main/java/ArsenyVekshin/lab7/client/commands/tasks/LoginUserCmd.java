package ArsenyVekshin.lab7.client.commands.tasks;

import ArsenyVekshin.lab7.client.AuthManager;
import ArsenyVekshin.lab7.client.commands.tasks.parents.DialogueCmd;
import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.security.User;
import ArsenyVekshin.lab7.common.ui.InputHandler;
import ArsenyVekshin.lab7.common.ui.OutputHandler;
import ArsenyVekshin.lab7.common.ui.exeptions.StreamBrooked;

public class LoginUserCmd extends DialogueCmd {
    private AuthManager authManager;
    public LoginUserCmd(OutputHandler outputHandler, InputHandler inputHandler, AuthManager authManager) {
        super("login", "authorise user for this session", outputHandler, inputHandler);
        this.authManager = authManager;
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true;}
        authManager.authDialogue(inputStream, outputStream);
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                > login
                   authorise user for this session
                   Calls dialogue
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());
        }
    }
}
