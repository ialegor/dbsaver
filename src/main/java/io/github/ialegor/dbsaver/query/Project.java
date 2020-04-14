package io.github.ialegor.dbsaver.query;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Project {
    private String name;
    private Map<String, QueryParameter> params = new HashMap<>();
    private List<Query> queries;
}
