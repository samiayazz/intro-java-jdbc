package dev.intro.function;

import java.sql.Connection;

@FunctionalInterface
public interface ExecuteSqlStatementFunction<T> {
    T execute(Connection connection);
}
