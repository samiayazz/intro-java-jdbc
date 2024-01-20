package dev.intro.jdbi;

import dev.intro.common.UserRepository;
import dev.intro.entity.UserRecord;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

import java.util.List;

public class JdbiUserRepository implements UserRepository {

    /*
     * useHandle: doesn't return a value
     * withHandle: returns a values
     */

    private final Jdbi jdbi;

    public JdbiUserRepository() {
        jdbi = Jdbi.create("jdbc:sqlite:intro-jdbi.db");
    }

    @Override
    public List<UserRecord> getUsers() {
        return jdbi.withHandle(handle -> {
            // For set values from constructor
            handle.registerRowMapper(ConstructorMapper.factory(UserRecord.class));

            /*handle.createQuery("SELECT * FROM users WHERE id > :id")
                    .bind("id", 1)
                    .mapTo(UserRecord.class)
                    .one();*/
            return handle.createQuery("SELECT * FROM users")
                    .mapTo(UserRecord.class)
                    .list();
        });
    }

    @Override
    public Boolean createUser(String name, String surname, int age) {
        return jdbi.withHandle(handle -> {
            //handle.execute("INSERT INTO users (name, surname, age) VALUES (?, ?, ?)", name, surname, age);
            return handle.createUpdate("INSERT INTO users (name, surname, age) VALUES (:name, :surname, :age)")
                    .bind("name", name)
                    .bind("surname", surname)
                    .bind("age", age)
                    .execute() > 0;
        });
    }

    @Override
    public void createUsersTableIfNotExists() {
        jdbi.useHandle(handle -> {
            String sql = """
                    CREATE TABLE IF NOT EXISTS users (
                        id integer PRIMARY KEY,
                        name text NOT NULL,
                        surname text NOT NULL,
                        age integer
                    )
                    """;
            handle.execute(sql);
        });
    }
}
