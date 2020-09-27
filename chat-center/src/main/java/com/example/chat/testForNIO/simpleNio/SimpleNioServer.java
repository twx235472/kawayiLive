package com.example.chat.testForNIO.simpleNio;

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
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6000);
        open.socket().bind(inetSocketAddress);

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
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int read = channel.read(byteBuffer);
                    if(read == -1){
                        System.out.println("client is disconnect!,channel= " + channel.hashCode());
                        channel.close();
                        continue;
                    }
                    System.out.println("get Message: "+new String(byteBuffer.array())+" channel: "+channel.hashCode());
                }
                iterator.remove();
            }
        }
    }
}
