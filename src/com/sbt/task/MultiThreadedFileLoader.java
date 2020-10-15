package com.sbt.task;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;


public class MultiThreadedFileLoader implements FileDownloader {

    @Override
    public int loadFiles(ArrayList<String> filesPaths) {

        //только один файл качаем и записываем
        String fileName = "dickens.txt";

//        try {
//            URL url = new URL(filesPaths.get(4));
//            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
//            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
//            FileChannel fileChannel = fileOutputStream.getChannel();
//            long count;
//            long startMillis, endMillis;//время начала и конца чтения байтов
//            long remainMillis; // оставшееся до секунды время чтения байтов
//            long secondInMillis = 1000;
//            startMillis = System.currentTimeMillis();
//            count = fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
//            endMillis = System.currentTimeMillis();
//            remainMillis = endMillis - startMillis; //вычитаем из секунды время чтения байтов
//            System.out.println(count);
//            System.out.println(remainMillis);
////            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, 2);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        int speed = 2500000 - 1024;
        int bufferLength = 1024;

        try {
            URL url = new URL(filesPaths.get(4));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            byte buffer[] = new byte[bufferLength];
            long loadedBytesSum = 0;
            int bytesCountRead = 0;
            long startMillis, endMillis;//время начала и конца чтения байтов
            long remainMillis; // оставшееся до секунды время чтения байтов
            long secondInMillis = 1000;
            while( bytesCountRead != -1){
                startMillis = System.currentTimeMillis();
                while((bytesCountRead != -1) && (loadedBytesSum <= speed)) {
                    bytesCountRead = bufferedInputStream.read(buffer, 0, bufferLength);
                    if(bytesCountRead > 0)
                        fileOutputStream.write(buffer, 0, bytesCountRead);
                    loadedBytesSum += bytesCountRead;
                }
                endMillis = System.currentTimeMillis();

                remainMillis = secondInMillis - (endMillis - startMillis); //вычитаем из секунды время чтения байтов

                if (remainMillis > 0)
                    Thread.sleep(remainMillis);
//                System.out.println("Time: " + remainMillis + "; bytes were read: " + loadedBytesSum);
                loadedBytesSum = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        try {
//            URL url = new URL(filesPaths.get(1));
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        FileUtils
//
//        FileUtils.copyURLToFile(
//                new URL(FILE__URL),
//                new File(FILE__NAME),
//                CONNECT_TIMEOUT,
//                READ_TIMEOUT);



        return 0;
    }

}
