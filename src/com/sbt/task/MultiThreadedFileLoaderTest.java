package com.sbt.task;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MultiThreadedFileLoaderTest {

    @Test
    public void tryCreateDirectory() {
        int speedLimit = 1500;
        int threadCount = 3;
        String directoryName = "G://java_projects//FileDownloader//test";
        ArrayList<String> filesPath = new ArrayList<>();
        filesPath.add("https://introcs.cs.princeton.edu/java/data/synsets.txt");
        filesPath.add("https://introcs.cs.princeton.edu/java/data/pi-10million.txt");
        filesPath.add("https://introcs.cs.princeton.edu/java/data/chromosome11.txt");

        MultiThreadedFileLoader mtfl = new MultiThreadedFileLoader(speedLimit, threadCount, directoryName);

        int workCode = mtfl.loadFiles(filesPath);

        File dir = new File(directoryName);
        boolean expected = dir.exists();
        boolean actual = true;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void loadedFilesCheck(){

        String testFileDir = "G://java_projects//FileDownloader//files_for_test";

        int speedLimit = 1500;
        int threadCount = 1;
        String directoryName = "G://java_projects//FileDownloader//download";
        String fileName = "synsets.txt";
        ArrayList<String> filesPath = new ArrayList<>();
        filesPath.add("https://introcs.cs.princeton.edu/java/data/synsets.txt");

        MultiThreadedFileLoader mtfl = new MultiThreadedFileLoader(speedLimit, threadCount, directoryName);

        int workCode = mtfl.loadFiles(filesPath);

        try {
            List<String> expectedLines = Files.readAllLines(Paths.get(testFileDir + System.getProperty("file.separator") + fileName));
            List<String> actualLines =  Files.readAllLines(Paths.get(directoryName + System.getProperty("file.separator") + fileName));
            Assert.assertEquals(expectedLines, actualLines);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getSpeedLimit() {

        int speedLimit = 1500;
        int threadCount = 3;
        String directoryName = "G://java_projects//FileDownloader//test";

        MultiThreadedFileLoader mtfl = new MultiThreadedFileLoader(speedLimit, threadCount, directoryName);

        int bufSize = 1024;
        int expected = mtfl.getSpeedLimit();
        int threadSpeedLimit = speedLimit * bufSize / threadCount - bufSize;

        Assert.assertEquals(threadSpeedLimit, expected);
    }

    @Test
    public void badSpeedLimit() {

        int speedLimit = 3;
        int threadCount = 3;
        String directoryName = "G://java_projects//FileDownloader//test";

        MultiThreadedFileLoader mtfl = new MultiThreadedFileLoader(speedLimit, threadCount, directoryName);

        int bufSize = 1024;
        int expected = mtfl.getSpeedLimit();
        int threadSpeedLimit = speedLimit * bufSize / threadCount - bufSize;

        //Assert.assertEquals(threadSpeedLimit, expected);
    }

}