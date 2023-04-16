package ArsenyVekshin.lab6.client.commands.tasks;

import ArsenyVekshin.lab6.client.commands.CommandContainer;
import ArsenyVekshin.lab6.client.collection.Storage;
import ArsenyVekshin.lab6.client.ui.OutputHandler;
import ArsenyVekshin.lab6.client.ui.exeptions.StreamBrooked;

public class SumOfPriceCmd extends DataCmd {

    public SumOfPriceCmd(Storage collection, OutputHandler outputHandler) {
        super("sum_of_price", "prints sum of prices from all collection", collection, outputHandler);
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(cmd.getArgs().contains("h")) { help(); return true; }
        try {
            outputStream.println(String.valueOf(collection.getPricesSum()));
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                > sum_of_price
                   prints sum of prices from all collection
                   PARAMS:
                   -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
    }
}
