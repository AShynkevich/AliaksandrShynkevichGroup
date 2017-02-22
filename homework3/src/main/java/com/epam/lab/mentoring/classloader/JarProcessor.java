package com.epam.lab.mentoring.classloader;

import com.epam.lab.mentoring.api.IPluggable;
import org.clapper.util.classutil.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

public class JarProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(JarProcessor.class);

    static Collection<ClassInfo> process(Path jarFile) {
        LOGGER.info("Attempt to load file: [{}].", jarFile);
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
        return foundClasses;
    }
 }
