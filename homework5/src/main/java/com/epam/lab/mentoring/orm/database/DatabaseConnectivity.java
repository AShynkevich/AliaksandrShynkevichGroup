package com.epam.lab.mentoring.orm.database;

import com.epam.lab.mentoring.orm.OrmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

import static com.epam.lab.mentoring.orm.OrmConstants.*;

public enum DatabaseConnectivity {
    INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnectivity.class);

    private Properties databaseProperties;
    private Connection dbConnection;

    public void initialize() {
        loadProperties();
        loadDriver();
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
        try {
            dbConnection.commit();
        } catch (SQLException e) {
            LOGGER.info("Failed to commit session actions!", e);
            throw new OrmException("Commit failure!");
        }

        if (null != dbConnection) {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                LOGGER.error("Failed to close database connection.", e);
                throw new OrmException("Failed to destroy database session!");
            }
        }
    }

    public Connection getConnection() {
        return dbConnection;
    }
}
