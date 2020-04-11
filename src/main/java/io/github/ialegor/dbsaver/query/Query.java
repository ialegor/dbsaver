package io.github.ialegor.dbsaver.query;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = true)
public class Query {
    private String name;
    private String description;
    private String sql;
}
