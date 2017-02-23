package com.epam.lab.mentoring.classloader;

import com.epam.lab.mentoring.api.IPluggable;
import org.apache.commons.io.IOUtils;
import org.clapper.util.classutil.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import static com.epam.lab.mentoring.ApplicationConstants.CLASS_FILE_EXTENSION;

public class JarResourceLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(JarResourceLoader.class);

    public static void load(Path jar) throws IOException {
        LOGGER.info("Attempt to load classes from jar: [{}].", jar);
        List<String> classNames = findPluginClasses(jar);
        JarResourceRegistry.REGISTRY.populateJarResources(jar.toFile(), classNames);
    }

    public static boolean isCurrentClassLoaderSupported(String name) {
        return JarResourceRegistry.REGISTRY.getJarResource(name) != null;
    }

    public static byte[] getClassByteArray(String name) {
        File resource = JarResourceRegistry.REGISTRY.getJarResource(name);
        if (null == resource) {
            LOGGER.info("No registry record find for class [{}]!", name);
            return null;
        }
        return getClassBytesFromJar(resource, name);
    }

    private static List<String> findPluginClasses(Path jar) {
        ClassFinder finder = new ClassFinder();
        finder.add(jar.toFile());

        ClassFilter filter = new AndClassFilter(
                new NotClassFilter(new InterfaceOnlyClassFilter()),
                new SubclassClassFilter(IPluggable.class),
                new NotClassFilter(new AbstractClassFilter())
        );
        Collection<ClassInfo> foundClasses = new ArrayList<>();
        finder.findClasses(foundClasses, filter);
        List<String> classNames = foundClasses.stream().map(ClassInfo::getClassName).collect(Collectors.toList());
        LOGGER.info("Found pluggable classes in jar file: [{}].", classNames);
        return classNames;
    }

    private static byte[] getClassBytesFromJar(File jar, String name) {
        try (JarFile jarFile = new JarFile(jar)) {
            Enumeration e = jarFile.entries();
            while (e.hasMoreElements()) {
                JarEntry je = (JarEntry) e.nextElement();
                if (je.isDirectory() || !je.getName().endsWith(CLASS_FILE_EXTENSION)) {
                    continue;
                }

                String className = je.getName()
                        .substring(0, je.getName().length() - 6)
                        .replace('/', '.');

                if (name.equals(className)) { // found supported class
                    try (InputStream io = jarFile.getInputStream(je)) {
                        return IOUtils.toByteArray(io);
                    }
                }
            }
        } catch (IOException exc) {
            LOGGER.error("Failed to process jar file: [{}].", jar);
        }

        return null;
    }
}
