package io.github.ialegor.dbsaver.cli;

import io.github.ialegor.dbsaver.query.Query;
import io.github.ialegor.dbsaver.query.QueryParameter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DetermineParameterCli {

    public static Map<String, Object> determine(Query query) {
        if (query.getParams() == null || query.getParams().isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Object> params = new HashMap<>();
        for (Map.Entry<String, QueryParameter> entry : query.getParams().entrySet()) {
            switch (entry.getValue().getType()) {
                case SHORT:
                case INTEGER:
                case LONG:
                    params.put(entry.getKey(), askLongValue(entry.getKey(), entry.getValue()));
            }
        }
        return params;
    }

    private static Long askLongValue(String name, QueryParameter parameter) {
        System.out.println(parameter.getDescription());
        System.out.print(name);
        System.out.print(": ");
        return new Scanner(System.in).nextLong();
    }
}
