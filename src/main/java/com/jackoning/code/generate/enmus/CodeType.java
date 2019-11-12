package com.jackoning.code.generate.enmus;

public enum CodeType
{
    /**
     * 控制器
     */
    controller("controller","controllerTemplate.ftl"),
    /**
     * 服务接口
     */
    service("service","serviceTemplate.ftl"),
    /**
     * 服务实现
     */
    serviceImpl("serviceImpl", "serviceImplTemplate.ftl"),
    /**
     * mapper映射接口
     */
    mapper("mapper", "mapperTemplate.ftl"),
    /**
     * mapper映射XML配置
     */
    mapperXML("mapperXML", "mapperXMLTemplate.ftl"),
    /**
     * 表实体
     */
    entity("entity", "entityTemplate.ftl"),
    /**
     * 服务层实体类
     */
    entityDTO("entityDTO", "entityDTOTemplate.ftl"),
    /**
     * 表示层实体类
     */
    entityVO("entityVO", "entityVOTemplate.ftl");

    private String type;

    private String template;

    CodeType(String type, String template) {
        this.type = type;
        this.template = template;
    }

    public String getType() {
        return type;
    }

    public String getTemplate() {
        return template;
    }
}
