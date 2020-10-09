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
//        String filePath = "https://github.com/ter0n/JSchool/blob/master/home_work9/src/com/company/Factorial.java";
//        String filePath = "https://raw.githubusercontent.com/ter0n/JSchool/master/home_work9/src/com/company/Factorial.java";
        String filePath = "https://introcs.cs.princeton.edu/java/data/california-gov.txt";

        String fileName = "downloadList.txt";
        ArrayList<String> filesPath = readFileLines(fileName);
        for(String path: filesPath){
            System.out.println(path);
        }

        MultiThreadedFileLoader mtfl = new MultiThreadedFileLoader();
        int workCode = mtfl.loadFiles(filesPath);
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
