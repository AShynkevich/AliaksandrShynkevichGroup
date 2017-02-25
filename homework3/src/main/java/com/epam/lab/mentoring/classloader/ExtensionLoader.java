package com.epam.lab.mentoring.classloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ExtensionLoader extends ClassLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionLoader.class);

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            // have to check whether some chained classes require system classloader
            if (JarResourceLoader.isCurrentClassLoaderSupported(name)) {
                byte[] b = loadClassData(name);
                String className = JarResourceRegistry.REGISTRY.translateToClassNameFromId(name);
                Class<?> clazz = this.defineClass(className, b, 0, b.length);
                resolveClass(clazz);
                return clazz;
            }
        } catch (IOException exc) {
            LOGGER.warn("Failed to load class with extension class loader by id [{}].", name);
        }
        return super.loadClass(name);
    }

    private byte[] loadClassData(String name) throws ClassNotFoundException, IOException {
        byte[] classData = JarResourceLoader.getClassByteArray(name);
        if (null == classData || classData.length == 0) {
            String msg = String.format("Failed to find class [%s] in jar resource registry.", name);
            LOGGER.warn(msg);
            throw new ClassNotFoundException(msg);
        } else {
            return classData;
        }
    }
}
