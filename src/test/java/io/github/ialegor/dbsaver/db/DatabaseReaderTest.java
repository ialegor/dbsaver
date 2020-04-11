package io.github.ialegor.dbsaver.db;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import io.github.ialegor.dbsaver.query.Query;
import io.github.ialegor.dbsaver.test.AbstractDatabaseTest;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DatabaseReaderTest extends AbstractDatabaseTest {

    EmbeddedPostgres postgres;

    DatabaseReader databaseReader;

    @Before
    public void before() throws IOException {
        databaseReader = new DatabaseReader();
        postgres = EmbeddedPostgres.builder().start();
    }

    @Test
    public void execute_simple_test() throws SQLException {
        assertNotNull(databaseReader);
        assertNotNull(postgres);

        Query query = new Query().name("simple").sql("select now();");
        ResultSet resultSet = databaseReader.execute(query, connection());
        int rows = 0;
        while (resultSet.next()) {
            rows++;
        }
        Assert.assertEquals(1, rows);
    }

    @Test
    public void execute_selectDistricts_test() throws SQLException, IOException {
        assertNotNull(databaseReader);
        assertNotNull(postgres);

        String districtSql = IOUtils.toString(getResourceStreamByName(DatabaseReaderTest.class, "data/district.sql"), StandardCharsets.UTF_8);
        PreparedStatement statement = connection().prepareStatement(districtSql);
        statement.executeUpdate();

        Query query = new Query().name("simple").sql("select * from district;");
        ResultSet resultSet = databaseReader.execute(query, connection());
        int rows = 0;
        while (resultSet.next()) {
            rows++;
        }
        Assert.assertEquals(18, rows);

        rows = 0;
        while (resultSet.next()) {
            rows++;
        }
        Assert.assertEquals(18, rows);
    }

    private Connection connection() throws SQLException {
        return postgres.getPostgresDatabase().getConnection();
    }

    private InputStream getResourceStreamByName(Class<?> clazz, String name) {
        InputStream stream = clazz.getResourceAsStream(name);
        if (stream == null) {
            stream = clazz.getClassLoader().getResourceAsStream(name);
            if (stream == null) {
                throw new IllegalArgumentException("Resource '" + name + "' not found");
            }
        }
        return stream;
    }
}
