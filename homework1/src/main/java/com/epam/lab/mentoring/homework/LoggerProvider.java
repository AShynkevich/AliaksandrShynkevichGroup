package com.epam.lab.mentoring.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerProvider {

    public static Logger getLogger() {
        final Throwable t = new Throwable();
        t.fillInStackTrace();
        final String clazz = t.getStackTrace()[1].getClassName();
        return LoggerFactory.getLogger(clazz);
    }
}
