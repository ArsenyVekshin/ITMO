package ArsenyVekshin.lab5.commands;

import ArsenyVekshin.lab5.collection.Storage;
import ArsenyVekshin.lab5.commands.tasks.*;
import ArsenyVekshin.lab5.ui.InputHandler;
import ArsenyVekshin.lab5.ui.OutputHandler;
import ArsenyVekshin.lab5.ui.console.ConsoleInputHandler;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab5.ui.file.FileInputHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static ArsenyVekshin.lab5.ui.DataFirewall.filterInputString;

public class CommandManager {

    InputHandler inputHandler;
    OutputHandler outputHandler;

    private final String logFilePath = "";
    OutputHandler logFile ;

    private Map<String, Command> commands = new HashMap<>();



    public CommandManager(Storage collection, InputHandler inputHandler, OutputHandler outputHandler){
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;

        commands.put("info", new CollectionInfoCmd(collection, outputHandler));
        commands.put("show", new ShowCollectionCmd(collection, outputHandler));
        commands.put("clear", new ClearCollectionCmd(collection, outputHandler));
        commands.put("save", new SaveCollectionCmd(collection, outputHandler));
        commands.put("load", new LoadCollectionCmd(collection, outputHandler));
        commands.put("remove_all_by_unit_of_measure", new RemoveByUnitOfMeasureCmd(collection, outputHandler));
        commands.put("sum_of_price", new SumOfPriceCmd(collection, outputHandler));
        commands.put("print_field_ascending_price", new PricesListCmd(collection, outputHandler));

        commands.put("add", new AddElementCmd(collection, outputHandler, inputHandler));
        commands.put("update", new UpdateElementByIDCmd(collection, outputHandler, inputHandler));
        commands.put("remove_by_id", new RemoveElementByIDCmd(collection, outputHandler, inputHandler));
        commands.put("insert_at", new InsertElementOnCmd(collection, outputHandler, inputHandler));
        commands.put("add_if_max", new AddIfMaxCmd(collection, outputHandler, inputHandler));
        commands.put("remove_greater", new RemoveGreaterCmd(collection, outputHandler, inputHandler));

        commands.put("exit", new ExitCmd(collection, outputHandler));
    }

    public void executeScript(String path) throws StreamBrooked {
        try {
            inputHandler = new FileInputHandler(path);
            startExecuting();
        } catch (Exception e) {
            outputHandler.printErr(e.getMessage());
        }
        inputHandler = new ConsoleInputHandler();
    }

    public void startExecuting() throws StreamBrooked {
        while (inputHandler.hasNextLine()) {
            String command = inputHandler.get();
            System.out.println("DEBUG: \"" + command + "\"");
            if(command.isEmpty() || command.isBlank()) {
                continue;
            }
            try {
                command = filterInputString(command);

                executeCommand(command.split(" "));
            } catch (IllegalArgumentException | StreamBrooked e) {
                outputHandler.printErr(e.getMessage());
            }
        }
    }

    private void help() throws StreamBrooked {
        for (Command cmd : commands.values()){
            cmd.help();
        }
        outputHandler.println("Вы можете вызвать подробную информацию по команде при помощи ключа -h\n");
    }

    public void executeCommand(String[] args) throws StreamBrooked {
        if(args == null) return;
        if(args[0] == "execute_script ") executeScript(args[1]);

        //DEBUG
        System.out.print("DEBUG: received cmd: \"");
        for(String a : args) System.out.print(a + "\", \"");
        System.out.println(" ");

        if(!commands.containsKey(args[0])){
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
}
