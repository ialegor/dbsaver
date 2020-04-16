package io.github.ialegor.dbsaver.query;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.util.Map;

@Data
@AllArgsConstructor
public class QueryResult {

    private Query query;
    private Map<String, Object> params;
    private ResultSet result;
}
