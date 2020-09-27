package com.example.chat.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class NioCopy {
    public static void main(String[] args) {
        try {
            ServerSocketChannel open = ServerSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
            open.bind(inetSocketAddress);

            ByteBuffer[] buffers = new ByteBuffer[2];
            buffers[0] = ByteBuffer.allocate(5);
            buffers[1] = ByteBuffer.allocate(3);

            SocketChannel socketChannel = open.accept();


            RandomAccessFile rw = new RandomAccessFile("a.text", "rw");
            FileChannel channel = rw.getChannel();

            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
            map.put(3,(byte)'2');


            while (true){
                int byteLenth = 0;
                while(byteLenth < buffers.length){
                    long read = socketChannel.read(buffers);
                    byteLenth += read;
                }
                Arrays.asList(buffers).stream().map(buffer->buffer.position()).forEach(System.out::println);


                Arrays.asList(buffers).stream().forEach(buffer->buffer.flip());

                int letn = 0;
                while(letn < buffers.length){
                    long write = socketChannel.write(buffers);
                    letn += write;
                }

                Arrays.asList(buffers).stream().forEach(buffer->{
                    buffer.clear();
                });
            }



        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
