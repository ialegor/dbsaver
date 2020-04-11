package io.github.ialegor.dbsaver.cli;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ialegor.dbsaver.db.ConnectionString;
import io.github.ialegor.dbsaver.db.ConnectionStringImpl;

import java.io.File;
import java.io.IOException;

public class SelectDatabaseCli {

    public static ConnectionString determine() throws IOException {
        return determine(".");
    }

    public static ConnectionString determine(String directory) throws IOException {
        File file = new File(directory);
        if (file.exists() && file.isFile() && file.getName().endsWith(".dbsd.json")) {
            return parseFile(file);
        } else if (file.isDirectory()) {
            throw new RuntimeException("Not implemented!");
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
