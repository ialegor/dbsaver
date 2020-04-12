package io.github.ialegor.dbsaver.app;

import io.github.ialegor.dbsaver.cli.DetermineParameterCli;
import io.github.ialegor.dbsaver.cli.SelectDatabaseCli;
import io.github.ialegor.dbsaver.cli.SelectQueryCli;
import io.github.ialegor.dbsaver.db.ConnectionString;
import io.github.ialegor.dbsaver.db.DatabaseReader;
import io.github.ialegor.dbsaver.out.TabSeparatedValueOut;
import io.github.ialegor.dbsaver.query.Query;

import java.sql.ResultSet;
import java.util.Map;

public class DbSaverApplication {

    public static void main(String... args) throws Exception {
        Query query = SelectQueryCli.determine(args[0]);
        System.out.println(query);
        ConnectionString cs = SelectDatabaseCli.determine(args[0]);
        System.out.println(cs);

        DatabaseReader reader = new DatabaseReader();

        Map<String, Object> params = DetermineParameterCli.determine(query);

        ResultSet resultSet = reader.execute(query, cs, params);

        TabSeparatedValueOut out = new TabSeparatedValueOut();
        out.format(resultSet);
    }
}
