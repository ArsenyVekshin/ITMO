package commands.tasks;

import collection.Storage;
import ui.exeptions.StreamBrooked;

public class PricesListCmd extends DataCmd{

    public PricesListCmd(Storage collection) {
        super("print_field_ascending_price", "prints list of sum from all collection", collection);
    }

    @Override
    public boolean execute(String arg) {
        if(checkHelpFlag(arg)) { help(); return true; }
        try {

            outputStream.println(collection.getPrices());

        } catch (StreamBrooked e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void help() {
        try {
            outputStream.println("""
                Syntax:
                > print_field_ascending_price
               prints list of sum from all collection
                PARAMS:
                -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            e.printStackTrace();
        }
    }
}
