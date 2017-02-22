package com.epam.lab.mentoring.watcher;

import com.epam.lab.mentoring.watcher.resolver.CreateEventResolver;
import com.epam.lab.mentoring.watcher.resolver.IEventResolver;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.HashMap;
import java.util.Map;


import static com.epam.lab.mentoring.ApplicationConstants.PLUGIN_EXTENSION;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

class WatchEventResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(WatchEventResolver.class);

    private static Map<WatchEvent.Kind<Path>, IEventResolver> resolvers = new HashMap<>();

    static {
        resolvers.put(ENTRY_CREATE, new CreateEventResolver());
        resolvers.put(ENTRY_MODIFY, new CreateEventResolver());
    }

    static void resolveEvent(WatchEvent<Path> event, Path file) {
        if (PLUGIN_EXTENSION.equals(FilenameUtils.getExtension(file.toString()))) {
            IEventResolver resolver = resolvers.get(event.kind());
            if (null != resolver) {
                LOGGER.info("Resolving file: [{}].", file);
                resolver.resolve(file);
            } else {
                LOGGER.info("No resolver found for event [{}].", event.kind().name());
            }
        }
    }

}
