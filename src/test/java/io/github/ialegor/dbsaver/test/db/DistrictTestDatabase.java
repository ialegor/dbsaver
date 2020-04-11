package io.github.ialegor.dbsaver.test.db;

import io.github.ialegor.dbsaver.db.DatabaseReaderTest;
import io.github.ialegor.dbsaver.query.Query;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static io.github.ialegor.util.resource.ResourceUtils.getResourceStreamByName;

public class DistrictTestDatabase {

    public void apply(Connection connection) throws IOException, SQLException {
        InputStream districtSqlStream = getResourceStreamByName(DatabaseReaderTest.class, "data/district.sql");
        String districtSql = IOUtils.toString(districtSqlStream, StandardCharsets.UTF_8);
        PreparedStatement statement = connection.prepareStatement(districtSql);
        statement.executeUpdate();
    }

    public Query selectAll() {
        return new Query().name("simple").sql("select * from district;");
    }
}
