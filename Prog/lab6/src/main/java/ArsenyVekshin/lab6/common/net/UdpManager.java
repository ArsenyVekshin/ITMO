package ArsenyVekshin.lab6.common.net;

import ArsenyVekshin.lab6.common.CommandContainer;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

public class UdpManager {

    public final static int SERVICE_PORT = 50001;
    public static ArrayList<CommandContainer> sendQueue = new ArrayList<>(); // queue of cmd to send
    public static ArrayList<CommandContainer> receivedQueue = new ArrayList<>(); // queue of answers which already received

    private boolean isServer = false;
    public InetSocketAddress targetIp ;
    public InetSocketAddress userIp;


    ByteBuffer channelDataBuffer = ByteBuffer.allocate(2048);

    DatagramChannel channel ;

    public UdpManager(){
        isServer = true;
    }


    public UdpManager(InetSocketAddress userIp, InetSocketAddress targetIp, boolean isServer) throws IOException {
        this.targetIp = targetIp;
        this.isServer = isServer;
        this.userIp = userIp;
        this.channel = DatagramChannel.open();
        channel.configureBlocking(false);
    }

    /***
     * Чтение пакета команд от всех источников
     */
    public void receiveCmd() throws IOException {
        try{
            channel.bind(userIp);
            channel.configureBlocking(false);
            System.out.print("DEBUG: receiving mes:");
            while(channelDataBuffer.hasRemaining()){
                System.out.print(".");
                channel.receive(channelDataBuffer);
                channelDataBuffer.flip();
                byte[] rawData = new byte[channelDataBuffer.remaining()];
                channelDataBuffer.get(rawData);
                if(isEmptyMes(rawData)) continue;

                ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(rawData));
                CommandContainer newCmd = (CommandContainer) stream.readObject();
                receivedQueue.add(newCmd);
                System.out.print("*");
            }
        } catch (Exception e) {
            if(e.getClass() == NullPointerException.class) return;
            System.out.println("receive: " + e.getMessage() + e.getClass());
            e.printStackTrace();
        }
        /*try{
            channel.close();
        }catch (Exception e) {
            if(e.getClass() == NullPointerException.class) return;
            System.out.println("receive: " + e.getMessage() + e.getClass());
        }*/
        System.out.println("done");
    }

    /***
     * Отправить все команды из очереди по назначению (адресат указан в команде)
     */
    public void sendCmd() throws IOException {
        if(sendQueue.size()==0) return;
        System.out.print("DEBUG: sending mes:");
        try {
            channel.bind(targetIp);
            //channel.bind(new InetSocketAddress(InetAddress.getLocalHost(), SERVICE_PORT));
        } catch (Exception e) {
            System.out.println("send: " + e.getMessage());
            return;
        }

        for(CommandContainer cmd: sendQueue){
            try{

                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
                objStream.writeObject(cmd);
                objStream.flush();
                objStream.close();

                channelDataBuffer = ByteBuffer.wrap(byteStream.toByteArray());

                if(isServer)
                    channel.send(channelDataBuffer, cmd.getSource());
                else
                    channel.send(channelDataBuffer, cmd.getTarget());


                System.out.println("DEBUG: cmd " + cmd.getType() + " - sent");
                sendQueue.remove(cmd);
                System.out.print("*");
            }
            catch (IOException e) {
                System.out.println("send: " + e.getMessage() + e.getClass());
            }
        }
        /*try{
            channel.close();
        }catch (Exception e) {
            System.out.println("send: " + e.getMessage() + e.getClass());
        }*/
        System.out.println("done");

    }

    private boolean isEmptyMes(byte[] data){
        if(data.length==0 || data == null) return true;
        for(byte s: data)
            if(s != 0) return true;
        return false;
    }

}
