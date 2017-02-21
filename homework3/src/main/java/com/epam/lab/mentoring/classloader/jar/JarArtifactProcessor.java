package com.epam.lab.mentoring.classloader.jar;


import com.epam.lab.mentoring.api.IPluggable;
import org.clapper.util.classutil.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class JarArtifactProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JarArtifactProcessor.class);

    public static List<String> process(Path jarFile) {
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
                .map(ClassInfo::getClassName)
                .collect(Collectors.toList());
    }

}
