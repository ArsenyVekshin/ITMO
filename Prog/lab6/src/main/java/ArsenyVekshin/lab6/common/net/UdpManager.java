package ArsenyVekshin.lab6.common.net;

import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.common.tools.ObjectSerializer;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Objects;

import static ArsenyVekshin.lab6.common.tools.DebugPrints.*;

public class UdpManager {
    public static final int SERVICE_PORT = 5000;

    public static ArrayList<CommandContainer> sendQueue = new ArrayList<>(); // queue of cmd to send
    public static ArrayList<CommandContainer> receivedQueue = new ArrayList<>(); // queue of answers which already received

    private static int callbackWaitList = 0;

    private boolean isServer = false;
    public InetSocketAddress targetAddress; //адрес сервера(еси клиент)
    public static InetSocketAddress userAddress; //адрес данного узла


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
                    "\n\tадрес клиента " + userAddress +
                    "\n\tадрес сервера " + targetAddress);
        }
    }

    /***
     * Отправка всех готовых пакетов адресатам
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
                debugPrintln0("-sent");
            } catch (IOException e) {
                debugPrintln0("-error");
                System.out.println("DEBUG: sending error " + e.getMessage() + " " + e.getClass());
            }
        }
        for(CommandContainer cmd: successfulSent) sendQueue.remove(cmd);
    }

    /***
     * Чтение 1 пакета через UDP
     */
    public void receiveCmd(){
        DatagramPacket inputPacket = new DatagramPacket(new byte[1024 * 1024], 1024 * 1024);
        debugPrint("receiving cmd ");

        try {
            channel.socket().receive(inputPacket);
        }catch (PortUnreachableException e) {
            debugPrintln0("-error");
            System.out.println("Сервер на данный момент недоступен");
            return;
        }catch (IOException e) {
            debugPrintln0("-error");
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
        groupReceiveProcessor();
    }

    /***
     * Добавление флага на отправку следующих N команд группой
     * @param _target целевой адрес
     * @param num количество пакетов в группе
     */
    public static void addGroupFlag(InetSocketAddress _target, int num){
        CommandContainer groupReceiveCmd = new CommandContainer("groupReceive", _target ,userAddress);
        groupReceiveCmd.setReturns(num);
        sendQueue.add(groupReceiveCmd);
    }

    /***
     * Запуск чтения группы команд от источника.
     * Запускается при получении ключ-команды "groupReceive".
     * Отрабатывает до тех пор, пока от источника не будет получено заданное количество команд.
     * Полученные команды будут записаны без пробелов.
     * Команды от других источников будут добавлены в очередь перед ними
     */
    public void groupReceiveProcessor(){
        if(Objects.equals(getLastReceivedCmd().getType(), "groupReceive")){
            System.out.println("founded flag for group-listening");
            System.out.println(getLastReceivedCmd().toString());
            ArrayList<CommandContainer> buffQueue = new ArrayList<>();
            CommandContainer groupCmd = getLastReceivedCmd();
            receivedQueue.remove(groupCmd);
            System.out.print("receiving group size " + (int) groupCmd.getReturns() + ":");
            while(buffQueue.size() < (int) groupCmd.getReturns()){
                receiveCmd();
                CommandContainer cmd = getLastReceivedCmd();
                if(cmd.getSource().equals(groupCmd.getSource())) {
                    buffQueue.add(cmd);
                    receivedQueue.remove(cmd);
                    //if(cmd.getReturns() == "finished") break;
                    System.out.print("*");
                }
                else System.out.print(".");
            }
            System.out.println("-done");
            receivedQueue.addAll(buffQueue);
        }
    }

    private CommandContainer getLastReceivedCmd(){
        return receivedQueue.get(receivedQueue.size() - 1);
    }

    public void targetStatus(){
        System.out.println("Ожидается ответ по " + callbackWaitList + " запросам");
    }

    public void queuesStatus(){
        if(receivedQueue.size()!=0){
            System.out.println("Получено:");
            for (CommandContainer cmd: receivedQueue){
                System.out.println("  " + cmd.toString());
            }
        }

        if(sendQueue.size()!=0){
            System.out.println("Готово к отправке:");
            for (CommandContainer cmd: sendQueue){
                System.out.println("  " + cmd.toString());
            }
        }
    }

}
