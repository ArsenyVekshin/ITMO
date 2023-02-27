package commands.tasks;

import collection.Storage;
import collection.data.UnitOfMeasure;
import collection.exceptions.WrongCmdParam;
import ui.exeptions.StreamBrooked;

public class SumOfPriceCmd extends DataCmd{

    public SumOfPriceCmd(Storage collection) {
        super("sum_of_price", "prints sum of prices from all collection", collection);
    }

    @Override
    public boolean execute(String arg) {
        if(checkHelpFlag(arg)) { help(); return true; }
        try {

            outputStream.println(String.valueOf(collection.getPricesSum()));

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
                > sum_of_price
                prints sum of prices from all collection
                PARAMS:
                -h / --help\tShow this menu
                    """);
        } catch (StreamBrooked e) {
            e.printStackTrace();
        }
    }
}
