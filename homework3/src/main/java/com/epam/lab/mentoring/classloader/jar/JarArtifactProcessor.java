package com.epam.lab.mentoring.classloader.jar;


import com.epam.lab.mentoring.api.IPluggable;
import org.clapper.util.classutil.*;
import org.objectweb.asm.ClassReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

public final class JarArtifactProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JarArtifactProcessor.class);
    private Map<String, File> searchDirectory = new LinkedHashMap<>();

    public static List<String> collectionClassInformationFromJar(Path jarFile) {
        LOGGER.info("Attempt to process jar file [{}].", jarFile);
        ClassFinder finder = new ClassFinder();
        finder.add(jarFile.toFile());

        ClassFilter filter =
                new AndClassFilter(
                        new NotClassFilter(new InterfaceOnlyClassFilter()),
                        new SubclassClassFilter(IPluggable.class),
                        new NotClassFilter(new AbstractClassFilter())
                );
        Collection<ClassInfo> foundClasses = new ArrayList<>();
        finder.findClasses(foundClasses, filter);

        return foundClasses.stream()
                .map(classInfo -> {
                    LOGGER.info("Found class [{}] in location [{}].", classInfo.getClassName(),
                            classInfo.getClassLocation());
                    return classInfo.getClassLocation() + classInfo.getClassName();
                })
                .collect(Collectors.toList());
    }

    // TODO: complete
    public static void updateExtensionRegistry(Path jarFile) {
        try {
            JarFile jar = new JarFile(jarFile.toFile());
            Manifest manifest = jar.getManifest();
            if (manifest != null) {
                Map map = manifest.getEntries();
                Attributes attrs = manifest.getMainAttributes();
                Set keys = attrs.keySet();
                Iterator i = keys.iterator();

                while (true) {
                    Object key;
                    String value;
                    do {
                        if (!i.hasNext()) {
                            return;
                        }

                        key = i.next();
                        value = (String) attrs.get(key);
                    } while (!key.toString().equals("Class-path"));

                    String jarName = jar.getName();
                    StringBuilder buf = new StringBuilder();
                    String element;
                    for(StringTokenizer tok = new StringTokenizer(value); tok.hasMoreTokens(); buf.append(element)) {
                        buf.setLength(0);
                        element = tok.nextToken();
                        String parent = jarFile.toFile().getParent();
                        if(parent != null) {
                            buf.append(parent);
                            buf.append(File.separator);
                        }
                    }

                    element = buf.toString();
                    File jarElementAsFile = new File(element);
                }
            }
        } catch (IOException exc) {
            LOGGER.error("Failed to update extension registry.", exc);
        }
    }
}
