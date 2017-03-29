package com.epam.lab.mentoring;

import com.epam.lab.mentoring.orm.OrmException;
import com.epam.lab.mentoring.orm.OrmTemplateRegistry;
import com.epam.lab.mentoring.orm.database.DatabaseConnectivity;
import com.epam.lab.mentoring.orm.database.DatabaseSession;
import com.epam.lab.mentoring.sample.User;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

/**
 * Write your own annotation-based or XML-based ORM which parses
 *      specific class and generates SQL-queries for CRUD (or SCRUD) operations.
 * Your MiniORM should have one entry point, which supports CRUD operations for
 *      parsed class passed as a parameter in .save .load .update .delete methods.
 * Inheritance support will be a plus.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static void prepareDatabase() {
        String[] sqlFiles = { "create_tables.sql", "insert_data.sql" };
        LOGGER.info("Preparing database for the first time...");
        DatabaseConnectivity.INSTANCE.createSession();

        ClassLoader currentClassloader = Main.class.getClassLoader();
        Arrays.stream(sqlFiles).forEach(file -> {
            InputStream fileStream = currentClassloader.getResourceAsStream(file);
            try {
                String query = IOUtils.toString(fileStream, Charset.defaultCharset());
                LOGGER.debug("Read query: [\n{}].", query);

                Statement statement = DatabaseConnectivity.INSTANCE.getConnection().createStatement();
                statement.execute(query);
                statement.close();
            } catch (SQLException | IOException e) {
                if (e.getMessage().contains("already exists") || e.getMessage().contains("violation")) {
                    LOGGER.warn("Ignoring [already exists] exception.");
                } else {
                    LOGGER.error("Failed to create sql statement for [{}].", file, e);
                    throw new OrmException("Failed to read sql files!");
                }
            }
        });

        DatabaseConnectivity.INSTANCE.destroySession();
    }

    private static void init() {
        OrmTemplateRegistry.INSTANCE.initialize("com.epam.lab.mentoring");
        DatabaseConnectivity.INSTANCE.initialize();
        prepareDatabase();
    }

    public static void main(String[] args) {
        init();

        DatabaseSession session = new DatabaseSession();
        session.startSession();

        User readUser1 = session.readForObject("IUserRepository.readUser", "1");
        if (null != readUser1) {
            LOGGER.info("Processed user from database first read => [{}].", readUser1); // User{id='1', name='James'}
        }

        session.insertObject("IUserRepository.insertUser", new User("2", "Makko"));
        session.updateObject("IUserRepository.updateUser", "JamesU", "1");

        readUser1 = session.readForObject("IUserRepository.readUser", "1");
        if (null != readUser1) {
            LOGGER.info("Processed user from database after update => [{}].", readUser1); // User{id='1', name='James'}
        }
        readUser1 = session.readForObject("IUserRepository.readUser", "2");
        if (null != readUser1) {
            LOGGER.info("Processed user from database after insert => [{}].", readUser1); // User{id='1', name='James'}
        }

        session.deleteObject("IUserRepository.deleteUser", "1");
        session.closeSession();
    }
}
