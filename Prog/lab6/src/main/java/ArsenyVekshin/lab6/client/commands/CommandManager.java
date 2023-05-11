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

import java.io.EOFException;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static ArsenyVekshin.lab6.client.ui.DataFirewall.filterInputString;
import static ArsenyVekshin.lab6.common.tools.FilesTools.getAbsolutePath;

public class CommandManager {

    private UdpManager udpManager ;
    private InputHandler inputHandler;
    private OutputHandler outputHandler;

    private ObjTree objTree;

    private ArrayList<String> parsedScripts = new ArrayList<>();

    private Map<String, Command> commands = new HashMap<>();

    Selector selector ;

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
     * @param cmd command which calls script
     * @throws StreamBrooked
     */
    public void executeScript(CommandContainer cmd) throws StreamBrooked {
        String path = getAbsolutePath(cmd.getArgs().get(0));
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
        if(cmd.getKeys().contains("g")) sendAsGroup();
    }

    public void startExecuting(boolean isSendDelay) throws StreamBrooked {
        while (true) {
            try {
                //Обработка ввода в консоль
                if(inputHandler.available()){
                    String raw =  inputHandler.get();
                    if(inputHandler instanceof FileInputHandler) System.out.println("> " + raw);

                    if(raw.isEmpty() || raw.isBlank()) continue;
                    raw = filterInputString(raw);
                    CommandContainer command = new CommandContainer(raw, udpManager.userAddress, udpManager.targetAddress);
                    executeCommand(command);
                }

                //отправка сообщения на сервер, если не чтение из файла
                if (!isSendDelay) {
                    udpManager.sendCmd();
                    udpManager.receiveCmd();    //читаем с канала
                    processServerCallback();    //обрабатываем полученные ответы на "повторный вызов"
                    udpManager.queuesStatus();  //печатаем статус очередей отправка-получение
                    udpManager.receivedQueue.clear();   //удаляем уже напечатанные команды из очереди полученных
                }
            }
            catch (EOFException e) {
                break;
            }
            catch (Exception e) {
                outputHandler.println(e.getMessage() + " " + e.getClass());
            }
        }
        if(inputHandler instanceof FileInputHandler) System.out.println("Ввод из файла завершен. Закрываю чтение.");
        else System.out.println("Ввод завершен. Закрываю программу.");
    }

    /**
     * while true cycle with cmd read->parse->execute
     * @throws StreamBrooked
     */
    public void startExecuting() throws StreamBrooked {
        startExecuting(false);
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
                    possible keys:
                        -g send this script as group 
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
                executeScript(cmd);
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

    public void sendAsGroup(){
        CommandContainer groupReceiveCmd = new CommandContainer("groupReceive", udpManager.userAddress, udpManager.targetAddress);
        groupReceiveCmd.setReturns(udpManager.sendQueue.size());
        udpManager.sendQueue.add(0, groupReceiveCmd);
        udpManager.queuesStatus();
        udpManager.sendCmd();
    }

    public void processServerCallback(){
        if(udpManager.receivedQueue.isEmpty()) return;

        for(CommandContainer cmd: udpManager.receivedQueue){
            try{
                if(cmd.isNeedToRecall() && cmd.getErrors()==null) {
                    executeCommand(cmd);
                    udpManager.sendQueue.add(cmd);
                }
            } catch (StreamBrooked e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
