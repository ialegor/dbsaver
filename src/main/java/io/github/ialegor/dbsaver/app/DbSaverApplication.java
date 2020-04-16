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
import io.github.ialegor.dbsaver.query.QueryResult;
import org.apache.commons.io.IOUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
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
    private File dbFile;

    @CommandLine.Option(names = {"--out-format"}, description = "Output format: ${COMPLETION-CANDIDATES}")
    private OutFormat outFormat = OutFormat.stdout;

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

        if (dbFile == null) {
            dbFile = directoryFile;
        }

        ConnectionString cs = SelectDatabaseCli.determine(dbFile);

        DatabaseReader reader = new DatabaseReader();

        List<QueryResult> resultSets;
        if (project != null) {
            Map<String, Object> params = DetermineParameterCli.determine(project.getParams());
            resultSets = reader.execute(project, cs, params);
        } else if (query != null) {
            Map<String, Object> params = DetermineParameterCli.determine(query.getParams());
            resultSets = Collections.singletonList(reader.execute(query, cs, params));
        } else {
            System.out.println("Not detected project or query!");
            return 1;
        }

        TabSeparatedValueOut tsvOut = new TabSeparatedValueOut();
        switch (outFormat) {
            case tsv:
                for (QueryResult result : resultSets) {
                    List<String> lines = tsvOut.format(result);
                    String fileName = resolveFileName(result.getQuery(), result.getParams()) + ".tsv";
                    FileWriter writer = new FileWriter(fileName, false);
                    IOUtils.writeLines(lines, System.lineSeparator(), writer);
                    writer.flush();
                    System.out.println("Written file " + fileName);
                }
                break;
            case stdout:
                for (QueryResult result : resultSets) {
                    List<String> lines = tsvOut.format(result);
                    for (String line : lines) {
                        System.out.println(line);
                    }
                    System.out.println();
                    System.out.println();
                }
                break;
            default:
                throw new RuntimeException("Unhandled out format: " + outFormat);
        }

        return 0;
    }

    public static void main(String... args) throws Exception {
        int exitCode = new CommandLine(new DbSaverApplication()).execute(args);
        System.exit(exitCode);
    }

    private static String resolveFileName(Query query, Map<String, Object> params) {
        if (query.getTemplate() == null || query.getTemplate().isEmpty()) {
            return query.getName();
        }
        return resolveFileName(query.getTemplate(), params);
    }

    private static String resolveFileName(String template, Map<String, Object> params) {
        String result = template;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            result = result.replace("${" + entry.getKey() + "}", entry.getValue().toString());
        }
        return result;
    }

    public enum OutFormat {
        stdout,
        tsv,
        // TODO: json
    }
}
