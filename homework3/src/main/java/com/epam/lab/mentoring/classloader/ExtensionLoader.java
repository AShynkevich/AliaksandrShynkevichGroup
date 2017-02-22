package com.epam.lab.mentoring.classloader;

import com.epam.lab.mentoring.classloader.jar.JarArtifactProcessor;
import com.epam.lab.mentoring.extension.ExtensionRegistry;
import com.epam.lab.mentoring.extension.Plugin;
import com.epam.lab.mentoring.extension.PluginSupportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static com.epam.lab.mentoring.ApplicationConstants.EXTENSION_FOLDER;

public class ExtensionLoader extends ClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionLoader.class);

    protected ExtensionLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // iterate extension folder
        iterateExtensionFolder();
        // some null check or ClassNotFoundException
        return ExtensionRegistry.REGISTRY.getPlugin(name).getClazz();
    }

    private void iterateExtensionFolder() {
        try {
            Files.walk(Paths.get(EXTENSION_FOLDER))
                    .filter(file -> file.toString().endsWith(".jar"))
                    .forEach(file -> {
                        List<String> jarClasses = JarArtifactProcessor.collectionClassInformationFromJar(file);
                        if (null != jarClasses) {
                            // timestamp is based on jar modified time for now
                            LocalDateTime timestamp =
                                    LocalDateTime.ofInstant(
                                            Instant.ofEpochMilli(file.toFile().lastModified()), ZoneOffset.UTC);
                            Plugin plugin = ExtensionRegistry.REGISTRY.getPlugin(file.toFile().getName());
                            if (null == plugin) {
                                plugin = PluginSupportUtils.createPlugin(null, timestamp);
                            } else {
                                if (timestamp.isBefore(plugin.getTimestamp())) {
                                    plugin = PluginSupportUtils.updatePlugin(plugin, null, timestamp);
                                }
                            }
                            ExtensionRegistry.REGISTRY.registerPlugin(plugin);
                        }
                    });
        } catch (IOException exc) {
            LOGGER.error("Failed to traverse the folder [{}].", EXTENSION_FOLDER);
        }
    }
}
