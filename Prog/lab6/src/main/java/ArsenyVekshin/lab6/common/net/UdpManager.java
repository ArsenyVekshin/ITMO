package ArsenyVekshin.lab6.common.net;

import ArsenyVekshin.lab6.common.CommandContainer;
import ArsenyVekshin.lab6.common.tools.ObjectSerializer;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

import static ArsenyVekshin.lab6.common.tools.DebugPrints.*;

public class UdpManager {
    public static final int SERVICE_PORT = 5000;

    public static ArrayList<CommandContainer> sendQueue = new ArrayList<>(); // queue of cmd to send
    public static ArrayList<CommandContainer> receivedQueue = new ArrayList<>(); // queue of answers which already received

    private static int callbackWaitList = 0;

    private final int bufferSize = 20*1024;
    //private ByteBuffer  messageBuff = ByteBuffer.allocate(bufferSize);

    private boolean isServer = false;
    public InetSocketAddress targetAddress; //адрес сервера(еси клиент)
    public static InetSocketAddress userAddress; //адрес данного узла

    DatagramChannel channel ;

    Selector selector;

    public UdpManager(InetSocketAddress userAddress) throws IOException {
        this(userAddress, userAddress);
    }

    public UdpManager(InetSocketAddress userAddress, InetSocketAddress targetAddress) throws IOException {
        this.userAddress = userAddress;
        this.targetAddress = targetAddress;
        isServer = (targetAddress == userAddress);

        channel = DatagramChannel.open();
        //channel.socket().setSoTimeout(100);
        channel.configureBlocking(false);

        channel.setOption(StandardSocketOptions.SO_RCVBUF, bufferSize);
        channel.setOption(StandardSocketOptions.SO_SNDBUF, bufferSize);

        if (isServer){
            try {
                channel.bind(userAddress);
            } catch (BindException e){
                System.out.println("ERROR: выбранный адрес недоступен, попробуйте снова с другими настройками сети");
                System.exit(0);
            }
            System.out.println("Сервер запущен: " + userAddress);
        }
        else{
            channel.connect(targetAddress);
            this.userAddress = (InetSocketAddress) channel.getLocalAddress();
            System.out.println("Клиент запущен" +
                    "\n\tадрес клиента " + this.userAddress +
                    "\n\tадрес сервера " + this.targetAddress);
        }

        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);
    }

    /***
     * Отправка всех готовых пакетов адресатам
     */
    public void sendCmd(){
        if(sendQueue.size()==0) return;
        debugPrintln("commands num to send " + sendQueue.size());
        ArrayList<CommandContainer> successfulSent = new ArrayList<>();

        for(CommandContainer cmd: sendQueue){
            try{
                if(cmd.getSource() == null || cmd.getTarget()==null){
                    successfulSent.add(cmd);
                    continue;
                }

                ByteBuffer messageBuff = ByteBuffer.wrap(ObjectSerializer.serializeObject(cmd));

                if(isServer) {
                    channel.send(messageBuff, cmd.getSource());
                }
                else {
                    channel.send(messageBuff, cmd.getTarget());
                }
                successfulSent.add(cmd);
                System.out.println("Отправлен пакет размера " + messageBuff.array().length);

            } catch (Exception e) {
                System.out.println("Ошибка при отправке пакета " + e.getMessage() + " " + e.getClass());
            }
        }
        sendQueue.removeAll(successfulSent);
    }

    /***
     * Чтение 1 пакета через UDP
     */
    public void receiveCmd(){
        try{
            selector.selectNow();

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                if(key.isReadable()){
                    ByteBuffer messageBuff = ByteBuffer.allocate(bufferSize);

                    channel.receive(messageBuff);
                    System.out.println("Получен пакет размера: " + messageBuff.array().length);

                    CommandContainer cmd = (CommandContainer) ObjectSerializer.deserializeObject(messageBuff.array());
                    receivedQueue.add(cmd);
                    groupReceiveProcessor();
                }
            }

        }catch (Exception e) {
            debugPrintln0("-error");
            debugPrintln("Ошибка получения пакета: " + e.getMessage() + " " + e.getClass());
            e.printStackTrace();
        }
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

            long start = new Date().getTime();
            while(buffQueue.size() < (int) groupCmd.getReturns() ||
                    (new Date().getTime() - start)>1000){
                try {
                    receiveCmd();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if(getLastReceivedCmd()==null) continue;
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
        if(receivedQueue.size()==0) return null;
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