package com.example.chat.testForNIO.test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioSelector {
    public static void main(String[] args) {
        try{
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(6000));
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

            while (true){
                if (selector.select(1000) == 0){
                    System.out.println("server wait 1s");
                    continue;
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                while(iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    if (next.isAcceptable()){
                        //确定有连接以后，生成一个soketchannel
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        //必须设置为非阻塞，因为你注册时候就是使用的非阻塞模型
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                    }
                    if(next.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) next.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) next.attachment();
                        int read = socketChannel.read(byteBuffer);
                        System.out.println("get message: "+new String(byteBuffer.array()));
                        byteBuffer.clear();
                    }
                    iterator.remove();
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
