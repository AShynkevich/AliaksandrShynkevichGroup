package com.epam.lab.mentoring.classloader;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.epam.lab.mentoring.ApplicationConstants.CLASSES_OUTPUT_TEMPLATE;

public enum JarResourceRegistry {
    REGISTRY;

    private Map<String, File> jarResources = new ConcurrentHashMap<>();
    private Map<String, String> idReferenceMap = new ConcurrentHashMap<>();

    public synchronized void populateJarResources(File jar, List<String> associatedClasses) {
        associatedClasses.forEach(className -> {
            jarResources.put(className, jar);
            idReferenceMap.put(String.valueOf(idReferenceMap.size()), className);
        });
    }

    public synchronized List<String> getLoadedClasses() {
        if (jarResources.isEmpty()) {
            return Collections.emptyList();
        } else {
            return idReferenceMap.entrySet().stream()
                    .map(pair -> String.format(CLASSES_OUTPUT_TEMPLATE,
                            FilenameUtils.getExtension(pair.getValue()), pair.getKey()))
                    .collect(Collectors.toList());
        }
    }
    public synchronized JarClassPair getJarResource(String id) {
        String className = idReferenceMap.get(id);
        if (null == className) {
            return null;
        }

        return new JarClassPair(className, jarResources.get(className));
    }

    public synchronized boolean verifyClassExistenceById(String id) {
        String className = idReferenceMap.get(id);
        return null != className && null != jarResources.get(className);
    }

    public synchronized String translateToClassNameFromId(String id) {
        return idReferenceMap.get(id);
    }

    public static class JarClassPair {
        private String className;
        private File jarFile;

        public JarClassPair(String className, File jarFile) {
            this.className = className;
            this.jarFile = jarFile;
        }

        public String getClassName() {
            return className;
        }

        public File getJarFile() {
            return jarFile;
        }
    }
}
