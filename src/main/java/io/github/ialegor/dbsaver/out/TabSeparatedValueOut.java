package io.github.ialegor.dbsaver.out;

import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class TabSeparatedValueOut {

    private final static char SEPARATOR = '\t';

    public List<String> format(ResultSet rs) throws SQLException {
        List<String> strings = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int column = 0;
        StringBuilder builder = new StringBuilder();
        while (++column <= rsmd.getColumnCount()) {
            builder.append(rsmd.getColumnName(column)).append(SEPARATOR);
        }
        strings.add(builder.toString());

        Set<Integer> unmappedColumnTypes = new HashSet<>();
        while (rs.next()) {
            column = 0;
            builder.setLength(0);
            while (++column <= rsmd.getColumnCount()) {
                if (rs.getObject(column) == null) {
                    builder.append(SEPARATOR);
                    continue;
                }
                int columnType = rsmd.getColumnType(column);
                switch (columnType) {
                    case Types.BIGINT:
                    case Types.INTEGER:
                        builder.append(rs.getLong(column));
                        break;
                    case Types.REAL:
                    case Types.DOUBLE:
                        builder.append(rs.getDouble(column));
                        break;
                    case Types.VARCHAR:
                        builder.append(rs.getString(column));
                        break;
                    case Types.DATE:
                        builder.append(rs.getDate(column));
                        break;
                    case Types.TIME:
                        builder.append(rs.getTime(column));
                        break;
                    case Types.TIMESTAMP:
                        builder.append(rs.getTimestamp(column));
                        break;
                    default:
                        builder.append(rs.getString(column));
                        unmappedColumnTypes.add(columnType);
                }
                builder.append(SEPARATOR);
            }
            strings.add(builder.toString());
        }
        unmappedColumnTypes.forEach(columnType -> log.warn("unmapped type={}", columnType));

        strings.forEach(System.out::println);
        return strings;
    }
}
