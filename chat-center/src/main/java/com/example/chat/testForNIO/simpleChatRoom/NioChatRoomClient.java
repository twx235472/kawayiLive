package com.example.chat.testForNIO.simpleChatRoom;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NioChatRoomClient {

    private Selector selector;
    private SocketChannel socketChannel;
    private static final InetSocketAddress address = new InetSocketAddress("127.0.0.1",6666);

    public NioChatRoomClient(){
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(address);
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) throws Exception{
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(byteBuffer);
    }

    public void reciveMsg() throws Exception{
            int select = selector.select(1000);
            if (select > 0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    if (next.isReadable()){
                        SocketChannel channel = (SocketChannel)next.channel();
                        channel.configureBlocking(false);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int read = channel.read(byteBuffer);
                        if(read == -1){
                            System.out.println("disconnnect from server!");
                            next.cancel();
                            next.channel().close();
                            return;
                        }
                        System.out.println(channel.getRemoteAddress() +" say:" + new String(byteBuffer.array()));
                    }
                    iterator.remove();
                }
            }
    }

    public static void main(String[] args) throws Exception{
        NioChatRoomClient nioChatRoomClient = new NioChatRoomClient();

        new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        nioChatRoomClient.reciveMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try{
                        Thread.currentThread().sleep(3000);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            nioChatRoomClient.sendMsg(s);
        }
    }
}
