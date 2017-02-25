package com.epam.lab.mentoring.watcher;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.nio.file.StandardWatchEventKinds.*;

public class WatcherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WatcherThread.class);

    private final WatchService watcher;
    private final Map<WatchKey, Path> keys;
    private boolean trace;

    // workaround for duplicating
    private ScheduledExecutorService scheduledExecutorService = Executors
            .newSingleThreadScheduledExecutor(new BasicThreadFactory.Builder().daemon(true).build());
    private static final Queue<Pair<WatchEvent<Path>, Path>> EVENT_QUEUE = new ConcurrentLinkedDeque<>();
    private final Lock lock = new ReentrantLock();

    WatcherService(Path dir) throws IOException {
        watcher = FileSystems.getDefault().newWatchService();
        scheduledExecutorService.scheduleWithFixedDelay(new ResolveEventRunnable(), 2, 2, TimeUnit.SECONDS);

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

            EVENT_QUEUE.add(new ImmutablePair<>(ev, child));
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

    private class ResolveEventRunnable implements Runnable {

        @Override
        public void run() {
            try {
                for (Iterator<Pair<WatchEvent<Path>, Path>> iterator = EVENT_QUEUE.iterator(); iterator.hasNext();) {
                    if (lock.tryLock()) {
                        try {
                            Pair<WatchEvent<Path>, Path> pair = iterator.next();
                            WatchEventResolver.resolveEvent(pair.getLeft(), pair.getRight());
                            iterator.remove();
                        } finally {
                            lock.unlock();
                        }
                    }
                }
            } catch (Throwable e) {
                LOGGER.error("Threading error.", e);
            }
        }
    }
}
