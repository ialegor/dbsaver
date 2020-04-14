package io.github.ialegor.dbsaver.cli;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ialegor.dbsaver.query.Query;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class SelectQueryCli {

    private static final String EXTENSION = ".dbsq.json";

    public static Query determine() throws IOException {
        return determine(".");
    }

    public static Query determine(String directory) throws IOException {
        File file = new File(directory);
        return determine(file);
    }

    public static Query determine(File file) throws IOException {
        if (file.exists() && file.isFile() && file.getName().endsWith(EXTENSION)) {
            return parseFile(file);
        } else if (file.isDirectory()) {
            File[] queries = file.listFiles(pathname -> pathname.getName().endsWith(EXTENSION));
            if (queries != null && queries.length == 0) {
                System.out.println("No queries in directory...");
                return null;
            }
            System.out.println("Select query to execute:");
            for (int i = 0, queriesLength = queries.length; i < queriesLength; i++) {
                File queryFile = queries[i];
                Query query = parseFile(queryFile);
                System.out.printf("%d) %s%n", i + 1, query.getName());
            }
            System.out.printf("0) For exit%n> ");
            Scanner scanner = new Scanner(System.in);
            int i = scanner.nextInt();
            return parseFile(queries[i - 1]);
        } else {
            throw new RuntimeException("Not implemented!");
        }
    }

    static void resolveSql(File file, Query query) throws IOException {
        if (query.getSqlFile() != null) {
            File sqlFile = new File(file.getParent() + "/" + query.getSqlFile());
            FileInputStream sqlFileStream = new FileInputStream(sqlFile);
            query.setSql(IOUtils.toString(sqlFileStream, StandardCharsets.UTF_8));
        }
    }

    static void validate(Query query) {
        Objects.requireNonNull(query, "query");
        if (query.getSql() == null && query.getSqlFile() == null) {
            throw new RuntimeException("In query '" + query.getName() + "' sql or sqlFile is not defined");
        }
    }

    private static Query parseFile(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        Query query = mapper.readValue(file, Query.class);
        validate(query);
        resolveSql(file, query);
        return query;
    }
}
