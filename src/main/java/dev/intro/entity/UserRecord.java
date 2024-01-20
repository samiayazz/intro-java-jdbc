package dev.intro.entity;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record UserRecord(int id, String name, String surname, int age) {
}
