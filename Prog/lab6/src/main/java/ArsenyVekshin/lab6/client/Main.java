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

import static ArsenyVekshin.lab6.common.net.UdpManager.SERVICE_PORT;
import static ArsenyVekshin.lab6.common.tools.DebugPrints.*;


public class Main {
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
            /*System.out.println("""
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
                    """);*/

            System.out.println("""
                    .| : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : : :”-'\\,,
                    ..\\: : : : : : : : : : :'\\: : : : : : : : : : : : : :~,,: : : : : : : : : “~-.,_
                    ...\\ : : : : : : : : : : :\\: /: : : : : : : : : : : : : : : “,: : : : : : : : : : :"~,_
                    ... .\\: : : : : : : : : : :\\|: : : : : : : : :_._ : : : : : : \\: : : : : : : : : : : : :”- .
                    ... ...\\: : : : : : : : : : \\: : : : : : : : ( O ) : : : : : : \\: : : : : : : : : : : : : : '\\._
                    ... ... .\\ : : : : : : : : : '\\': : : : : : : :"*": : : : : : : :|: : : : : : : : : : : : : : : |0)
                    ... ... ...\\ : : : : : : : : : '\\: : : : : : : : : : : : : : : :/: : : : : : : : : : : : : : : /""
                    ... ... .....\\ : : : : : : : : : \\: : : : : : : : : : : : : ,-“: : : : : : : : : : : : : : : :/
                    ... ... ... ...\\ : : : : : : : : : \\: : : : : : : : : _=" : : : : : ',_.: : : : : : : :,-“
                    ... ... ... ... \\,: : : : : : : : : \\: :"”'~—-~”" : : : : : : : : : : : : = :"”~~
                    """);

            if (args.length < 2){
                userAddress = new InetSocketAddress(InetAddress.getLocalHost(), SERVICE_PORT+1);
                serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), SERVICE_PORT);
                debugPrintln("0 param mode");
            }
            else if(args.length == 4){
                userAddress = new InetSocketAddress(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
                serverAddress = new InetSocketAddress(InetAddress.getByName(args[2]),Integer.parseInt(args[3]));
                debugPrintln("4 param mode");
            }
            else if(args.length == 2){
                userAddress = new InetSocketAddress(InetAddress.getLocalHost(), Integer.parseInt(args[0]));
                serverAddress = new InetSocketAddress(InetAddress.getLocalHost(),Integer.parseInt(args[1]));
                debugPrintln("2 param mode");
            }
            debugPrintln("client begins at "+ userAddress + " with server at " + serverAddress);
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