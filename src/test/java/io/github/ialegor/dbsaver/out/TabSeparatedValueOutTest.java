package io.github.ialegor.dbsaver.out;

import io.github.ialegor.dbsaver.db.DatabaseReader;
import io.github.ialegor.dbsaver.test.AbstractDatabaseTest;
import io.github.ialegor.dbsaver.test.db.DistrictTestDatabase;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.*;

public class TabSeparatedValueOutTest extends AbstractDatabaseTest {

    DatabaseReader databaseReader;

    TabSeparatedValueOut out;

    @Override
    protected void beforeNext() {
        databaseReader = new DatabaseReader();
        out = new TabSeparatedValueOut();
    }

    @Test
    public void format_selectDistricts_test() throws Exception {
        DistrictTestDatabase database = new DistrictTestDatabase();
        apply(database);

        ResultSet resultSet = databaseReader.execute(database.selectAll(), connection());

        List<String> strings = out.format(resultSet);

        assertEquals(19, strings.size());
    }
}
