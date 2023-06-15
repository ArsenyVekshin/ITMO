package ArsenyVekshin.lab7.server.commands.tasks;

import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.server.collection.Storage;
import ArsenyVekshin.lab7.server.commands.tasks.parents.DataCmd;


public class PricesListCmd extends DataCmd {

    public PricesListCmd(Storage collection) {
        super("print_field_ascending_price", "prints list of sum from all collection", collection);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        cmd.setReturns(collection.getPrices());
        return true;
    }

}
