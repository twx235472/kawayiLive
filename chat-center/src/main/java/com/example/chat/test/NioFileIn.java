package com.example.chat.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileIn {

    public static void main(String[] args) {
        try {
            String test = "hello world";

            FileOutputStream fileInputStream = new FileOutputStream("a.txt");
            FileChannel fileChannel = fileInputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(test.getBytes());

            byteBuffer.flip();
            fileChannel.write(byteBuffer);

            fileInputStream.close();


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
