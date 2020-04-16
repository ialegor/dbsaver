package io.github.ialegor.dbsaver.query;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QueryParameter {
    private String description;
    private Type type;
    private boolean array = false;
    // TODO: default value
}
