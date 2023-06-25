package ArsenyVekshin.lab7.server.commands;

import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.net.UdpManager;
import ArsenyVekshin.lab7.common.ui.InputHandler;
import ArsenyVekshin.lab7.common.ui.console.ConsoleInputHandler;
import ArsenyVekshin.lab7.common.ui.file.FileInputHandler;
import ArsenyVekshin.lab7.server.AuthManager;
import ArsenyVekshin.lab7.server.Database.DataBaseManager;
import ArsenyVekshin.lab7.server.collection.Storage;
import ArsenyVekshin.lab7.server.commands.tasks.*;
import ArsenyVekshin.lab7.server.commands.tasks.parents.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


import static ArsenyVekshin.lab7.common.tools.DebugPrints.debugPrintln;
import static ArsenyVekshin.lab7.common.ui.DataFirewall.filterInputString;

public class CommandManager implements Runnable{
    InputHandler inputHandler = new ConsoleInputHandler();
    private UdpManager udpManager ;
    private AuthManager authManager;

    private Map<String, Command> commands = new HashMap<>();

    private DataBaseManager dataBaseManager;


    /**
     * default cmd-manager constructor
     * @param collection collection manager
     * @param udpManager link to default net-manager
     */
    public CommandManager(Storage collection, UdpManager udpManager, AuthManager authManager, DataBaseManager dataBaseManager){
        this.udpManager = udpManager;
        this.authManager = authManager;
        this.dataBaseManager = dataBaseManager;
        dataBaseManager.setUserSet(authManager);
        init(collection);
        new Thread(this).start();
    }

    private void init(Storage collection){
        commands.put("info", new CollectionInfoCmd(collection));
        commands.put("show", new ShowCollectionCmd(collection, udpManager));
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

        commands.put("new_user", new AddNewUserCmd(authManager));
        commands.put("login", new LoginUserCmd(authManager));
    }

    /**
     * while true cycle with cmd read->parse->execute
     */
    public void startExecuting(){
        CommandContainer cmd;
        while (true){
            try {
                if (inputHandler.available()) {
                    String raw = inputHandler.get();
                    if (inputHandler instanceof FileInputHandler) System.out.println("> " + raw);

                    if (raw.isEmpty() || raw.isBlank()) continue;
                    raw = filterInputString(raw);
                    cmd = new CommandContainer(raw, null, null);
                    if(cmd.getType() == "save" || cmd.getType() == "load"){
                        commands.get(cmd.getType()).execute(cmd);
                    }
                }

                cmd = udpManager.getCommand();
                if (cmd != null) {
                    debugPrintln("got " + cmd.toString());
                    debugPrintln("authorized = "); //+ authManager.isAuthorised(cmd.getUser())
                    if (commands.containsKey(cmd.getType())){
                        if(authManager.isAuthorised(cmd.getUser())
                                || Objects.equals(cmd.getType(), "login")
                                || Objects.equals(cmd.getType(), "new_user")){
                            commands.get(cmd.getType()).execute(cmd);
                            debugPrintln("send " + cmd.toString());

                            if (cmd.isNeedToRecall() || !cmd.getReturns().equals("done"))
                                udpManager.addCallBack(cmd);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println(e.getMessage());
            }
        }
    }


    @Override
    public void run() {
        startExecuting();
    }
}
