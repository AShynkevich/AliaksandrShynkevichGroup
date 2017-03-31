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
    private Map<String, TemplatePair> queryRegistry;

    public void initialize(String repositoryPackage) {
        this.repositoryPackage = repositoryPackage;

        populateRepositoryRegistry();
    }

    public TemplatePair getTemplateWithReturnType(String id) {
        return queryRegistry.get(id);
    }

    private void populateRepositoryRegistry() {
        LOGGER.debug("Processing package [{}] for classes.", repositoryPackage);
        Reflections reflections = new Reflections(repositoryPackage, new MethodAnnotationsScanner());
        Set<Method> ormSupportedMethods = reflections.getMethodsAnnotatedWith(Query.class);
        queryRegistry = new HashMap<>();

        ormSupportedMethods.forEach(method -> {
            String statement = method.getAnnotation(Query.class).value();
            String uniqueKey = method.getDeclaringClass().getSimpleName().concat(".").concat(method.getName());
            LOGGER.debug("Unique key: [{}].", uniqueKey);

            queryRegistry.put(uniqueKey, new TemplatePair(statement, method.getReturnType()));
        });
    }

    public static class TemplatePair {
        private String left;
        private Class<?> right;

        public TemplatePair(String left, Class<?> right) {
            this.left = left;
            this.right = right;
        }

        public String getLeft() {
            return left;
        }

        public Class<?> getRight() {
            return right;
        }
    }
}
