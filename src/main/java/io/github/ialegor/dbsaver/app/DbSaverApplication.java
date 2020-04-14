package io.github.ialegor.dbsaver.app;

import io.github.ialegor.dbsaver.cli.DetermineParameterCli;
import io.github.ialegor.dbsaver.cli.SelectDatabaseCli;
import io.github.ialegor.dbsaver.cli.SelectProjectCli;
import io.github.ialegor.dbsaver.cli.SelectQueryCli;
import io.github.ialegor.dbsaver.db.ConnectionString;
import io.github.ialegor.dbsaver.db.DatabaseReader;
import io.github.ialegor.dbsaver.out.TabSeparatedValueOut;
import io.github.ialegor.dbsaver.query.Project;
import io.github.ialegor.dbsaver.query.Query;
import picocli.CommandLine;

import java.io.File;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@SuppressWarnings("FieldMayBeFinal")
@CommandLine.Command(name = "dbsaver", mixinStandardHelpOptions = true, description = "Export sql query result")
public class DbSaverApplication implements Callable<Integer> {

    @CommandLine.Option(names = {"-d", "--directory"}, description = "Directory (current by default) with project, query or db connections")
    private File directoryFile = new File(".");

    @CommandLine.Option(names = {"-p", "--project"}, description = "Directory with projects or project file")
    private File projectFile = null;

    @CommandLine.Option(names = {"-q", "--query"}, description = "Directory with queries or query file (if project selected - ignored)")
    private File queryFile = null;

    @CommandLine.Option(names = {"--db"}, description = "Directory with database connections or database connection file")
    private File dbFile = directoryFile;

    @Override
    public Integer call() throws Exception {
        if (projectFile != null && queryFile != null) {
            System.out.println("parameter --query=" + queryFile + " is ignoring because used --project");
            queryFile = null;
        } else if (projectFile == null && queryFile == null) {
            projectFile = directoryFile;
            queryFile = directoryFile;
        }

        Project project = null;
        if (projectFile != null) {
            project = SelectProjectCli.determine(projectFile);
        }

        Query query = null;
        if (queryFile != null && project == null) {
            query = SelectQueryCli.determine(queryFile);
        }

        ConnectionString cs = SelectDatabaseCli.determine(dbFile);

        DatabaseReader reader = new DatabaseReader();

        List<ResultSet> resultSets;
        if (project != null) {
            Map<String, Object> params = DetermineParameterCli.determine(project.getParams());
            resultSets = reader.execute(project, cs, params);
        } else if (query != null) {
            Map<String, Object> params = DetermineParameterCli.determine(query.getParams());
            resultSets = Collections.singletonList(reader.execute(query, cs, params));
        } else  {
            throw new RuntimeException("Not defined project and query!");
        }

        TabSeparatedValueOut tsvOut = new TabSeparatedValueOut();
        for (ResultSet resultSet : resultSets) {
            List<String> format = tsvOut.format(resultSet);
            for (String line : format) {
                System.out.println(line);
            }
            System.out.println();
            System.out.println();
        }

        return 0;
    }

    public static void main(String... args) throws Exception {
        int exitCode = new CommandLine(new DbSaverApplication()).execute(args);
        System.exit(exitCode);

        Project project = SelectProjectCli.determine(args[0]);
        Query query = null;
        if (project == null) {
            query = SelectQueryCli.determine(args[0]);
        }
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
