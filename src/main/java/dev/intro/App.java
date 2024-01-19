package dev.intro;

import dev.intro.entity.UserRecord;
import dev.intro.util.SqlConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Create users table if not exists
        createUsersTableIfNotExists();

        // Create users
        if (createUser("John", "Doe", 21))
            System.out.println("User created with named 'John Doe'");
        if (createUser("Jane", "Doe", 25))
            System.out.println("User created with named 'Jane Doe");

        // Get users
        List<UserRecord> users = getUsers();
        System.out.println(users);
    }

    private static List<UserRecord> getUsers() {
        return SqlConnectionProvider.provide((connection) -> {
            List<UserRecord> result = null;

            try {
                ResultSet sqlResult = connection.createStatement().executeQuery("SELECT * FROM users");
                while (sqlResult.next()) {
                    result = result == null ? new ArrayList<>() : result;

                    result.add(new UserRecord(
                            sqlResult.getInt("id"),
                            sqlResult.getString("name"),
                            sqlResult.getString("surname"),
                            sqlResult.getInt("age")
                    ));
                }
            } catch (SQLException e) {
                System.out.println("An exception has occurred while getting users! Details: " + e);
            }

            return result;
        });
    }

    private static Boolean createUser(String name, String surname, int age) {
        return SqlConnectionProvider.provide(connection -> {
            Boolean result = null;

            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, surname, age) VALUES (?, ?, ?)");
                statement.setString(1, name);
                statement.setString(2, surname);
                statement.setInt(3, age);

                result = statement.executeUpdate() > 0;
            } catch (SQLException e) {
                System.out.println("An exception has occurred while creating user! Details: " + e);
            }

            return result;
        });
    }

    /*private static Integer getLastUserId() {
        return SqlConnectionProvider.provide(connection -> {
            Integer result = null;

            try {
                ResultSet sqlResult = connection.prepareStatement("SELECT LAST_INSERT_ROWID() FROM users").executeQuery();

                try {
                    sqlResult.next();
                    result = sqlResult.getInt(1);
                } catch (SQLException e) {
                    System.out.println("An exception has occurred while setting last user id to result! Details: " + e);
                }
            } catch (SQLException e) {
                System.out.println("An exception has occurred while getting last user id! Details: " + e);
            }

            return result;
        });
    }*/

    private static void createUsersTableIfNotExists() {
        SqlConnectionProvider.provide(connection -> {
            try {
                String sql = """
                        CREATE TABLE IF NOT EXISTS users (
                            id integer PRIMARY KEY,
                            name text NOT NULL,
                            surname text NOT NULL,
                            age integer
                        )
                        """;
                connection.prepareStatement(sql).executeUpdate();
            } catch (SQLException e) {
                System.out.println("An exception has occurred while creating users table! Details: " + e);
            }

            return null;
        });
    }
}
