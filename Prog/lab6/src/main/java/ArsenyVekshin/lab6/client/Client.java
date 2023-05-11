package ArsenyVekshin.lab6.client;

import ArsenyVekshin.lab6.client.ui.InputHandler;
import ArsenyVekshin.lab6.client.ui.OutputHandler;
import ArsenyVekshin.lab6.client.ui.console.ConsoleInputHandler;
import ArsenyVekshin.lab6.client.ui.console.ConsoleOutputHandler;
import ArsenyVekshin.lab6.client.utils.builder.ObjTree;
import ArsenyVekshin.lab6.common.collectionElems.data.Product;
import ArsenyVekshin.lab6.common.net.UdpManager;
import ArsenyVekshin.lab6.client.commands.CommandManager;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import static ArsenyVekshin.lab6.common.net.UdpManager.SERVICE_PORT;
import static ArsenyVekshin.lab6.common.tools.DebugPrints.*;


public class Client {
    public static UdpManager net;
    public static InetSocketAddress serverAddress;
    public static InetSocketAddress userAddress;

    public static InputHandler inputHandler;
    public static OutputHandler outputHandler;

    /***
     *
     * @param args userIp userPort serverIp serverPort
     */
    public static void main(String[] args) {
        try {
            if(args.length == 2){
                userAddress   = new InetSocketAddress(InetAddress.getLocalHost(), Integer.parseInt(args[0]));
                serverAddress = new InetSocketAddress(InetAddress.getLocalHost(),Integer.parseInt(args[1]));
                debugPrintln("2 param mode");
            }
            else throw new RuntimeException();
            net = new UdpManager(userAddress, serverAddress);

            inputHandler = new ConsoleInputHandler();
            outputHandler = new ConsoleOutputHandler();

            CommandManager commandManager = new CommandManager(inputHandler, outputHandler, net, new ObjTree(Product.class));
            debugPrintln("init finished");

            commandManager.startExecuting();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}