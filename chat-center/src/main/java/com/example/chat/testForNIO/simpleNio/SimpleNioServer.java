package com.example.chat.testForNIO.simpleNio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class SimpleNioServer {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel open = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        open.socket().bind(inetSocketAddress);
        open.configureBlocking(false);

        Selector selector = Selector.open();
        open.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            //wait for client to connect
            if(selector.select(1000) == 0){
                System.out.println("wait fot connect 1 second");
            }
            //some events happened
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                if(next.isAcceptable()){
                    SocketChannel accept = open.accept();
                    accept.configureBlocking(false);
                    accept.register(selector,SelectionKey.OP_READ);
                    System.out.println("one client connect, channel: "+accept.hashCode());
                }
                if(next.isReadable()){
                    SocketChannel channel = (SocketChannel) next.channel();
                    try{
                        ByteBuffer byteBuffer = ByteBuffer.allocate(2014);
                        int read = channel.read(byteBuffer);
                        if(read == -1){
                            System.out.println("client out!");
                            next.cancel();
                            channel.socket().close();
                            continue;
                        }
                        System.out.println("get Message: "+new String(byteBuffer.array())+" channel: "+channel.hashCode());

                    }catch (IOException e)
                    {
                        System.out.println("client out but not correct");
                        next.cancel();
                        channel.socket().close();
                    }
                }
                iterator.remove();
            }
        }
    }
}
