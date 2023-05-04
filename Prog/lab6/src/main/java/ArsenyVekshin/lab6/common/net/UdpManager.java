package ArsenyVekshin.lab6.common.net;

import ArsenyVekshin.lab6.common.CommandContainer;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

import static ArsenyVekshin.lab6.common.tools.DebugPrints.*;

public class UdpManager {

    public final static int SERVICE_PORT = 50001;
    public static ArrayList<CommandContainer> sendQueue = new ArrayList<>(); // queue of cmd to send
    public static ArrayList<CommandContainer> receivedQueue = new ArrayList<>(); // queue of answers which already received

    private boolean isServer = false;
    public InetSocketAddress targetIp ;
    public InetSocketAddress userIp;


    ByteBuffer channelDataBuffer = ByteBuffer.allocate(65536);

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
        channel.bind(userIp);
        channel.socket().setSoTimeout(5000);

        if(!isServer) channel.connect(targetIp);
    }

    /***
     * Чтение пакета команд от всех источников
     */
    public void receiveCmd() {
        try{
            //debugPrint("receiving mes:");
            while(channelDataBuffer.hasRemaining()){
                //debugPrint0(".");
                channel.receive(channelDataBuffer);
                channelDataBuffer.flip();
                byte[] rawData = new byte[channelDataBuffer.remaining()];
                channelDataBuffer.get(rawData);
                if(isEmptyMes(rawData)) continue;

                ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(rawData));
                CommandContainer newCmd = (CommandContainer) stream.readObject();
                receivedQueue.add(newCmd);
                //debugPrint0("*");
            }
        } catch (Exception e) {
            if(e.getClass() == NullPointerException.class) return;
            debugPrintln("receiving error: " + e.getMessage() + e.getClass());
            return;
        }
        //debugPrintln0("done");
    }

    /***
     * Отправить все команды из очереди по назначению (адресат указан в команде)
     */
    public void sendCmd() throws IOException {
        if(sendQueue.size()==0) return;

        for(CommandContainer cmd: sendQueue){
            try{
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
                objStream.writeObject(cmd);
                objStream.flush();
                objStream.close();

                channelDataBuffer = ByteBuffer.wrap(byteStream.toByteArray());

                if(isServer){
                    channel.connect(cmd.getSource());
                    channel.send(channelDataBuffer, cmd.getSource());
                    channel.disconnect();
                }
                else{
                    channel.send(channelDataBuffer, cmd.getTarget());
                }


                debugPrintln("cmd " + cmd.getType() + " - sent");

            }
            catch (IOException e) {
                debugPrintln("send: " + e.getMessage() + e.getClass());
                return;
            }
        }
        sendQueue.clear();
    }

    private boolean isEmptyMes(byte[] data){
        if(data.length==0 || data == null) return true;
        for(byte s: data)
            if(s != 0) return true;
        return false;
    }

    public void queuesStatus(){
        System.out.println("QUEUES: to_send=" + sendQueue.size() + " received=" + receivedQueue.size() );
    }

}
