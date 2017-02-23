package com.epam.lab.mentoring.classloader;

import com.epam.lab.mentoring.api.IPluggable;
import com.epam.lab.mentoring.extension.ExtensionRegistry;
import com.epam.lab.mentoring.extension.Plugin;
import com.epam.lab.mentoring.extension.PluginSupportUtils;
import org.apache.commons.io.IOUtils;
import org.clapper.util.classutil.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import static com.epam.lab.mentoring.ApplicationConstants.CLASS_FILE_EXTENSION;

public class ExtensionLoader extends ClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionLoader.class);

    public ExtensionLoader(ClassLoader parent) {
        super(parent);
    }

    @SuppressWarnings("unchecked")
    public void load(Path jar) throws IOException {
        registerClasses(jar);
    }

    private void registerClasses(Path jar) throws IOException {
        ClassFinder finder = new ClassFinder();
        finder.add(jar.toFile());

        List<String> classNames = foundSupportedClassesInJar(finder);

        JarFile jarFile = new JarFile(jar.toFile());
        Enumeration e = jarFile.entries();
        while (e.hasMoreElements()) {
            JarEntry je = (JarEntry) e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(CLASS_FILE_EXTENSION)) {
                continue;
            }

            String className = je.getName()
                    .substring(0, je.getName().length() - 6)
                    .replace('/', '.');

            if (classNames.contains(className)) {
                proccessJarEntry(jarFile, je, className);
            }
        }

        jarFile.close();
    }

    private List<String> foundSupportedClassesInJar(ClassFinder finder) {
        // well meh
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

    private void proccessJarEntry(JarFile jarFile, JarEntry je, String className) throws IOException {
        LOGGER.info("Found class in jar [{}].", className);
        InputStream stream = jarFile.getInputStream(je);
        byte[] classBytes = IOUtils.toByteArray(stream);
        LOGGER.info("Class bytes for class [{}] [{}].", className, classBytes.length);
        try {
            Class<?> clz = this.defineClass(className, classBytes, 0, classBytes.length); // duplicating
            LOGGER.info("Defined class: [{}].", clz.getClass());
            if (null == ExtensionRegistry.REGISTRY.getRegisteredClass(className)) {
                Plugin plugin = PluginSupportUtils.createPlugin(className);
                ExtensionRegistry.REGISTRY.registerPlugin(plugin);
            } else {
                Plugin plugin = ExtensionRegistry.REGISTRY.getPlugin(className);
                PluginSupportUtils.updatePlugin(plugin);
                ExtensionRegistry.REGISTRY.registerPlugin(plugin);
            }
        } catch (LinkageError error) { // duplicating
            LOGGER.warn("This class is already registered: [{}]. Ignored!", className);
        }

        stream.close();
    }
}
