package com.sbt.task;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
	    //адрес файла
        String fileName = "downloadList.txt";
        String directoryName = "G://java_projects//FileDownloader//test//new";
        String threadCount = "3";
        String speedLimit = "2000";

        String[] userArgs = {fileName, directoryName, threadCount, speedLimit};

        FileDownloaderInitializer fileDownloaderInitializer = new FileDownloaderInitializer(userArgs);

//        File dir = new File(directoryName);
//        dir.mkdirs();
//        File newF = new File(dir.getPath() + System.getProperty("file.separator") + "new.txt");
//        System.out.println(dir.getPath());
//        try {
//            newF.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
