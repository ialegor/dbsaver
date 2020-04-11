package io.github.ialegor.dbsaver.db;

import io.github.ialegor.dbsaver.query.Query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseReader {

    public ResultSet execute(Query query, DatabaseConnectionString connection) {
        throw new RuntimeException();
    }

    public ResultSet execute(Query query, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query.sql());
    }
}
