package ArsenyVekshin.lab6.server.commands;

import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.common.net.UdpManager;
import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.commands.tasks.*;
import ArsenyVekshin.lab6.server.commands.tasks.parents.Command;
import ArsenyVekshin.lab6.server.ui.OutputHandler;

import java.util.HashMap;
import java.util.Map;
public class CommandManager {

    private UdpManager udpManager ;

    private final String logFilePath = "";
    OutputHandler logFile ;
    private Map<String, Command> commands = new HashMap<>();


    /**
     * default cmd-manager constructor
     * @param collection collection manager
     * @param udpManager link to default net-manager
     */
    public CommandManager(Storage collection, UdpManager udpManager){
        this.udpManager = udpManager;
        init(collection);
    }

    private void init(Storage collection){
        commands.put("info", new CollectionInfoCmd(collection));
        commands.put("show", new ShowCollectionCmd(collection));
        commands.put("clear", new ClearCollectionCmd(collection));
        commands.put("load", new LoadCollectionCmd(collection));
        commands.put("remove_all_by_unit_of_measure", new RemoveByUnitOfMeasureCmd(collection));
        commands.put("sum_of_price", new SumOfPriceCmd(collection));
        commands.put("print_field_ascending_price", new PricesListCmd(collection));

        commands.put("add", new AddElementCmd(collection));
        commands.put("update", new UpdateElementByIDCmd(collection));
        commands.put("remove_by_id", new RemoveElementByIDCmd(collection));
        commands.put("insert_at", new InsertElementOnCmd(collection));
        commands.put("add_if_max", new AddIfMaxCmd(collection));
        commands.put("remove_greater", new RemoveGreaterCmd(collection));

    }

    /**
     * while true cycle with cmd read->parse->execute
     */
    public void startExecuting(){
        for(CommandContainer cmd: udpManager.receivedQueue){
            if(commands.containsKey(cmd.getType())){
                commands.get(cmd.getType()).execute(cmd);
                udpManager.sendQueue.add(cmd);
            }
        }
    }
}
