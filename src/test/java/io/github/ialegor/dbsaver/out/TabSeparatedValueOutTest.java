package io.github.ialegor.dbsaver.out;

import io.github.ialegor.dbsaver.db.DatabaseReader;
import io.github.ialegor.dbsaver.test.AbstractDatabaseTest;
import io.github.ialegor.dbsaver.test.db.DistrictTestDatabase;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void format_selectParametrizedDistricts_test() throws Exception {
        DistrictTestDatabase database = new DistrictTestDatabase();
        apply(database);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("population", 100000);
        ResultSet resultSet = databaseReader.execute(database.selectAllWherePopulationMore(), connection(), parameters);

        List<String> strings = out.format(resultSet);

        assertEquals(6, strings.size());
    }
}
