package dev.intro.common;

import dev.intro.entity.UserRecord;

import java.util.List;

public interface UserRepository {
    List<UserRecord> getUsers();

    Boolean createUser(String name, String surname, int age);

    void createUsersTableIfNotExists();
}
