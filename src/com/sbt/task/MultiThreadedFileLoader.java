package com.sbt.task;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.FileOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;


public class MultiThreadedFileLoader implements FileDownloader {

    @Override
    public int loadFiles(ArrayList<String> filesPaths) {

        //только один файл качаем и записываем
        String fileName = "california-gov.txt";

        try {
            URL url = new URL(filesPaths.get(0));
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            FileChannel fileChannel = fileOutputStream.getChannel();
//            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, 2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
