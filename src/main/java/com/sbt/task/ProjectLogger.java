package com.sbt.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectLogger {

    private static final Logger INSTANCE = LoggerFactory.getLogger(ProjectLogger.class);

    private ProjectLogger(){}

    public static void info(String s){
        INSTANCE.info(s);
    }

    public static Logger getInstance(){
        return INSTANCE;
    }

}
