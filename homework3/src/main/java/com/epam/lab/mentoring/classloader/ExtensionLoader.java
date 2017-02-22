package com.epam.lab.mentoring.classloader;

import com.epam.lab.mentoring.api.IPluggable;
import com.epam.lab.mentoring.classloader.jar.JarArtifactProcessor;
import com.epam.lab.mentoring.extension.ExtensionRegistry;
import com.epam.lab.mentoring.extension.Plugin;
import com.epam.lab.mentoring.extension.PluginSupportUtils;
import org.apache.commons.io.FilenameUtils;
import org.clapper.util.classutil.ClassInfo;
import org.objectweb.asm.ClassReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

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
                        LOGGER.info("Found classes: [{}].", jarClasses);
                        if (null != jarClasses) {
                            // timestamp is based on jar modified time for now
                            LocalDateTime timestamp =
                                    LocalDateTime.ofInstant(
                                            Instant.ofEpochMilli(file.toFile().lastModified()), ZoneOffset.UTC);

                            try {
                                // get class from jar
                                JarFile jar = new JarFile(file.toFile());
                                Enumeration e = jar.entries();
                                while (e.hasMoreElements()) {
                                    ZipEntry entry = (ZipEntry) e.nextElement();
                                    if (!entry.isDirectory() && entry.getName().toLowerCase().endsWith(".class")
                                            && jarClasses.contains(new File(entry.getName()).getName())) {

                                        // exist interesting approach using visitors
                                        InputStream input = jar.getInputStream(entry);
                                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                                        int data = input.read();

                                        while (data != -1) {
                                            buffer.write(data);
                                            data = input.read();
                                        }

                                        input.close();

                                        byte[] classData = buffer.toByteArray();
                                        // supposed to be filtered before
                                        Class<? extends IPluggable> clazz =
                                                (Class<IPluggable>) defineClass(file.toFile().getName(), classData, 0, classData.length);

                                        // create or update plugin
                                        Plugin plugin = ExtensionRegistry.REGISTRY.getPlugin(file.toFile().getName());
                                        if (null == plugin) {
                                            plugin = PluginSupportUtils.createPlugin(clazz, timestamp);
                                        } else {
                                            if (timestamp.isBefore(plugin.getTimestamp())) {
                                                plugin = PluginSupportUtils.updatePlugin(plugin, clazz, timestamp);
                                            }
                                        }
                                        ExtensionRegistry.REGISTRY.registerPlugin(plugin);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (IOException exc) {
            LOGGER.error("Failed to traverse the folder [{}].", EXTENSION_FOLDER);
        }
    }
}
