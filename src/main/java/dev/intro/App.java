package dev.intro;

import dev.intro.common.UserRepository;
import dev.intro.entity.UserRecord;
import dev.intro.jdbi.JdbiUserRepository;
import dev.intro.pure.JdbcUserRepository;

import java.util.List;

public class App {
    private static final UserRepository userRepository = new JdbiUserRepository();

    public static void main(String[] args) {
        // Create users table if not exists
        userRepository.createUsersTableIfNotExists();

        // Create users
        if (userRepository.createUser("John", "Doe", 21))
            System.out.println("User created with named 'John Doe'");
        if (userRepository.createUser("Jane", "Doe", 25))
            System.out.println("User created with named 'Jane Doe");

        // Get users
        List<UserRecord> users = userRepository.getUsers();
        System.out.println(users);
    }
}
