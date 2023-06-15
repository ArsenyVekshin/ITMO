package ArsenyVekshin.lab7.server.commands.tasks;

import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.server.collection.Storage;
import ArsenyVekshin.lab7.server.commands.tasks.parents.DataCmd;

public class LoadCollectionCmd extends DataCmd {

    public LoadCollectionCmd(Storage collection) {
        super("load", "load collection from .csv file", collection);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        try{
            if(cmd.getArgs().isEmpty()) collection.load();
            else collection.load(cmd.getArgs().get(0));
            collection.sort();
            collection.save();
        } catch (Exception e) {
            cmd.setErrors(e.getMessage());
        }

        return true;
    }
}
