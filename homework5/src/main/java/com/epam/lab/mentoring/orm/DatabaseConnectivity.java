package com.epam.lab.mentoring.orm;

import com.epam.lab.mentoring.orm.annotation.Query;
import org.apache.commons.io.IOUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;

import static com.epam.lab.mentoring.orm.OrmConstants.*;

public enum DatabaseConnectivity {
    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnectivity.class);

    private String repositoryPackage;
    private Map<String, PreparedStatement> queryRegistry;
    private Properties databaseProperties;
    private Connection dbConnection;

    public void initialize(String repositoryPackage) {
        loadProperties();
        loadDriver();
        this.repositoryPackage = repositoryPackage;
    }

    public void prepareDatabase(String... sqlFiles) {
        createSession();

        ClassLoader currentClassloader = this.getClass().getClassLoader();
        Arrays.stream(sqlFiles).forEach(file -> {
            InputStream fileStream = currentClassloader.getResourceAsStream(file);
            try {
                String query = IOUtils.toString(fileStream, Charset.defaultCharset());
                LOGGER.info("Read query: [\n{}].", query);

                Statement statement = dbConnection.createStatement();
                statement.execute(query);
                statement.close();
            } catch (SQLException | IOException e) {
                if (e.getMessage().contains("already exists")) {
                    LOGGER.info("Ignoring [adlready exists] exception.");
                } else {
                    LOGGER.error("Failed to create sql statement for [{}].", file, e);
                    throw new OrmException("Failed to read sql files!");
                }
            }
        });

        destroySession();
    }

    public void createSession() {
        try {
            dbConnection = DriverManager.getConnection(
                    databaseProperties.getProperty(DB_URL),
                    databaseProperties.getProperty(DB_USERNAME),
                    databaseProperties.getProperty(DB_PWD));
            dbConnection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("Failed to create connection to database [{}] with username [{}] and password [{}].",
                databaseProperties.getProperty(DB_URL), databaseProperties.getProperty(DB_USERNAME),
                    databaseProperties.getProperty(DB_PWD), e);
            throw new OrmException("Failed to create database session!");
        }
    }

    public void destroySession() {
        if (null != dbConnection) {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                LOGGER.error("Failed to close database connection.", e);
                throw new OrmException("Failed to destroy database session!");
            }
        }
    }

    public void populateRepositoryRegistry() {
        LOGGER.info("Processing package [{}] for classes.", repositoryPackage);
        Reflections reflections = new Reflections(repositoryPackage, new MethodAnnotationsScanner());
        Set<Method> ormSupportedMethods = reflections.getMethodsAnnotatedWith(Query.class);
        queryRegistry = new HashMap<>();
        ormSupportedMethods.forEach(method -> {
            String statement = method.getAnnotation(Query.class).value();
            System.out.println("Method class: " + method.getDeclaringClass());
            System.out.println("Method name: " + method.getName());

            try {
                queryRegistry.put("id", dbConnection.prepareStatement(statement));
            } catch (SQLException e) {
                LOGGER.error("Failed to create sql query [{}].", statement, e);
                throw new OrmException("Failed to create sql query!");
            }
        });
    }

    private void loadProperties() {
        LOGGER.info("Reading [database.properties] file...");
        databaseProperties = new Properties();
        try {
            databaseProperties.load(this.getClass()
                    .getClassLoader()
                    .getResourceAsStream(PROPERTIES_FILE)
            );
        } catch (Exception exc) {
            LOGGER.error("Failed to read properties from [database.properties] file!", exc);
            throw new OrmException("Failed to read [database.properties]!");
        }
    }

    private void loadDriver() {
        LOGGER.info("Reading database driver...");
        try {
            Class.forName(databaseProperties.getProperty(DB_DRIVER));
        } catch (ClassNotFoundException e) {
            LOGGER.error("Failed to load [{}] property.", DB_DRIVER, e);
            throw new OrmException("Failed to load database driver!");
        }
    }
}
