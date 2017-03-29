package com.epam.lab.mentoring.orm.database;

import com.epam.lab.mentoring.orm.OrmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DatabaseQueryUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseQueryUtils.class);

    public static PreparedStatement createPreparedStatement(String query, Connection conn, String... args) {
        try {
            PreparedStatement statement = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            for (int i = 0; i < args.length; i++) {
               statement.setString(i + 1, args[i]);
            }

            return statement;
        } catch (SQLException e) {
            LOGGER.error("Failed to created prepared statement for [{}].", query, e);
            throw new OrmException("Failed to query database!");
        }
    }

    public static int getRowsCount(ResultSet rs) {
        int rowCount = 0;
        try {
            if (rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to process result set!", e);
            throw new OrmException("Failed to count query result!");
        }
        return rowCount;
    }
}
