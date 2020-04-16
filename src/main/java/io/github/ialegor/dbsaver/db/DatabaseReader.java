package io.github.ialegor.dbsaver.db;

import com.axiomalaska.jdbc.NamedParameterPreparedStatement;
import io.github.ialegor.dbsaver.query.Project;
import io.github.ialegor.dbsaver.query.Query;
import io.github.ialegor.dbsaver.query.QueryResult;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class DatabaseReader {

    public QueryResult execute(Query query, ConnectionString connectionString) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", connectionString.getUsername());
        properties.setProperty("password", connectionString.getPassword());
        Connection connection = DriverManager.getConnection(connectionString.getConnectionString(), properties);
        return execute(query, connection);
    }

    public QueryResult execute(Query query, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query.getSql());
        return new QueryResult(query, Collections.emptyMap(), resultSet);
    }

    public QueryResult execute(Query query, ConnectionString connectionString, Map<String, Object> parameters) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", connectionString.getUsername());
        properties.setProperty("password", connectionString.getPassword());
        Connection connection = DriverManager.getConnection(connectionString.getConnectionString(), properties);
        return execute(query, connection, parameters);
    }

    public QueryResult execute(Query query, Connection connection, Map<String, Object> parameters) throws SQLException {
        NamedParameterPreparedStatement statement = NamedParameterPreparedStatement.createNamedParameterPreparedStatement(connection, query.getSql());
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            if (entry.getValue() instanceof Short) {
                statement.setShort(entry.getKey(), (Short) entry.getValue());
            } else if (entry.getValue() instanceof Integer) {
                statement.setInt(entry.getKey(), (Integer) entry.getValue());
            } else if (entry.getValue() instanceof Long) {
                statement.setLong(entry.getKey(), (Long) entry.getValue());
            } else if (entry.getValue() instanceof LocalDate) {
                statement.setDate(entry.getKey(), Date.valueOf((LocalDate) entry.getValue()));
            }
        }
        ResultSet result = statement.executeQuery();
        return new QueryResult(query, parameters, result);
    }

    public List<QueryResult> execute(Project project, ConnectionString cs, Map<String, Object> params) throws SQLException {
        List<QueryResult> results = new ArrayList<>();
        for (Query query : project.getQueries()) {
            results.add(execute(query, cs, params));
        }
        return results;
    }
}
