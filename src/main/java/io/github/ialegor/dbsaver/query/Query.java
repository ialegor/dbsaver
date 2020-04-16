package io.github.ialegor.dbsaver.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Query {
    private String name;
    private String description;
    private Map<String, QueryParameter> params = new HashMap<>();

    private String template;

    @JsonProperty
    private String sql;

    @JsonProperty
    private String sqlFile;
}
