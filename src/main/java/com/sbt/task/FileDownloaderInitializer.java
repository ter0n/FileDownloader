package com.sbt.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;

public class FileDownloaderInitializer {

//    private Logger logger = LoggerFactory.getLogger(FileDownloaderInitializer.class);

    FileDownloaderInitializer(String[] args){
        InputUserDataInterface inputDataHandler = new UserDataHandler(args);

        int speedLimit = inputDataHandler.getSpeedLimit();
        int threadCount = inputDataHandler.getThreadCount();
        String directoryName = inputDataHandler.getDirectoryName();
        ArrayList<String> filesPath = inputDataHandler.getUrlList();

        FileDownloader multiThreadedFileLoader = new MultiThreadedFileLoader(speedLimit, threadCount, directoryName);

        //время начала загрузки
        long startMillis;
        //время окончания загрузки
        long endMillis;
        // Время работы
        long workTimeMillis;

        startMillis = System.currentTimeMillis();

        int workCode = multiThreadedFileLoader.loadFiles(filesPath);

        endMillis = System.currentTimeMillis();

        workTimeMillis = endMillis - startMillis; //вычитаем из секунды время чтения байтов


        ProjectLogger.info("Work time: " + workTimeMillis);
        ProjectLogger.info(String.valueOf(workCode));

    }



}
