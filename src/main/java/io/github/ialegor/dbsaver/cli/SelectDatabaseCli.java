package io.github.ialegor.dbsaver.cli;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ialegor.dbsaver.db.ConnectionString;
import io.github.ialegor.dbsaver.db.ConnectionStringImpl;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SelectDatabaseCli {

    private static final String EXTENSION = ".dbsd.json";

    public static ConnectionString determine() throws IOException {
        return determine(".");
    }

    public static ConnectionString determine(String directory) throws IOException {
        File file = new File(directory);
        if (file.exists() && file.isFile() && file.getName().endsWith(EXTENSION)) {
            return parseFile(file);
        } else if (file.isDirectory()) {
            System.out.println("Select database to connect:");
            File[] dbs = file.listFiles(pathname -> pathname.getName().endsWith(EXTENSION));
            for (int i = 0, queriesLength = dbs.length; i < queriesLength; i++) {
                File dbFiles = dbs[i];
                ConnectionString query = parseFile(dbFiles);
                System.out.printf("%d) %s%n", i + 1, query.getName());
            }
            System.out.printf("0) For exit%n> ");
            Scanner scanner = new Scanner(System.in);
            int i = scanner.nextInt();
            return parseFile(dbs[i - 1]);
        } else {
            throw new RuntimeException("Not implemented!");
        }
    }

    private static ConnectionString parseFile(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        return mapper.readValue(file, ConnectionStringImpl.class);
    }
}
