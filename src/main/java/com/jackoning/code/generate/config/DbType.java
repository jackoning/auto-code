package com.jackoning.code.generate.config;


public enum DbType {
    MYSQL("mysql"),
    ORACLE("oracle"),
    POSTGRE_SQL("postgre_sql");

    private final String value;

    private DbType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
