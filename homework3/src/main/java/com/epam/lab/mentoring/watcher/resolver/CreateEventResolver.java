package com.epam.lab.mentoring.watcher.resolver;

import com.epam.lab.mentoring.classloader.ExtensionLoader;
import com.epam.lab.mentoring.classloader.JarResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

public class CreateEventResolver implements IEventResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateEventResolver.class);

    @Override
    public void resolve(Path file) {
        LOGGER.info("File creation event occurred in [{}].", file);
        try {
            JarResourceLoader.load(file);
        } catch (IOException exc) {
            LOGGER.error("Failed to load [{}].", file, exc);
        }
    }
}
