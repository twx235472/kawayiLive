package com.example.chat.testForNIO.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileOut {

    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("a.txt");
            FileChannel fileChannel = fileInputStream.getChannel();

            FileOutputStream fileOutputStream = new FileOutputStream("b.txt");
            FileChannel fileOutChannel = fileOutputStream.getChannel();


            ByteBuffer byteBuffer = ByteBuffer.allocate(2);

            while (true){
                byteBuffer.clear();
                int read = fileChannel.read(byteBuffer);
                if(read == -1){
                    break;
                }
                byteBuffer.flip();
                fileOutChannel.write(byteBuffer);
            }

            fileInputStream.close();
            fileOutputStream.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
