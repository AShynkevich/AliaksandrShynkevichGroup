package com.epam.lab.mentoring.classloader;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public enum JarResourceRegistry {
    REGISTRY;

    private Map<String, File> jarResources = new ConcurrentHashMap<>();

    public synchronized void populateJarResources(File jar, List<String> associatedClasses) {
        associatedClasses.forEach(className -> jarResources.put(className, jar));
    }

    public synchronized void unloadClass(String name) {
        jarResources.remove(name);
    }

    public synchronized List<String> getLoadedClasses() {
        if (jarResources.isEmpty()) {
            return Collections.emptyList();
        } else {
            return new ArrayList<>(jarResources.keySet());
        }
    }
    public synchronized File getJarResource(String className) {
        return jarResources.get(className);
    }
}
