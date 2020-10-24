package com.sbt.task;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.*;


public class MultiThreadedFileLoader implements FileDownloader {

    private ConcurrentLinkedQueue<String> concLinkedQueue;
    private CountDownLatch threadPoolLatch;
    private int speedLimit;
    private int threadCount;
    private String directoryName;
    private Path dir;


    private final int bufferSize = 1024; //in bytes
    private final int speedLimitConst = 500;
    private final int threadCountConst = 1;

    MultiThreadedFileLoader(int spdLimit, int thrdCount, String drctrName){
        setThreadCount(thrdCount);
        setSpeedLimit(spdLimit);
        directoryName = drctrName;
    }

    @Override
    public int loadFiles(ArrayList<String> filesPaths) {

        concLinkedQueue = new ConcurrentLinkedQueue<>(filesPaths);

        tryCreatePath(directoryName);

        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
        threadPoolLatch = new CountDownLatch(threadCount);
        for(int i = 0; i < threadCount; i++){
            threadPool.submit(new Loader());
        }

        threadPool.shutdown();

        try {
            threadPoolLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private void loadData(URL fileURL, String fileName){
        int speed = 1000000 - 1024;
        int bufferLength = 1024;

        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileURL.openStream());
            String fullFileName = directoryName + System.getProperty("file.separator") + fileName;
            FileOutputStream fileOutputStream = new FileOutputStream(fullFileName);
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

                loadedBytesSum = 0;
            }
        } catch (IOException e) {
            System.out.println("Проблемы с соединение по url: " + fileURL.toString() + "\n" + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    //сделать выброс исключения, если имя файла не найдено!!!!!!!!!!!!
    private String getFileNameFromFilePath(String filePath){
        int lastSlash = -1;
        lastSlash = filePath.lastIndexOf("/");
        String fileName = "";
        if(lastSlash > -1) {
            fileName = filePath.substring(lastSlash + 1, filePath.length());
        } else {
            System.out.println("В файле с url:\n" +
                    filePath + "\n не найдено имя, поэтому он будет пропущен!!!");
        }
        return fileName;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    private void setSpeedLimit(int speedLimit) {
        //делаем ограничение на скорость с рассчетом минимум 2Кб на один поток
        if (speedLimit >= threadCount * 2) {
            if (threadCount > 0)
                this.speedLimit = speedLimit * bufferSize / threadCount - bufferSize;
        } else {
            this.speedLimit = speedLimitConst;
            System.out.println("Пользовательская скорость не подходит для данного количества потоков.\n" +
                    "Поэтому была выставлена программная скорость:" + speedLimitConst + " * threadCount");
        }
    }

    private void setThreadCount(int threadCount) {
        if(threadCount > 0) {
            this.threadCount = threadCount;
        } else {
            this.threadCount = threadCountConst;
            System.out.println("Пользовательское количество потоков не подходит.\n" +
                    "Поэтому было выставлено программное количество потоков: " + threadCountConst);
        }
    }

    private class Loader implements Runnable{

        @Override
        public void run() {
            while(concLinkedQueue.isEmpty() != true) {
                String filePath = concLinkedQueue.poll();
                try {
                    URL url = new URL(filePath);
                    String fileName;
                    fileName = getFileNameFromFilePath(filePath);
                    //проверяем, что имя файла найдено
                    if(fileName.isEmpty() == false) {
//                        fileName = directoryName + "/" + fileName;
                        loadData(url, fileName);
                        System.out.println("Загрузка " + fileName + " закончена");
                    }
                } catch (MalformedURLException e) {
                    System.out.println("Проблемы с url: " + filePath + e.getMessage());
                }
            }
            threadPoolLatch.countDown();
        }
    }

    private void tryCreatePath(String path){

        File dir = new File(path);
        if(dir.exists() == false){
            dir.mkdirs();
        }
    }

}
