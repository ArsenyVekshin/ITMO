package ArsenyVekshin.lab6.client.commands;

import ArsenyVekshin.lab6.client.commands.tasks.*;
import ArsenyVekshin.lab6.client.commands.tasks.parents.Command;
import ArsenyVekshin.lab6.client.commands.tasks.parents.DataCmd;
import ArsenyVekshin.lab6.client.commands.tasks.parents.DialogueCmd;
import ArsenyVekshin.lab6.client.ui.InputHandler;
import ArsenyVekshin.lab6.client.ui.OutputHandler;
import ArsenyVekshin.lab6.client.ui.console.ConsoleInputHandler;
import ArsenyVekshin.lab6.client.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab6.client.ui.file.FileInputHandler;
import ArsenyVekshin.lab6.client.utils.builder.ObjTree;
import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.common.net.UdpManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static ArsenyVekshin.lab6.client.Main.*;
import static ArsenyVekshin.lab6.client.ui.DataFirewall.filterInputString;
import static ArsenyVekshin.lab6.common.tools.FilesTools.getAbsolutePath;

public class CommandManager {

    private UdpManager udpManager ;
    private InputHandler inputHandler;
    private OutputHandler outputHandler;

    private ObjTree objTree;

    private ArrayList<String> parsedScripts = new ArrayList<>();

    private Map<String, Command> commands = new HashMap<>();


    /**
     * default cmd-manager constructor
     * @param inputHandler stream for input
     * @param outputHandler stream for output
     */
    public CommandManager(InputHandler inputHandler, OutputHandler outputHandler, UdpManager udpManager, ObjTree objTree ){
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.udpManager = udpManager;
        this.objTree = objTree;
        init();
    }

    private void init(){
        commands.put("info", new CollectionInfoCmd(outputHandler));
        commands.put("show", new ShowCollectionCmd(outputHandler));
        commands.put("clear", new ClearCollectionCmd(outputHandler));
        commands.put("load", new LoadCollectionCmd(outputHandler));
        commands.put("remove_all_by_unit_of_measure", new RemoveByUnitOfMeasureCmd(outputHandler));
        commands.put("sum_of_price", new SumOfPriceCmd(outputHandler));
        commands.put("print_field_ascending_price", new PricesListCmd(outputHandler));

        commands.put("add", new AddElementCmd(outputHandler, inputHandler, objTree));
        commands.put("update", new UpdateElementByIDCmd(outputHandler, inputHandler, objTree));
        commands.put("remove_by_id", new RemoveElementByIDCmd(outputHandler, inputHandler));
        commands.put("insert_at", new InsertElementOnCmd(outputHandler, inputHandler, objTree));
        commands.put("add_if_max", new AddIfMaxCmd(outputHandler, inputHandler, objTree));
        commands.put("remove_greater", new RemoveGreaterCmd(outputHandler, inputHandler, objTree));
    }

    /**
     * execute script func
     * @param path script-file location
     * @throws StreamBrooked
     */
    public void executeScript(String path) throws StreamBrooked {
        path = getAbsolutePath(path);
//        System.out.println("DEBUG: begin executing script " + path);
        if(parsedScripts.contains(path)){
            outputHandler.println("Sorry you can't execute this script recursive");
            outputHandler.println("Execution blocked, returned to cli-mode");
            parsedScripts.clear();
        }
        else {
            parsedScripts.add(path);
            try {
//                System.out.println("DEBUG: begin executing script " + path);
                inputHandler = new FileInputHandler(path);
                changeGlobalStreams(inputHandler, outputHandler);
                startExecuting();
            } catch (Exception e) {
                outputHandler.printErr(e.getMessage());
            }
        }
        inputHandler = new ConsoleInputHandler();
        changeGlobalStreams(inputHandler, outputHandler);
    }

    /**
     * while true cycle with cmd read->parse->execute
     * @throws StreamBrooked
     */
    public void startExecuting() throws StreamBrooked {
        while (true) {
            System.out.println("hasnextline = " + inputHandler.hasNextLine());
            if(!inputHandler.hasNextLine()) break;

            try {
                String raw = inputHandler.get();
                if(inputHandler instanceof FileInputHandler) System.out.println("> " + raw);
                else{
                    udpManager.receiveCmd();
                    processServerCallback();
                    udpManager.queuesStatus();
                }
                if(raw.isEmpty() || raw.isBlank()) {
                    continue;
                }

                raw = filterInputString(raw);
                CommandContainer command = new CommandContainer(raw, net.userAddress, net.targetAddress);

//                System.out.println("DEBUG:");
//                System.out.println(command.toString());

                executeCommand(command);
                System.out.println("DEBUG: execute stg");
                if (!(inputHandler instanceof FileInputHandler)){
                   udpManager.sendCmd();
                    udpManager.receiveCmd();
                    processServerCallback();
                    udpManager.queuesStatus();
                }
            }
            catch (Exception e) {
                outputHandler.println(e.getMessage() + " " + e.getClass());
            }
        }
        if(inputHandler instanceof FileInputHandler) System.out.println("Ввод из файла завершен. Закрываю чтение.");
        else System.out.println("Ввод завершен. Закрываю программу.");
    }

    /**
     * help table printer
     * @throws StreamBrooked
     */
    private void help() throws StreamBrooked {
        for (Command cmd : commands.values()){
            cmd.help();
        }
        outputHandler.println("""
                >execute_script {path}
                    supports only your filesystem
                    read file as multiline cmd queue
                """);

        outputHandler.println("Вы можете вызвать подробную информацию по команде при помощи ключа -h\n");
    }

    /**
     * execute parsed cmd
     * @param cmd parsed cmd-container
     * @throws StreamBrooked
     */
    public void executeCommand(CommandContainer cmd) throws StreamBrooked {
        if(cmd.getType() == null) return;

        if(!commands.containsKey(cmd.getType())){
            if(cmd.getType().contains("execute_script")) {
                executeScript(cmd.getArgs().get(0));
            }

           else if(cmd.getType().contains("help")) {
                help();
                return;
            }
            else {
                throw new IllegalArgumentException("Данной команды не существует");
            }
        }
        commands.get(cmd.getType()).execute(cmd);
        if(!cmd.getArgs().contains("h")) udpManager.sendQueue.add(cmd);
    }

    private  <T extends Command> void changeGlobalStreams(InputHandler inputHandler, OutputHandler outputHandler){
        for( Command cmd : commands.values()) {
            if(cmd instanceof DataCmd) ((DataCmd) cmd).setOutputStream(outputHandler);
            if(cmd instanceof DialogueCmd) ((DialogueCmd) cmd).setInputStream(inputHandler);
        }
    }

    public void processServerCallback(){
        if(udpManager.receivedQueue.isEmpty()) return;

        for(CommandContainer cmd: udpManager.receivedQueue){
            try{
                if(cmd.isNeedToRecall()) {
                    executeCommand(cmd);
                    udpManager.sendQueue.add(cmd);
                }
            } catch (StreamBrooked e) {
                System.out.println(e.getMessage());
            }
        }
    }



}
