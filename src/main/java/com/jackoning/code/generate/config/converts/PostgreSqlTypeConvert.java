package com.jackoning.code.generate.config.converts;

import com.jackoning.code.generate.config.DbColumnType;
import com.jackoning.code.generate.config.ITypeConvert;


public class PostgreSqlTypeConvert implements ITypeConvert {
    public PostgreSqlTypeConvert() {
    }

    @Override
    public DbColumnType processTypeConvert(String fieldType) {
        String str = fieldType.toLowerCase();
        if (!str.contains("char") && !str.contains("text")) {
            if (str.contains("bigint")) {
                return DbColumnType.LONG;
            } else if (str.contains("int")) {
                return DbColumnType.INTEGER;
            } else if (!str.contains("date") && !str.contains("time") && !str.contains("year")) {
                if (str.contains("text")) {
                    return DbColumnType.STRING;
                } else if (str.contains("bit")) {
                    return DbColumnType.BOOLEAN;
                } else if (str.contains("decimal")) {
                    return DbColumnType.BIG_DECIMAL;
                } else if (str.contains("clob")) {
                    return DbColumnType.CLOB;
                } else if (str.contains("blob")) {
                    return DbColumnType.BYTE_ARRAY;
                } else if (str.contains("float")) {
                    return DbColumnType.FLOAT;
                } else if (str.contains("double")) {
                    return DbColumnType.DOUBLE;
                } else if (!str.contains("json") && !str.contains("enum")) {
                    return str.contains("boolean") ? DbColumnType.BOOLEAN : DbColumnType.STRING;
                } else {
                    return DbColumnType.STRING;
                }
            } else {
                return DbColumnType.DATE;
            }
        } else {
            return DbColumnType.STRING;
        }
    }
}
