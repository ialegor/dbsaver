package io.github.ialegor.dbsaver.db;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConnectionStringImpl implements ConnectionString {

    private String name;

    private String connectionString;

    private String username;

    private String password;
}
