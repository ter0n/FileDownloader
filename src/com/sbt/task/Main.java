package com.sbt.task;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<String> readFileLines(String fileName){
        ArrayList<String> fileLines = new ArrayList<>();
        try {
            //перенести эту строку в try(сюда!)
            FileReader fr = new FileReader(fileName);
            Scanner scanner = new Scanner(fr);

            while(scanner.hasNextLine()){
                fileLines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileLines;
    }

    public static void main(String[] args) {
	    //адрес файла
        String fileName = "downloadList.txt";



        ArrayList<String> filesPath = readFileLines(fileName);
        for(String path: filesPath){
            System.out.println(path);
        }

        int speedLimit = 3; //Kb
        int threadCount = 3;

        MultiThreadedFileLoader mtfl = new MultiThreadedFileLoader(speedLimit, threadCount);
        long startMillis, endMillis;//время начала и конца чтения байтов
        long workTimeMillis; // оставшееся до секунды время чтения байтов
        startMillis = System.currentTimeMillis();
        int workCode = mtfl.loadFiles(filesPath);
        endMillis = System.currentTimeMillis();
        workTimeMillis = endMillis - startMillis; //вычитаем из секунды время чтения байтов
        System.out.println("All time: " + workTimeMillis);
        System.out.println(workCode);


        //переделываем строку в URL
//        BufferedReader br;
//        String s;
//        try {
//            URL filePathURL = new URL(filePath);
//            System.out.println(filePathURL);
//            br = new BufferedReader(new InputStreamReader(filePathURL.openConnection().getInputStream()));
//            while((s = br.readLine())!=null){
//                System.out.println(s);
//            }
//            br.close();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
