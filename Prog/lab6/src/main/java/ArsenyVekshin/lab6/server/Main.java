package ArsenyVekshin.lab6.server;

import ArsenyVekshin.lab6.general.net.UdpManager;
import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.commands.CommandManager;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) throws SocketException, UnknownHostException {
        try{
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
            Storage collection = new Storage();

            /*if(args.length==0) net = new UdpManager();
            else net = new UdpManager(args[0], true);*/
            UdpManager net = new UdpManager();
            CommandManager commandManager = new CommandManager(collection, net);

            while (true){
                net.receiveCmd();
                commandManager.startExecuting();
                net.sendCmd();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}