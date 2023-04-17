package ArsenyVekshin.lab6.general.net;

import ArsenyVekshin.lab6.general.CommandContainer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class UdpManager {

    public static ArrayList<CommandContainer> sendQueue = new ArrayList<>(); // queue of cmd to send
    public static ArrayList<CommandContainer> receivedQueue = new ArrayList<>(); // queue of answers which already received

    public final static int SERVICE_PORT = 50001;
    InetAddress targetIp ;
    DatagramSocket socket ;

    byte[] sendingDataBuffer = new byte[1024];
    byte[] receivingDataBuffer = new byte[1024];

    public UdpManager(byte[] ip, int port) throws UnknownHostException, SocketException {
        targetIp = InetAddress.getByAddress(ip);
        socket = new DatagramSocket(port, targetIp);
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
                DatagramPacket buff =  new DatagramPacket(
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
