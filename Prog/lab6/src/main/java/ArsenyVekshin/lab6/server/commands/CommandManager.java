package ArsenyVekshin.lab6.server.commands;

import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.commands.tasks.*;
import ArsenyVekshin.lab6.server.ui.InputHandler;
import ArsenyVekshin.lab6.server.ui.OutputHandler;
import ArsenyVekshin.lab6.server.ui.console.ConsoleInputHandler;
import ArsenyVekshin.lab6.server.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab6.server.ui.file.FileInputHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static ArsenyVekshin.lab6.server.tools.FilesTools.getAbsolutePath;
import static ArsenyVekshin.lab6.server.ui.DataFirewall.filterInputString;

public class CommandManager {

    InputHandler inputHandler;
    OutputHandler outputHandler;

    private final String logFilePath = "";
    OutputHandler logFile ;
    private ArrayList<String> parsedScripts = new ArrayList<>();

    private Map<String, Command> commands = new HashMap<>();


    /**
     * default cmd-manager constructor
     * @param collection collection manager
     * @param inputHandler stream for input
     * @param outputHandler stream for output
     */
    public CommandManager(Storage collection, InputHandler inputHandler, OutputHandler outputHandler){
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;

        init(collection);
    }

    private void init(Storage collection){
        commands.put("info", new CollectionInfoCmd(collection, outputHandler));
        commands.put("show", new ShowCollectionCmd(collection, outputHandler));
        commands.put("clear", new ClearCollectionCmd(collection, outputHandler));
        commands.put("save", new SaveCollectionCmd(collection, outputHandler));
        commands.put("load", new LoadCollectionCmd(collection, outputHandler));
        commands.put("remove_all_by_unit_of_measure", new RemoveByUnitOfMeasureCmd(collection, outputHandler));
        commands.put("sum_of_price", new SumOfPriceCmd(collection, outputHandler));
        commands.put("print_field_ascending_price", new PricesListCmd(collection, outputHandler));

        commands.put("add", new AddElementCmd(collection, outputHandler, inputHandler, Storage.productTree));
        commands.put("update", new UpdateElementByIDCmd(collection, outputHandler, inputHandler, Storage.productTree));
        commands.put("remove_by_id", new RemoveElementByIDCmd(collection, outputHandler, inputHandler));
        commands.put("insert_at", new InsertElementOnCmd(collection, outputHandler, inputHandler, Storage.productTree));
        commands.put("add_if_max", new AddIfMaxCmd(collection, outputHandler, inputHandler, Storage.productTree));
        commands.put("remove_greater", new RemoveGreaterCmd(collection, outputHandler, inputHandler));

        commands.put("exit", new ExitCmd(collection, outputHandler));
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
        commands.get(args[0]).execute(args);
        //logFile.println(args.toString());
    }

    private  <T extends Command> void changeGlobalStreams(InputHandler inputHandler, OutputHandler outputHandler){
        for( Command cmd : commands.values()) {
            if(cmd instanceof DataCmd) ((DataCmd) cmd).setOutputStream(outputHandler);
            if(cmd instanceof DialogueCmd) ((DialogueCmd) cmd).setInputStream(inputHandler);
        }
    }
}
