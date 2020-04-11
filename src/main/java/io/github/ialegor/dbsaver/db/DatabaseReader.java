package io.github.ialegor.dbsaver.db;

import com.axiomalaska.jdbc.NamedParameterPreparedStatement;
import io.github.ialegor.dbsaver.query.Query;

import java.sql.*;
import java.util.Map;

public class DatabaseReader {

    public ResultSet execute(Query query, DatabaseConnectionString connection) {
        throw new RuntimeException();
    }

    public ResultSet execute(Query query, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query.sql());
    }

    public ResultSet execute(Query query, Connection connection, Map<String, Object> parameters) throws SQLException {
        NamedParameterPreparedStatement statement = NamedParameterPreparedStatement.createNamedParameterPreparedStatement(connection, query.sql());
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            if (entry.getValue() instanceof Integer) {
                statement.setInt(entry.getKey(), (Integer) entry.getValue());
            }
        }
        return statement.executeQuery();
    }
}
