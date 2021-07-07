/*
Creating by Zelma Milev
*/
package com.zm.utils;

import com.zm.db.ConnectionPool;
import com.zm.beans.Category;

import java.sql.*;
import java.time.LocalDate;
import java.util.Map;
/*
   ערכים פרימיטיביים פשוטים לצורך השגת Connection למסד הנתונים.
טיפול ב- ConnectionPool
 */

public class DBUtils {
    private static Connection connection;

    public static final String URL = "jdbc:mysql://localhost:3306" +
            "?createDatabaseIfNotExist=FALSE" +
            "&useTimezone=TRUE" +
            "&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASS = "ZM032021";

    public static void runStatement(String SQL_QUERY) throws SQLException {
        Connection connection = null;
        try {
            //STEP 2 - Open Connection to DB
            connection = ConnectionPool.getInstance().getConnection();
            //STEP 3 - Run your SQL instruction
            PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //STEP 5 - Close Connection to DB
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }

    public static void runStatement(String SQL_QUERY, Map<Integer, Object> map) throws SQLException {
        Connection connection = null;
        try {
            //STEP 2 - Open Connection to DB
            connection = ConnectionPool.getInstance().getConnection();
            //STEP 3 - Run your SQL instruction
            PreparedStatement statement = connection.prepareStatement(SQL_QUERY);

            for (Map.Entry<Integer, Object> entry : map.entrySet()) {
                int key = entry.getKey();
                Object value = entry.getValue();

                if (value instanceof Integer) {
                    statement.setInt(key, (int) value);
                } else if (value instanceof String) {
                    statement.setString(key, String.valueOf(value));
                } else if (value instanceof Float) {
                    statement.setFloat(key, (float) value);
                } else if (value instanceof Double) {
                    statement.setDouble(key, (double) value);
                } else if (value instanceof Category) {
                    statement.setString(key, String.valueOf(value));
                } else if (value instanceof LocalDate) {
                    statement.setDate(key, Date.valueOf((LocalDate) value));
                } else if (value instanceof Date) {
                    statement.setDate(key,(Date) value);
                }
            }
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //STEP 5 - Close Connection to DB
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }

    public static ResultSet runStatementWithResultSet(String SQL_QUERY) throws SQLException {
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement stm = connection.prepareStatement(SQL_QUERY);
            resultSet = stm.executeQuery();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
        return resultSet;
    }

    public static ResultSet runStatementWithResultSet(String SQL_QUERY, Map<Integer, Object> map) throws SQLException {

        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement stm = connection.prepareStatement(SQL_QUERY);
            for (Map.Entry<Integer, Object> entry : map.entrySet()) {
                int key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Integer) {
                    stm.setInt(key, (int) value);
                } else if (value instanceof String) {
                    stm.setString(key, String.valueOf(value));
                } else if (value instanceof Float) {
                    stm.setFloat(key, (float) value);
                } else if (value instanceof Double) {
                    stm.setDouble(key, (double) value);
                } else if (value instanceof Category) {
                    stm.setString(key, String.valueOf(value));
                } else if (value instanceof LocalDate) {
                    stm.setDate(key, Date.valueOf((LocalDate) value));
                } else if (value instanceof Date) {
                    stm.setDate(key, (Date) value);
                }
            }
            resultSet = stm.executeQuery();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
        return resultSet;
    }
}
