package com.sbt.task;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    public static void main(String[] args) {
        //файл со списком фалов для загрузки
        String fileName = "downloadList.txt";

        // директория, в которую складывать сохраненные файлы
        String directoryName = "test//new";

        // количество потоков
        String threadCount = "3";
        
        // ограничение на скорость скачивания, которое будет делиться на количество потоков.
        // Указывается в килобайтах, с ограничением не менее 2 килобайт на поток
        String speedLimit = "6000";

        String[] userArgs = {fileName, directoryName, threadCount, speedLimit};

        FileDownloaderInitializer fileDownloaderInitializer = new FileDownloaderInitializer(userArgs);


//        logger.info("Example log from {}", Main.class.getSimpleName());


    }
}
