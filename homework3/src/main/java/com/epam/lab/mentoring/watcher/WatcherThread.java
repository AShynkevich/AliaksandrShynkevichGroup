package com.epam.lab.mentoring.watcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;

public class WatcherThread extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(WatcherThread.class);

    private boolean watch = true;
    private WatcherService watcherService;

    public WatcherThread(String searchingPath) throws IOException {
        watcherService = new WatcherService(Paths.get(searchingPath));
    }

    @Override
    public void run() {
        LOGGER.info("Artifact watching thread started.");
        try {
            while (watch) {
                if (!watcherService.watch()) {
                    break;
                }
            }
        } catch (ClosedWatchServiceException exc) {
            // just as planned
        }
        LOGGER.info("Artifact watching thread stopped.");
    }

    public void stopThread() throws IOException {
        // instead of waiting we might handling ClosedWatchServiceException I think
        LOGGER.info("Stopping thread may take some time. Please wait...");
        watcherService.stopWatcher();
        watch = false;
    }
}
