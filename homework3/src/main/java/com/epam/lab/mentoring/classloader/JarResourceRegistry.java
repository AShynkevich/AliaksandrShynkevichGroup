package com.epam.lab.mentoring.classloader;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public enum JarResourceRegistry {
    REGISTRY;

    private Map<String, File> jarResources = new ConcurrentHashMap<>();

    public void populateJarResources(File jar, List<String> associatedClasses) {
        associatedClasses.forEach(className -> jarResources.put(className, jar));
    }

    public List<String> getLoadedClasses() {
        if (jarResources.isEmpty()) {
            return Collections.emptyList();
        } else {
            return new ArrayList<>(jarResources.keySet());
        }
    }
    public File getJarResource(String className) {
        return jarResources.get(className);
    }
}
