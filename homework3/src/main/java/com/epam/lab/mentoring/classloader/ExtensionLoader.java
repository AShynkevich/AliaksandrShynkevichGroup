package com.epam.lab.mentoring.classloader;

import org.apache.commons.io.IOUtils;
import org.clapper.util.classutil.ClassInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.stream.Collectors;

public class ExtensionLoader extends ClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionLoader.class);

    public ExtensionLoader(ClassLoader parent) {
        super(parent);
    }

    @SuppressWarnings("unchecked")
    public void load(Path jar) throws IOException {
        List<String> classNames = JarProcessor.process(jar).stream()
                .map(ClassInfo::getClassName)
                .collect(Collectors.toList());
        registerClasses(jar, classNames);
    }

    private void registerClasses(Path jar, List<String> classNames) throws IOException {
        JarFile jarFile = new JarFile(jar.toFile());
        Enumeration<JarEntry> e = jarFile.entries();
        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }

            String className = je.getName().substring(0, je.getName().length() - 6).replace('/', '.');
            if (classNames.contains(className)) {
                LOGGER.info("Found class in jar [{}].", className);
                InputStream stream = new JarInputStream(jarFile.getInputStream(je));
                byte[] classBytes = IOUtils.toByteArray(stream); // empty array
                // I believe I can get all the information about the class there without [classNames] search
                Class<?> clz = this.defineClass(className, classBytes, 0, classBytes.length);
                LOGGER.info("Defined class: [{}].", clz.getClass());
            }
        }
    }
}
