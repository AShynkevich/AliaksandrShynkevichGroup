package com.epam.lab.mentoring.orm;

import com.epam.lab.mentoring.orm.annotation.Query;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum OrmTemplateRegistry {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmTemplateRegistry.class);

    private String repositoryPackage;
    private Map<String, String> queryRegistry;
    private Map<String, Class<?>> queryReturnTypeRegistry;

    public void initialize(String repositoryPackage) {
        this.repositoryPackage = repositoryPackage;

        populateRepositoryRegistry();
    }

    public Class<?> getTemplateReturnType(String id) {
        return queryReturnTypeRegistry.get(id);
    }

    public String getTemplate(String id) {
        return queryRegistry.get(id);
    }

    private void populateRepositoryRegistry() {
        LOGGER.debug("Processing package [{}] for classes.", repositoryPackage);
        Reflections reflections = new Reflections(repositoryPackage, new MethodAnnotationsScanner());
        Set<Method> ormSupportedMethods = reflections.getMethodsAnnotatedWith(Query.class);
        queryRegistry = new HashMap<>();
        queryReturnTypeRegistry = new HashMap<>();

        ormSupportedMethods.forEach(method -> {
            String statement = method.getAnnotation(Query.class).value();
            String uniqueKey = method.getDeclaringClass().getSimpleName().concat(".").concat(method.getName());
            LOGGER.debug("Unique key: [{}].", uniqueKey);

            queryRegistry.put(uniqueKey, statement);
            queryReturnTypeRegistry.put(uniqueKey, method.getReturnType());
        });
    }
}
