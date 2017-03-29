package com.epam.lab.mentoring;

import com.epam.lab.mentoring.orm.DatabaseConnectivity;

/**
 * Write your own annotation-based or XML-based ORM which parses
 *      specific class and generates SQL-queries for CRUD (or SCRUD) operations.
 * Your MiniORM should have one entry point, which supports CRUD operations for
 *      parsed class passed as a parameter in .save .load .update .delete methods.
 * Inheritance support will be a plus.
 */
public class Main {
    public static void main(String[] args) {
        DatabaseConnectivity.INSTANCE.initialize("com.epam.lab.mentoring.sample"); // load properties, driver

        // necessary if no tables exist
        // files are being read from /resources folder
        DatabaseConnectivity.INSTANCE.prepareDatabase("create_tables.sql");
        DatabaseConnectivity.INSTANCE.populateRepositoryRegistry(); // process classes for ORM support

        DatabaseConnectivity.INSTANCE.createSession(); // create connection instance

        DatabaseConnectivity.INSTANCE.destroySession(); // close connection instance
    }

}
