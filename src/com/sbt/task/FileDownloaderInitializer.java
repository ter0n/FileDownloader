package com.sbt.task;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileDownloaderInitializer {

    FileDownloaderInitializer(String[] args){
        InputUserDataInterface inputDataHandler = new UserDataHandler(args);

        int speedLimit = inputDataHandler.getSpeedLimit();
        int threadCount = inputDataHandler.getThreadCount();
        String directoryName = inputDataHandler.getDirectoryName();
        ArrayList<String> filesPath = inputDataHandler.getUrlList();

        FileDownloader mtfl = new MultiThreadedFileLoader(speedLimit, threadCount, directoryName);

        long startMillis, endMillis;//время начала и конца загрузки
        long workTimeMillis; // Время работы

        startMillis = System.currentTimeMillis();

        int workCode = mtfl.loadFiles(filesPath);

        endMillis = System.currentTimeMillis();

        workTimeMillis = endMillis - startMillis; //вычитаем из секунды время чтения байтов
        System.out.println("Work time: " + workTimeMillis);
        System.out.println(workCode);


//        } catch (URISyntaxException e) {
//            System.out.println("Невозможно преобразовать путь : " + directoryName + " в URI");
//            System.out.println(e.getMessage());
//        } catch (IOException e) {
//            System.out.println("Проблемы с созданием директории для каталога: " + directoryName);
//            System.out.println(e.getMessage());
//        }



    }



}
