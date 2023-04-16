package ArsenyVekshin.lab6.client.commands.tasks;

import ArsenyVekshin.lab6.client.commands.CommandContainer;
import ArsenyVekshin.lab6.client.collection.Storage;
import ArsenyVekshin.lab6.client.ui.OutputHandler;
import ArsenyVekshin.lab6.client.ui.exeptions.StreamBrooked;

public class PricesListCmd extends DataCmd {

    public PricesListCmd(Storage collection, OutputHandler outputHandler) {
        super("print_field_ascending_price", "prints list of sum from all collection", collection, outputHandler);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        cmd.setReturns(collection.getPrices());
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
               > print_field_ascending_price
                  prints list of sum from all collection
                  PARAMS:
                  -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
