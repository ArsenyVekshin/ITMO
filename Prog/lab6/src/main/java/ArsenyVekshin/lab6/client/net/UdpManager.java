package ArsenyVekshin.lab6.client.net;

import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.common.tools.ObjectSerializer;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

import static ArsenyVekshin.lab6.common.tools.DebugPrints.*;
import static ArsenyVekshin.lab6.common.tools.DebugPrints.debugPrintln;


/***
 * Client-type udp manager
 */
public class UdpManager {

    public static ArrayList<CommandContainer> sendQueue = new ArrayList<>(); // queue of cmd to send
    public static ArrayList<CommandContainer> receivedQueue = new ArrayList<>(); // queue of answers which already received

    private static int callbackWaitList = 0;

    private boolean isServer = false;
    public InetSocketAddress targetAddress; //адрес сервера(еси клиент)
    public InetSocketAddress userAddress; //адрес данного узла


    ByteBuffer channelDataBuffer = ByteBuffer.allocate(65536);

    DatagramChannel channel ;

    public UdpManager(InetSocketAddress userAddress) throws IOException {
        this(userAddress, userAddress);
    }

    public UdpManager(InetSocketAddress userAddress, InetSocketAddress targetAddress) throws IOException {
        this.userAddress = userAddress;
        this.targetAddress = targetAddress;
        isServer = (targetAddress == userAddress);

        channel = DatagramChannel.open();
        try {
            channel.bind(userAddress);
        } catch (BindException e){
            System.out.println("ERROR: выбранный адрес недоступен, попробуйте снова с другими настройками сети");
            System.exit(0);
        }

        if (isServer){
            System.out.println("Сервер запущен: " + userAddress);
        }
        else{
            channel.connect(targetAddress);
            channel.socket().setSoTimeout(5000);
            System.out.println("Клиент запущен" +
                    "\tадрес клиента " + userAddress +
                    "\tадрес сервера " + targetAddress);
        }
    }


    /***
     * Send all send-queue to the targets in datagram-format
     */
    public void sendCmd(){
        if(sendQueue.size()==0) return;
        debugPrintln("commands num to send " + sendQueue.size());
        ArrayList<CommandContainer> successfulSent = new ArrayList<>();
        DatagramPacket datagramPacket;

        for(CommandContainer cmd: sendQueue){
            try{
                debugPrint("sending cmd "+ cmd.getType() + " ");

                byte[] serializedCmd = ObjectSerializer.serializeObject(cmd);
                debugPrint0("-serialised");

                if(isServer) datagramPacket = new DatagramPacket(serializedCmd,
                            serializedCmd.length,
                            cmd.getSource()) ;
                else datagramPacket = new DatagramPacket(serializedCmd,
                        serializedCmd.length,
                        cmd.getTarget()) ;
                debugPrint0("-packet");

                channel.socket().send(datagramPacket);
                successfulSent.add(cmd);
                debugPrint0("-sent");
            } catch (IOException e) {
                debugPrintln0("-error");
                System.out.println("DEBUG: sending error " + e.getMessage());
            }
        }
        for(CommandContainer cmd: successfulSent) sendQueue.remove(cmd);
    }

    public void receiveCmd(){
        DatagramPacket inputPacket = new DatagramPacket(new byte[1024 * 1024], 1024 * 1024);
        debugPrint("receiving cmd ");

        try {
            channel.socket().receive(inputPacket);
        }catch (PortUnreachableException e) {
            debugPrintln("Сервер на данный момент недоступен");
            return;
        }catch (IOException e) {
            debugPrintln("Ошибка получения пакета: " + e.getMessage());
            return;
        }
        debugPrint0("-received");

        try{
            CommandContainer cmd = (CommandContainer) ObjectSerializer.deserializeObject(inputPacket.getData());
            receivedQueue.add(cmd);
            debugPrint0("-deSerialised");
        } catch (Exception e) {
            debugPrintln0("-error");
            debugPrintln("Ошибка чтения полученного пакета: " + e.getMessage());
        }
        debugPrintln0("-done");
    }

    public void targetStatus(){
        System.out.println("Ожидается ответ по " + callbackWaitList + " запросам");
    }

}
