package com.epam.lab.mentoring.watcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WatcherThread extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(WatcherThread.class);

    private boolean watch = true;

    @Override
    public void run() {
        LOGGER.info("Artifact watching thread started.");
        while(watch) {
            // do watch
        }
        LOGGER.info("Artifact watching thread stopped.");
    }

    public void stopThread() {
        watch = false;
    }
}
