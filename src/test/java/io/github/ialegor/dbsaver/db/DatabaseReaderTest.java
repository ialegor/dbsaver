package io.github.ialegor.dbsaver.db;

import io.github.ialegor.dbsaver.query.Query;
import io.github.ialegor.dbsaver.test.AbstractDatabaseTest;
import io.github.ialegor.dbsaver.test.db.DistrictTestDatabase;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class DatabaseReaderTest extends AbstractDatabaseTest {

    DatabaseReader databaseReader;

    @Override
    protected void beforeNext() {
        databaseReader = new DatabaseReader();
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
        DistrictTestDatabase database = new DistrictTestDatabase();
        apply(database);

        ResultSet resultSet = databaseReader.execute(database.selectAll(), connection());
        int rows = 0;
        while (resultSet.next()) {
            rows++;
        }
        Assert.assertEquals(18, rows);
    }
}
