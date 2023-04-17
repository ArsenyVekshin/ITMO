package ArsenyVekshin.lab6.client;

import ArsenyVekshin.lab6.general.net.UdpManager;
import ArsenyVekshin.lab6.server.collection.Storage;
import ArsenyVekshin.lab6.server.commands.CommandManager;

public class Main {
    public static UdpManager net;

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
            Storage collection = new Storage();

            if (args.length == 0) net = new UdpManager();
            else net = new UdpManager(args[0], true);
            //UdpManager net = new UdpManager();
            CommandManager commandManager = new CommandManager(collection, net);

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