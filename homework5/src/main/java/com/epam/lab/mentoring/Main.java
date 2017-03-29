package com.epam.lab.mentoring;

import com.epam.lab.mentoring.orm.database.DatabaseConnectivity;
import com.epam.lab.mentoring.orm.database.DatabaseSession;
import com.epam.lab.mentoring.sample.User;

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
        DatabaseConnectivity.INSTANCE.populateRepositoryRegistry(); // process classes for ORM support
        // necessary if no tables exist
        // files are being read from /resources folder
        DatabaseConnectivity.INSTANCE.prepareDatabase("create_tables.sql", "insert_data.sql");

        DatabaseSession session = new DatabaseSession();
        User readUser = session.readForObject("IRepositorySample.readUser", User.class, "1");
        if (null != readUser) {
            System.out.println("Proccessed user from database. " + readUser);
        }
    }
}
