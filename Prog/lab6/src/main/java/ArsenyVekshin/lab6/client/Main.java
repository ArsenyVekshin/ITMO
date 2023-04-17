package ArsenyVekshin.lab6.client;

import ArsenyVekshin.lab6.client.ui.InputHandler;
import ArsenyVekshin.lab6.client.ui.OutputHandler;
import ArsenyVekshin.lab6.client.ui.console.ConsoleInputHandler;
import ArsenyVekshin.lab6.client.ui.console.ConsoleOutputHandler;
import ArsenyVekshin.lab6.client.utils.builder.ObjTree;
import ArsenyVekshin.lab6.general.collectionElems.data.Product;
import ArsenyVekshin.lab6.general.net.UdpManager;
import ArsenyVekshin.lab6.client.commands.CommandManager;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import static ArsenyVekshin.lab6.general.net.UdpManager.SERVICE_PORT;

public class Main {
    public static UdpManager net;
    public static InetSocketAddress serverAddress;
    public static InetSocketAddress userAddress;

    public static InputHandler inputHandler;
    public static OutputHandler outputHandler;

    public static void main(String[] args) {
        try {
            System.out.println("""
                    ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                    ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠾⢻⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                    ⠀⣀⠀⠀⠀⢀⣠⡴⠖⠚⠋⠉⠉⠀⠀⠉⠛⠲⣤⣀⣴⠶⢶⡄⠀⠀⠀⠀⠀
                    ⠀⣿⠙⠓⠖⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⡿⠙⣯⠁⣷⠀⠀⠀⠀⠀
                    ⠀⢸⡄⠀⠀⣠⣤⣤⡀⠀⠀⠀⠀⠀⠀⢀⠐⠗⠊⠃⠀⠀⢀⡟⠀⠀⠀⠀⠀
                    ⠀⣠⠗⠂⣴⢿⡜⣈⣇⢸⡦⠀⠘⠛⠛⠉⢀⣀⣠⣤⠶⢶⣾⣁⣤⡄⠀⠀⠀
                    ⢰⠏⠀⠀⠁⠘⠋⣩⣇⣀⣤⣤⣶⣶⣻⣯⣽⡷⠟⢻⡟⠋⠻⣄⠀⢳⣄⠀⠀
                    ⣿⣤⡤⠶⠶⠚⣻⣿⠿⠿⣟⠋⠙⣧⡀⠀⠘⣧⠀⠀⢻⣄⣀⣹⣦⣤⣿⣷⣄
                    ⠀⠀⠀⠀⠀⠀⣏⠻⣆⠀⢘⣷⣄⣨⣷⣤⣤⣾⣿⣿⡿⠿⠿⠟⠛⠛⠋⠁⣿
                    ⠀⠀⠀⠀⠀⠀⠘⣦⠘⣏⣭⠽⠷⠞⠛⠛⠋⠉⠁⠀⣀⣠⠤⠶⠶⠚⠛⠋⠉
                    ⠀⠀⠀⠀⠀⠀⠀⠈⢷⣿⡤⠤⠤⠴⠴⠶⠶⠒⠛⠋⠉⠀⠀⠀⠀⠀⠀⠀⠀
                    """);

            if (args.length == 0){
                userAddress = new InetSocketAddress(InetAddress.getLocalHost(), SERVICE_PORT);
                serverAddress = new InetSocketAddress(InetAddress.getByName("192.168.31.254"), SERVICE_PORT);
                net = new UdpManager(serverAddress, false);
            }
            else{
                userAddress = new InetSocketAddress(InetAddress.getLocalHost(),SERVICE_PORT);
                serverAddress = new InetSocketAddress(InetAddress.getByName(args[0]),SERVICE_PORT);
                net = new UdpManager(serverAddress, false);
            }
            //UdpManager net = new UdpManager();

            inputHandler = new ConsoleInputHandler();
            outputHandler = new ConsoleOutputHandler();

            CommandManager commandManager = new CommandManager(inputHandler, outputHandler, net, new ObjTree(Product.class));

            while (true) {
                net.receiveCmd();
                commandManager.startExecuting();
                net.sendCmd();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}