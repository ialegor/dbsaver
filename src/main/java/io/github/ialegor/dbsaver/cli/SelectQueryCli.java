package io.github.ialegor.dbsaver.cli;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ialegor.dbsaver.query.Query;

import java.io.File;
import java.io.IOException;
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
            System.out.println("Select query to execute:");
            File[] queries = file.listFiles(pathname -> pathname.getName().endsWith(EXTENSION));
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

    private static Query parseFile(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        return mapper.readValue(file, Query.class);
    }
}
