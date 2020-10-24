package com.sbt.task;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDataHandler implements InputUserDataInterface{

    private ArrayList<String> urlList;
    private int threadCount;
    private int speedLimit;
    private String directoryName;
    /*
    На вход подается массив строк размером 4. Он должен содержать следующую информацию:
    [0] -- название файла со списком файлов для скачивания
    [1] -- название каталога, в котором будут храниться файлы, которые загрузим
    [2] -- количество потоков
    [3] -- ограничение скорости загрузки, которое будет делиться на количество потоков, т.е. каждый поток получит одинаковое ограничение
    Если длина массива не равна 4, то данные будут запрашиваться из консоли, т.е. их надо будет вводить вручную.
     */
    UserDataHandler(String[] args){
        if(args.length == 4){
            String urlListFileName = args[0];
            urlList = readFileLines(urlListFileName);
            directoryName = args[1];
            threadCount = Integer.parseInt(args[2]);
            speedLimit = Integer.parseInt(args[3]);
        } else {
            Scanner in = new Scanner(System.in);

            System.out.println("Введите название файла со списком фалйов, которые необходимо скачать: ");
            String urlListFileName = in.nextLine();
            urlList = readFileLines(urlListFileName);

            System.out.println("\nВведите название каталога, в котором будут храниться файлы: ");
            directoryName = in.nextLine();

            System.out.println("\nВведите количество потоков: ");
            threadCount = in.nextInt();

            System.out.println("\nВведите ограничение на скорость скачивания(Кб), данная скорость будет разделена на количество потоков: ");
            speedLimit = in.nextInt();
        }
    }


    @Override
    public ArrayList<String> getUrlList() {
        return urlList;
    }

    @Override
    public int getThreadCount() {
        return threadCount;
    }

    @Override
    public int getSpeedLimit() {
        return speedLimit;
    }

    @Override
    public String getDirectoryName() {
        return directoryName;
    }
    /*
     * Читаем файл со списком файлов для загрузки и возвращаем их список.
     */
    private ArrayList<String> readFileLines(String fileName){
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
}
