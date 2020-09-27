package com.example.chat.testForNIO.simpleChatRoom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOChatRoomServer {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private InetSocketAddress inetSocketAddress;
    private final static Integer port = 6666;

    public NIOChatRoomServer(){
        //初始化
        try {
            serverSocketChannel = ServerSocketChannel.open();
            inetSocketAddress = new InetSocketAddress(port);
            serverSocketChannel.socket().bind(inetSocketAddress);
            serverSocketChannel.configureBlocking(false);

            selector = Selector.open();

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("finish the server init!.....");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listen(){
        while (true){
            try {
                int select = selector.select(1000);
                if(select > 0){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while(iterator.hasNext()){
                        SelectionKey next = iterator.next();
                        if(next.isAcceptable()){
                            SocketChannel accept = serverSocketChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector,SelectionKey.OP_READ);
                            System.out.println("client "+accept.getRemoteAddress()+" connect!...");
                        }
                        if(next.isReadable()){
                            try {
                                //read msg
                                SocketChannel channel = (SocketChannel)next.channel();
                                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                int read = channel.read(byteBuffer);
                                if(read == -1){
                                    next.cancel();
                                    channel.close();
                                }
                                String msg = new String(byteBuffer.array());
                                System.out.println("get msg from "+channel.getRemoteAddress()+" msg is:"+msg);

                                //send msg to other client
                                sendMsgToOther(msg,channel);
                            }catch (IOException e){
                                next.cancel();
                                next.channel().close();
                            }
                        }
                        iterator.remove();
                    }
                }
            }catch (Exception e){

            }
        }
    }
    public void sendMsgToOther(String msg,SocketChannel self) throws IOException{
        System.out.println("send msg to other client");
        String info = self.getRemoteAddress() +" say: "+msg;

        Set<SelectionKey> keys = selector.keys();
        Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()){
            SelectionKey next = iterator.next();
            Channel channel = next.channel();
            if(channel instanceof SocketChannel && channel != self){
                //send to them
                ((SocketChannel) channel).write(ByteBuffer.wrap(msg.getBytes()));
            }
        }
    }

    public static void main(String[] args) {
        NIOChatRoomServer nioChatRoomServer = new NIOChatRoomServer();
        nioChatRoomServer.listen();
    }
}
