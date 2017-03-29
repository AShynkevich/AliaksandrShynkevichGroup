package com.epam.lab.mentoring;

import com.epam.lab.mentoring.orm.DatabaseConnectivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Write your own annotation-based or XML-based ORM which parses
 *      specific class and generates SQL-queries for CRUD (or SCRUD) operations.
 * Your MiniORM should have one entry point, which supports CRUD operations for
 *      parsed class passed as a parameter in .save .load .update .delete methods.
 * Inheritance support will be a plus.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        DatabaseConnectivity.INSTANCE.initialize("com.epam.lab.mentoring.sample"); // load properties, driver

        // necessary if no tables exist
        // files are being read from /resources folder
        DatabaseConnectivity.INSTANCE.prepareDatabase("create_tables.sql");

        DatabaseConnectivity.INSTANCE.createSession(); // create connection instance
        DatabaseConnectivity.INSTANCE.populateRepositoryRegistry(); // process classes for ORM support

        DatabaseConnectivity.INSTANCE.destroySession(); // close connection instance
    }

}
