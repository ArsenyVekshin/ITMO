package ArsenyVekshin.lab7.server.commands.tasks;

import ArsenyVekshin.lab7.server.commands.tasks.parents.Command;
import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.exceptions.AccessRightsException;
import ArsenyVekshin.lab7.common.security.User;
import ArsenyVekshin.lab7.server.AuthManager;

public class AddNewUserCmd extends Command{
    private AuthManager authManager;
    public AddNewUserCmd(AuthManager authManager) {
        super("new_user", "add new user");
        this.authManager = authManager;
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        try {
            authManager.addUser(cmd.getUser(), (User) cmd.getReturns());
            cmd.setReturns("success");
        } catch (AccessRightsException e) {
            cmd.setErrors(e.getMessage());
        }
        return true;
    }

}
