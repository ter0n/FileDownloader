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
        String fileName = "downloadList.txt";   //файл со списком фалов для загрузки
        String directoryName = "G://java_projects//FileDownloader//test//new";  // директория, в которую складывать сохраненные файлы
        String threadCount = "3";   // количество потоков
        String speedLimit = "2000"; // ограничение на скорость скачивания, которое будет делиться на количество потоков. Указывается в килобайтах, с ограничением не менее 2 килобайт на поток

        String[] userArgs = {fileName, directoryName, threadCount, speedLimit};

        FileDownloaderInitializer fileDownloaderInitializer = new FileDownloaderInitializer(userArgs);

    }
}
