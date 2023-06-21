package ArsenyVekshin.lab7.client;

import ArsenyVekshin.lab7.client.commands.CommandManager;
import ArsenyVekshin.lab7.common.builder.ObjTree;
import ArsenyVekshin.lab7.common.collectionElems.data.Product;
import ArsenyVekshin.lab7.common.net.UdpManager;
import ArsenyVekshin.lab7.common.ui.InputHandler;
import ArsenyVekshin.lab7.common.ui.OutputHandler;
import ArsenyVekshin.lab7.common.ui.console.ConsoleInputHandler;
import ArsenyVekshin.lab7.common.ui.console.ConsoleOutputHandler;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import static ArsenyVekshin.lab7.common.tools.DebugPrints.*;


public class Client {
    public static AuthManager authManager;
    public static UdpManager net;
    public static InetSocketAddress serverAddress;
    public static InetSocketAddress userAddress;

    public static InputHandler inputHandler;
    public static OutputHandler outputHandler;

    /***
     *
     * @param args serverPort
     */
    public static void main(String[] args) {
        try {
            if(args.length > 0){
                userAddress   = new InetSocketAddress(InetAddress.getLocalHost(), Integer.parseInt(args[0]));
                serverAddress = new InetSocketAddress(InetAddress.getLocalHost(),Integer.parseInt(args[0]));
            }
            else throw new RuntimeException();
            net = new UdpManager(userAddress, serverAddress);

            inputHandler = new ConsoleInputHandler();
            outputHandler = new ConsoleOutputHandler();

            authManager = new AuthManager();
            CommandManager commandManager = new CommandManager(inputHandler, outputHandler, net, new ObjTree(Product.class), authManager);
            debugPrintln("init finished");

            commandManager.startExecuting();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}