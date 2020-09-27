package com.example.chat.testForNIO.simpleNio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SimpleNioClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",6666);

        socketChannel.configureBlocking(false);

        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("no server for connect!");
            }
        }
        String msg = "test";
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());

        socketChannel.write(byteBuffer);
        int read = System.in.read();
//        socketChannel.close();
    }
}
