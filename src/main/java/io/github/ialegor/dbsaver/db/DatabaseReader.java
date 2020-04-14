package io.github.ialegor.dbsaver.db;

import com.axiomalaska.jdbc.NamedParameterPreparedStatement;
import io.github.ialegor.dbsaver.query.Project;
import io.github.ialegor.dbsaver.query.Query;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DatabaseReader {

    public ResultSet execute(Query query, ConnectionString connectionString) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", connectionString.getUsername());
        properties.setProperty("password", connectionString.getPassword());
        Connection connection = DriverManager.getConnection(connectionString.getConnectionString(), properties);
        return execute(query, connection);
    }

    public ResultSet execute(Query query, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query.getSql());
    }

    public ResultSet execute(Query query, ConnectionString connectionString, Map<String, Object> parameters) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", connectionString.getUsername());
        properties.setProperty("password", connectionString.getPassword());
        Connection connection = DriverManager.getConnection(connectionString.getConnectionString(), properties);
        return execute(query, connection, parameters);
    }

    public ResultSet execute(Query query, Connection connection, Map<String, Object> parameters) throws SQLException {
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
        return statement.executeQuery();
    }

    public List<ResultSet> execute(Project project, ConnectionString cs, Map<String, Object> params) throws SQLException {
        List<ResultSet> resultSets = new ArrayList<>();
        for (Query query : project.getQueries()) {
            resultSets.add(execute(query, cs, params));
        }
        return resultSets;
    }
}
