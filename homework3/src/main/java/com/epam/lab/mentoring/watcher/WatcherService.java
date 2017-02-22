package com.epam.lab.mentoring.watcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

class WatcherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WatcherThread.class);

    private final WatchService watcher;
    private final Map<WatchKey, Path> keys;
    private boolean trace;

    WatcherService(Path dir) throws IOException {
        watcher = FileSystems.getDefault().newWatchService();
        keys = new HashMap<>();
        registerAll(dir);
        trace = true;
    }

    private void registerAll(final Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (null == prev) {
                LOGGER.info("Register path: [{}].", dir);
            } else {
                if (!dir.equals(prev)) {
                    LOGGER.info("Updated path: [{}] -> [{}].", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }

    boolean watch() {
        WatchKey key;
        try {
            key = watcher.take();
        } catch (InterruptedException exc) {
            return false;
        }

        Path dir = keys.get(key);
        if (null == dir) {
            LOGGER.warn("WatchKey is not recognized!");
            return false;
        }

        // forEach?
        for (WatchEvent event: key.pollEvents()) {
            WatchEvent.Kind kind = event.kind();
            if (OVERFLOW == kind) {
                continue;
            }

            WatchEvent<Path> ev = (WatchEvent<Path>) event;
            Path name = ev.context();
            Path child = dir.resolve(name);
            WatchEventResolver.resolveEvent(ev, child);
        }

        boolean valid = key.reset();
        if (!valid) {
            keys.remove(key);

            if (keys.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void stopWatcher() throws IOException {
        watcher.close();
    }
}