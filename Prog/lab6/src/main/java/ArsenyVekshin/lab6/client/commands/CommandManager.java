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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


import static ArsenyVekshin.lab6.client.ui.DataFirewall.filterInputString;
import static ArsenyVekshin.lab6.client.tools.FilesTools.getAbsolutePath;

public class CommandManager {

    InputHandler inputHandler;
    OutputHandler outputHandler;

    ObjTree objTree;

    private final String logFilePath = "";
    OutputHandler logFile ;
    private ArrayList<String> parsedScripts = new ArrayList<>();

    private Map<String, Command> commands = new HashMap<>();


    /**
     * default cmd-manager constructor
     * @param inputHandler stream for input
     * @param outputHandler stream for output
     */
    public CommandManager(InputHandler inputHandler, OutputHandler outputHandler){
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
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
//        System.out.println("DEBUG: execution by stream started at " + inputHandler.getClass().getName());
        while (inputHandler.hasNextLine()) {
            String command = inputHandler.get();
            if(inputHandler instanceof FileInputHandler) System.out.println("> " + command);
//            System.out.println("DEBUG: \"" + command + "\" stream=" + inputHandler.getClass().getName());
            if(command.isEmpty() || command.isBlank()) {
                continue;
            }
            try {
                command = filterInputString(command);
                //System.out.println(command.split(" "));
                executeCommand(command.split(" "));
            } catch (NoSuchElementException e) {
                break;
            }
            catch (Exception e) {
                outputHandler.printErr(e.getMessage());
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
                    read file as multiline cmd queue
                """);

        outputHandler.println("Вы можете вызвать подробную информацию по команде при помощи ключа -h\n");
    }

    /**
     * execute parsed cmd
     * @param args parsed cmd
     * @throws StreamBrooked
     */
    public void executeCommand(String[] args) throws StreamBrooked {
        if(args == null) return;

        //DEBUG
        /*System.out.print("DEBUG: received cmd: \"");
        for(String a : args) System.out.print(a + "\", \"");
        System.out.println(" ");*/

        if(!commands.containsKey(args[0])){
            if(args[0].contains("execute_script")) {
                executeScript(args[1]);
            }

            if(args[0].contains("help")) {
                help();
                return;
            }
            else {
                throw new IllegalArgumentException("Данной команды не существует");
            }
        }
        //commands.get(args[0]).execute(args);
        //logFile.println(args.toString());
    }

    private  <T extends Command> void changeGlobalStreams(InputHandler inputHandler, OutputHandler outputHandler){
        for( Command cmd : commands.values()) {
            if(cmd instanceof DataCmd) ((DataCmd) cmd).setOutputStream(outputHandler);
            if(cmd instanceof DialogueCmd) ((DialogueCmd) cmd).setInputStream(inputHandler);
        }
    }
}
