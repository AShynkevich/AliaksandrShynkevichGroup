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

public class DatabaseSession {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSession.class);

    public <T> T readForObject(String templateId, String... args) {
        T toReturn = null;
        DatabaseConnectivity.INSTANCE.createSession();
        try {
            String queryTemplate = OrmTemplateRegistry.INSTANCE.getTemplate(templateId);
            PreparedStatement statement = DatabaseQueryUtils.createPreparedStatement(queryTemplate,
                    DatabaseConnectivity.INSTANCE.getConnection(), args);

            ResultSet result = statement.executeQuery();
            int rowsCount = DatabaseQueryUtils.getRowsCount(result);
            if (rowsCount > 1) {
                throw new OrmException("More then 1 result found!");
            }

            // do object conversion
            ResultSetMetaData resultSetMetaData = result.getMetaData();
            Class<T> expectedObject = (Class<T>) OrmTemplateRegistry.INSTANCE.getTemplateReturnType(templateId);
            toReturn = expectedObject.newInstance();
            int numberOfColumns = resultSetMetaData.getColumnCount();
            while (result.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    String resultSetValue = result.getString(i);

                    try {
                        Field field = expectedObject.getDeclaredField(columnName.toLowerCase());
                        field.setAccessible(true);
                        field.set(toReturn, resultSetValue);
                    } catch (NoSuchFieldException e) {
                        LOGGER.warn("Object [{}] does not contain field [{}].", expectedObject, columnName.toLowerCase());
                    }
                }
            }

            statement.close();
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            LOGGER.error("Failure during query processing!", e);
        }

        DatabaseConnectivity.INSTANCE.destroySession();
        return toReturn;
    }

}
