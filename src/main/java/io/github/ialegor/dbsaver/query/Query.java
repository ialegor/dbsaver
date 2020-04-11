package io.github.ialegor.dbsaver.query;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true, fluent = true)
public class Query {
    private String name;
    private String description;
    private Map<String, QueryParameter> parameters = new HashMap<>();
    private String sql;
}
