package ArsenyVekshin.lab7.server.commands.tasks;

import ArsenyVekshin.lab7.server.AuthManager;
import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.server.commands.tasks.parents.Command;

public class LoginUserCmd extends Command {
    private AuthManager authManager;
    public LoginUserCmd(AuthManager authManager) {
        super("login", "authorise user for this session");
        this.authManager = authManager;
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        cmd.setReturns(authManager.isAuthorised(cmd.getUser()));
        return true;
    }
}
