package com.jackoning.code.generate.enmus;

public enum FileType
{
    /**
     * java文件
     */
    JAVA(".java"),
    /**
     * XML配置文件
     */
    XML(".xml");

    private String type;

    FileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
