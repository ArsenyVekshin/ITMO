package ArsenyVekshin.lab7.server;

import ArsenyVekshin.lab7.common.net.UdpManager;
import ArsenyVekshin.lab7.server.Database.DataBaseManager;
import ArsenyVekshin.lab7.server.commands.CommandManager;
import ArsenyVekshin.lab7.server.collection.Storage;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Server {
    public static InetSocketAddress serverAddress;
    public static UdpManager net;
    static DataBaseManager dataBaseManager;

    public static void main(String[] args){
        try{


            if(args.length>=2){
                serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), Integer.parseInt(args[0]));
                    dataBaseManager = new DataBaseManager(args[1]);
            }
            else
                System.exit(0);

            AuthManager authManager = new AuthManager(dataBaseManager);
            Storage collection = new Storage(dataBaseManager, authManager);

            dataBaseManager.setUserSet(authManager);
            dataBaseManager.setCollection(collection);



            net = new UdpManager(serverAddress);

            dataBaseManager.updateAll();
            CommandManager commandManager = new CommandManager(collection, net, authManager, dataBaseManager);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}