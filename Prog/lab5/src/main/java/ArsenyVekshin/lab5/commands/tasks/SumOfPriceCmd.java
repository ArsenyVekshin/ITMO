package ArsenyVekshin.lab5.commands.tasks;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.collection.data.UnitOfMeasure;
import ArsenyVekshin.lab5.collection.exceptions.WrongCmdParam;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;

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
