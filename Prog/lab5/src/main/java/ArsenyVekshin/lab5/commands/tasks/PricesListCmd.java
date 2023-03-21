package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;

public class PricesListCmd extends DataCmd{

    public PricesListCmd(Storage collection, OutputHandler outputHandler) {
        super("print_field_ascending_price", "prints list of sum from all collection", collection, outputHandler);
    }

    @Override
    public boolean execute(String[] args) {
        if(checkHelpFlag(args)) { help(); return true; }
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
