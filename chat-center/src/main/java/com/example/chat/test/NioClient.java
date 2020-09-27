package com.example.chat.test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",6000);

        if (socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("wait");
            }
        }
        //connet sucess
        String str = "hello,wttang!";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());

        socketChannel.write(byteBuffer);
        System.in.read();
        socketChannel.write(byteBuffer);
    }
}
