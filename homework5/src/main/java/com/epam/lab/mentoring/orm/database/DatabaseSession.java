package com.epam.lab.mentoring.orm.database;

import com.epam.lab.mentoring.orm.OrmException;
import com.epam.lab.mentoring.orm.OrmTemplateRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DatabaseSession {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSession.class);

    public void startSession() {
        LOGGER.debug("Starting session");
        DatabaseConnectivity.INSTANCE.createSession();
    }

    public void closeSession() {
        LOGGER.debug("Closing session");
        DatabaseConnectivity.INSTANCE.destroySession();
    }

    public <T> T readForObject(String templateId, String... args) {
        LOGGER.info("Attempt to read object: [{}].", args);
        T toReturn = null;
        OrmTemplateRegistry.TemplatePair pair = OrmTemplateRegistry.INSTANCE.getTemplateWithReturnType(templateId);
        String queryTemplate = pair.getLeft();
        Class<T> expectedObject = (Class<T>) pair.getRight();
        PreparedStatement statement = DatabaseQueryUtils.createPreparedStatement(queryTemplate,
                DatabaseConnectivity.INSTANCE.getConnection(), args);
        try {
            toReturn = expectedObject.newInstance();

            ResultSet result = statement.executeQuery();
            int rowsCount = DatabaseQueryUtils.getRowsCount(result);
            if (rowsCount > 1) {
                throw new OrmException("More then 1 result found!");
            } else if (rowsCount == 0) {
                return null;
            } else {
                result.next(); // there must only one element or zero
                toReturn = populateObject(toReturn, result, expectedObject);
            }
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            LOGGER.error("Failure during query processing [{}].", queryTemplate, e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Failed to close execution statement!");
            }
        }
        return toReturn;
    }

    private <T> T populateObject(T toReturn, ResultSet resultSet, Class<T> objectClass) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int numberOfColumns = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= numberOfColumns; i++) {
            String columnName = resultSetMetaData.getColumnName(i);
            String resultSetValue = resultSet.getString(i);

            try {
                Field field = objectClass.getDeclaredField(columnName.toLowerCase());
                field.setAccessible(true);
                field.set(toReturn, resultSetValue);
            } catch (NoSuchFieldException e) {
                LOGGER.warn("Object [{}] does not contain field [{}].", objectClass,
                        columnName.toLowerCase());
            } catch (IllegalAccessException e) {
                LOGGER.error("Failed to access object field!", e);
            }
        }
        return toReturn;
    }

    public void deleteObject(String templateId, String... args) {
        LOGGER.info("Attempt to delete object: [{}].", args);
        modifyObject(templateId, args);
    }

    public void updateObject(String templateId, String... args) {
        LOGGER.info("Attempt to update object: [{}].", args);
        modifyObject(templateId, args);
    }

    public <T> void insertObject(String templateId, T object) {
        LOGGER.info("Attempt to insert object: [{}].", object);
        Field[] fields = object.getClass().getDeclaredFields();
        String[] fieldValues = new String[fields.length];

        Arrays.stream(fields).map(field -> {
            try {
                field.setAccessible(true);
                return field.get(object).toString();
            } catch (IllegalAccessException e) {
                LOGGER.error("Failed to set object [{}] field [{}].", object, field.getName(), e);
                throw new OrmException("Failed to execute insert statement!");
            }
        }).collect(Collectors.toList()).toArray(fieldValues);

        modifyObject(templateId, fieldValues);
    }

    private void modifyObject(String templateId, String... args) {
        OrmTemplateRegistry.TemplatePair pair = OrmTemplateRegistry.INSTANCE.getTemplateWithReturnType(templateId);
        String queryTemplate = pair.getLeft();

        PreparedStatement statement = DatabaseQueryUtils.createPreparedStatement(queryTemplate,
                DatabaseConnectivity.INSTANCE.getConnection(), args);
        try {
            int result = statement.executeUpdate();
            LOGGER.info("Number of affected queries: [{}].", result);
        } catch (SQLException e) {
            if (e.getMessage().contains("violation")) {
                LOGGER.warn("Record with such parameters [{}] already exist!", Arrays.asList(args));
            } else {
                LOGGER.error("Failed to execute query [{}]!", queryTemplate, e);
                throw new OrmException("Failed to execute query!");
            }
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Failed to close execution statement", e);
            }
        }
    }
}
