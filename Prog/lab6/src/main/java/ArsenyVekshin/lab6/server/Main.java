package ArsenyVekshin.lab6.server;

import ArsenyVekshin.lab6.common.net.UdpManager;
import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.commands.CommandManager;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import static ArsenyVekshin.lab6.common.net.UdpManager.SERVICE_PORT;
import static ArsenyVekshin.lab6.common.tools.DebugPrints.debugPrintln;

public class Main {
    public static InetSocketAddress serverAddress;
    public static UdpManager net;

    public static void main(String[] args){
        try{
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

            Storage collection = new Storage();
            collection.init();

            if(args.length==0){
                serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), SERVICE_PORT);
            }
            else{
                serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), Integer.parseInt(args[0]));
            }

            net = new UdpManager(serverAddress);
            debugPrintln("server begins at " + serverAddress);
            CommandManager commandManager = new CommandManager(collection, net);

            commandManager.startExecuting();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}