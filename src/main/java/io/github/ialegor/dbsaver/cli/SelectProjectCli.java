package io.github.ialegor.dbsaver.cli;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ialegor.dbsaver.query.Project;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SelectProjectCli {

    private static final String EXTENSION = ".dbsp.json";

    public static Project determine() throws IOException {
        return determine(".");
    }

    public static Project determine(String directory) throws IOException {
        File file = new File(directory);
        return determine(file);
    }

    public static Project determine(File file) throws IOException {
        if (file.exists() && file.isFile() && file.getName().endsWith(EXTENSION)) {
            return parseFile(file);
        } else if (file.isDirectory()) {
            System.out.println("Select project:");
            File[] dbs = file.listFiles(pathname -> pathname.getName().endsWith(EXTENSION));
            for (int i = 0, queriesLength = dbs.length; i < queriesLength; i++) {
                File dbFiles = dbs[i];
                Project query = parseFile(dbFiles);
                System.out.printf("%d) %s%n", i + 1, query.getName());
            }
            System.out.printf("0) For choice query%n> ");
            Scanner scanner = new Scanner(System.in);
            int i = scanner.nextInt();
            if (i == 0) {
                return null;
            }
            return parseFile(dbs[i - 1]);
        } else {
            throw new RuntimeException("Not implemented!");
        }
    }

    private static Project parseFile(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        return mapper.readValue(file, Project.class);
    }
}
