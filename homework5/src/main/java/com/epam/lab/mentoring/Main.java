package com.epam.lab.mentoring;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Write your own annotation-based or XML-based ORM which parses
 *      specific class and generates SQL-queries for CRUD (or SCRUD) operations.
 * Your MiniORM should have one entry point, which supports CRUD operations for
 *      parsed class passed as a parameter in .save .load .update .delete methods.
 * Inheritance support will be a plus.
 */
public class Main {

    private static final String DB_URL = "database.url";
    private static final String DB_USERNAME = "database.username";
    private static final String DB_PWD = "database.password";
    private static final String DB_DRIVER = "database.driver";

    public static void main(String[] args) {
        Connection dbConnection = null;
        try {
            Properties props = loadConfig();
            Class.forName(props.getProperty(DB_DRIVER));
            dbConnection = DriverManager.getConnection(
                    props.getProperty(DB_URL),
                    props.getProperty(DB_USERNAME),
                    props.getProperty(DB_PWD));
            dbConnection.setAutoCommit(true);

            // create tables in the database
            String createTableQuery = "CREATE TABLE USERS (id int PRIMARY KEY, name VARCHAR(128))";
            PreparedStatement createTableStatement = dbConnection.prepareStatement(createTableQuery);
            createTableStatement.executeUpdate();
            createTableStatement.close();

            dbConnection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != dbConnection) {
                try {
                    dbConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Properties loadConfig() throws IOException {
        Properties props = new Properties();
        props.load(Main.class.getClassLoader().getResourceAsStream("database.properties"));
        return props;
    }
}
