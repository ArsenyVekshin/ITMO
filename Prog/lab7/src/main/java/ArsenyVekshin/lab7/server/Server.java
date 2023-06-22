package ArsenyVekshin.lab7.server;

import ArsenyVekshin.lab7.common.builder.ObjTree;
import ArsenyVekshin.lab7.common.collectionElems.data.Product;
import ArsenyVekshin.lab7.common.net.UdpManager;
import ArsenyVekshin.lab7.common.security.Encoder;
import ArsenyVekshin.lab7.server.Database.DataBaseManager;
import ArsenyVekshin.lab7.server.commands.CommandManager;
import ArsenyVekshin.lab7.server.collection.Storage;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import static ArsenyVekshin.lab7.common.net.UdpManager.SERVICE_PORT;
import static ArsenyVekshin.lab7.common.tools.DebugPrints.debugPrintln;

public class Server {
    public static InetSocketAddress serverAddress;
    public static UdpManager net;

    public static void main(String[] args){
        try{
            System.out.println(Encoder.getSHAString("user1"));
            System.out.println(Encoder.getSHAString("user2"));
            System.out.println(Encoder.getSHAString("user3"));
            AuthManager authManager = new AuthManager();
            DataBaseManager dataBaseManager = new DataBaseManager();
            if(args.length==0){
                serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), SERVICE_PORT);
            }
            else{
                serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), Integer.parseInt(args[0]));
                if(args.length>1)
                    dataBaseManager.setDatabasePass(args[1]);
            }

            dataBaseManager.setUserSet(authManager.getUserSet());
            Storage collection = new Storage(dataBaseManager, authManager);

            net = new UdpManager(serverAddress);

            dataBaseManager.updateAll();
            CommandManager commandManager = new CommandManager(collection, net, authManager, dataBaseManager);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}