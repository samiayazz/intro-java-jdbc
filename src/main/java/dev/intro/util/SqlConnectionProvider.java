package dev.intro.util;

import dev.intro.function.ExecuteSqlStatementFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnectionProvider {
    public static <T> T provide(ExecuteSqlStatementFunction<T> func) {
        T result = null;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:intro-jdbc.db");
            result = func.execute(connection);
        } catch (SQLException e) {
            System.out.println("An error has occurred while providing SQL Connection! Details: " + e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("An error has occurred while closing SQL Connection! Details: " + e);
                }
            }
        }

        return result;
    }
}
