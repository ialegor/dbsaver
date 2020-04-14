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

    @Deprecated
    public static Map<String, Object> determine(Query query) {
        Map<String, QueryParameter> params1 = query.getParams();
        return determine(params1);
    }

    public static Map<String, Object> determine(Map<String, QueryParameter> params) {
        if (params == null || params.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Object> determineParams = new HashMap<>();
        for (Map.Entry<String, QueryParameter> entry : params.entrySet()) {
            switch (entry.getValue().getType()) {
                case SHORT:
                    determineParams.put(entry.getKey(), askShortValue(entry.getKey(), entry.getValue()));
                    break;
                case INTEGER:
                    determineParams.put(entry.getKey(), askIntValue(entry.getKey(), entry.getValue()));
                    break;
                case LONG:
                    determineParams.put(entry.getKey(), askLongValue(entry.getKey(), entry.getValue()));
                    break;
                case DATE:
                    determineParams.put(entry.getKey(), askDateValue(entry.getKey(), entry.getValue()));
                    break;
                case TIME:
                    determineParams.put(entry.getKey(), askTimeValue(entry.getKey(), entry.getValue()));
                    break;
                case DATETIME:
                    determineParams.put(entry.getKey(), askDateTimeValue(entry.getKey(), entry.getValue()));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + entry.getValue().getType());
            }
        }
        return determineParams;
    }

    private static Short askShortValue(String name, QueryParameter parameter) {
        System.out.println(parameter.getDescription());
        System.out.print(name);
        System.out.print(": ");
        return new Scanner(System.in).nextShort();
    }

    private static Integer askIntValue(String name, QueryParameter parameter) {
        System.out.println(parameter.getDescription());
        System.out.print(name);
        System.out.print(": ");
        return new Scanner(System.in).nextInt();
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
        System.out.printf("%s: ", name);
        // TODO: implement time asking
        throw new RuntimeException();
    }

    private static LocalDateTime askDateTimeValue(String name, QueryParameter parameter) {
        System.out.println(parameter.getDescription());
        System.out.printf("%s: ", name);
        // TODO: implement datetime asking
        throw new RuntimeException();
    }
}
