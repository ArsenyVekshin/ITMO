package ArsenyVekshin.lab6.general.net;

import ArsenyVekshin.lab6.general.CommandContainer;

import javax.print.DocFlavor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.ArrayList;

public class UdpManager {

    public final static int SERVICE_PORT = 50001;
    public static ArrayList<CommandContainer> sendQueue = new ArrayList<>(); // queue of cmd to send
    public static ArrayList<CommandContainer> receivedQueue = new ArrayList<>(); // queue of answers which already received

    private boolean isServer = false;
    public InetAddress targetIp ;
    private DatagramSocket socket ;

    byte[] sendingDataBuffer = new byte[1024];
    byte[] receivingDataBuffer = new byte[1024];

    public UdpManager(){
        isServer = true;
    }

    public UdpManager(String ip, boolean isServer) throws UnknownHostException, SocketException {
        targetIp = InetAddress.getByName(ip);
        this.isServer = isServer;
    }

    /***
     * Чтение пакета команд от всех источников
     */
    public void receiveCmd() {
        DatagramPacket buff =  new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
        try{
            socket = new DatagramSocket(SERVICE_PORT);
            while(true){
                socket.receive(buff);
                byte[] rawData = buff.getData();
                if(isEmptyMes(rawData)) break;
                CommandContainer newCmd = new CommandContainer();
                ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(rawData));
                newCmd = (CommandContainer) stream.readObject();
                receivedQueue.add(newCmd);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        socket.close();
    }

    /***
     * Отправить все команды из очереди по назначению (адресат указан в команде)
     */
    public void sendCmd(){
        if(sendQueue.size()==0) return;

        try {
            socket = new DatagramSocket(SERVICE_PORT);
        } catch (SocketException e) {
            System.out.println(e.getMessage());
            return;
        }

        for(CommandContainer cmd: sendQueue){
            try{
                DatagramPacket buff;
                if(isServer)
                    buff =  new DatagramPacket(
                            sendingDataBuffer,
                            sendingDataBuffer.length,
                            cmd.getSource());
                else
                    buff =  new DatagramPacket(
                            sendingDataBuffer,
                            sendingDataBuffer.length,
                            cmd.getTarget());
                socket.send(buff);

                System.out.println("DEBUG: cmd " + cmd.getType() + " - sent");
                sendQueue.remove(cmd);
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        socket.close();
    }

    private boolean isEmptyMes(byte[] data){
        if(data.length==0 || data == null) return true;
        for(byte s: data)
            if(s != 0) return true;
        return false;
    }

}
