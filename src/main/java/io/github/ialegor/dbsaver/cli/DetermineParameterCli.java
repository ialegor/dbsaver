package io.github.ialegor.dbsaver.cli;

import io.github.ialegor.dbsaver.query.Query;
import io.github.ialegor.dbsaver.query.QueryParameter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DetermineParameterCli {

    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

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
                case DATE:
                    params.put(entry.getKey(), askDateValue(entry.getKey(), entry.getValue()));
                    break;
                case TIME:
                    params.put(entry.getKey(), askTimeValue(entry.getKey(), entry.getValue()));
                    break;
                case DATETIME:
                    params.put(entry.getKey(), askDateTimeValue(entry.getKey(), entry.getValue()));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + entry.getValue().getType());
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

    private static LocalDate askDateValue(String name, QueryParameter parameter) {
        System.out.println(parameter.getDescription());
        System.out.print(name);
        System.out.print(": ");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLong()) {
            long l = scanner.nextLong();
            LocalDate date = LocalDate.now().plusDays(l);
            System.out.println("Selected date: " + date);
            return date;
        } else if (scanner.hasNextLine()) {
            String dateString = scanner.nextLine();
            if (DATE_PATTERN.matcher(dateString).matches()) {
                return LocalDate.parse(dateString);
            }
        }
        throw new RuntimeException("Неправильный формат");
    }

    private static LocalTime askTimeValue(String name, QueryParameter parameter) {
        System.out.println(parameter.getDescription());
        System.out.print(name);
        System.out.print(": ");
        // TODO: implement time asking
        throw new RuntimeException();
    }

    private static LocalDateTime askDateTimeValue(String name, QueryParameter parameter) {
        System.out.println(parameter.getDescription());
        System.out.print(name);
        System.out.print(": ");
        // TODO: implement datetime asking
        throw new RuntimeException();
    }
}
