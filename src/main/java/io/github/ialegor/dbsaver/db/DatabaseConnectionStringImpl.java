package io.github.ialegor.dbsaver.db;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = true)
public class DatabaseConnectionStringImpl implements DatabaseConnectionString {

    private String connectionString;
}
