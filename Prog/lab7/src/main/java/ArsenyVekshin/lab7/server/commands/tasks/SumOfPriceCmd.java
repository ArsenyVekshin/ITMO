package ArsenyVekshin.lab7.server.commands.tasks;

import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.server.collection.Storage;
import ArsenyVekshin.lab7.server.commands.tasks.parents.DataCmd;

public class SumOfPriceCmd extends DataCmd {

    public SumOfPriceCmd(Storage collection) {
        super("sum_of_price", "prints sum of prices from all collection", collection);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        try {
            cmd.setReturns(String.valueOf(collection.getPricesSum()));
        } catch (Exception e) {
            cmd.setReturns(e.getMessage());
            return false;
        }
        return true;
    }
}
