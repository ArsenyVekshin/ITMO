package ArsenyVekshin.lab6.server.commands;

import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.common.net.UdpManager;
import ArsenyVekshin.lab6.common.ui.InputHandler;
import ArsenyVekshin.lab6.common.ui.console.ConsoleInputHandler;
import ArsenyVekshin.lab6.common.ui.file.FileInputHandler;
import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.commands.tasks.*;
import ArsenyVekshin.lab6.server.commands.tasks.parents.Command;
import ArsenyVekshin.lab6.common.ui.OutputHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static ArsenyVekshin.lab6.common.tools.DebugPrints.*;
import static ArsenyVekshin.lab6.common.ui.DataFirewall.filterInputString;

public class CommandManager {
    InputHandler inputHandler = new ConsoleInputHandler();
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
        commands.put("show", new ShowCollectionCmd(collection, udpManager.sendQueue));
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
        while (true){
            try {
                if (inputHandler.available()) {
                    String raw = inputHandler.get();
                    if (inputHandler instanceof FileInputHandler) System.out.println("> " + raw);

                    if (raw.isEmpty() || raw.isBlank()) continue;
                    raw = filterInputString(raw);
                    CommandContainer command = new CommandContainer(raw, null, null);
                    if(command.getType() == "save" || command.getType() == "load"){
                        commands.get(command.getType()).execute(command);
                    }
                }
                udpManager.receiveCmd();
                udpManager.queuesStatus();
                for (CommandContainer cmd : udpManager.receivedQueue) {
                    if (commands.containsKey(cmd.getType())) {
                        commands.get(cmd.getType()).execute(cmd);
                        udpManager.sendQueue.add(cmd);
                    }
                }
                udpManager.receivedQueue.clear();

                udpManager.queuesStatus();
                udpManager.sendCmd();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }



}
