package io.github.ialegor.dbsaver.test;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import io.github.ialegor.dbsaver.test.db.DistrictTestDatabase;
import org.junit.Before;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDatabaseTest {

    protected EmbeddedPostgres postgres;

    @Before
    public final void before() throws Exception {
        postgres = EmbeddedPostgres.builder().start();
        beforeNext();
    }

    protected void beforeNext() {
    }

    protected void apply(DistrictTestDatabase database) throws SQLException, IOException {
        database.apply(connection());
    }

    protected Connection connection() throws SQLException {
        return postgres.getPostgresDatabase().getConnection();
    }
}
