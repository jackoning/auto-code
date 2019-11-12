package com.jackoning.code.generate.config;


public class GlobalConfig {

    /*文件编码*/
    private String system_encoding = "utf-8";
    /*模板*/
    private String templatepath = "/template/stytle1";
    /*
    文件输出路径，不配置的话默认输出当前项目的resources/code目录下
     */
    private String outputDir;
    private String parentPackage;
    private String entityPackage = "entity";
    private String entityDTOPackage = "entity.dto";
    private String entityVOPackage = "entity.vo";
    /*dao层包名*/
    private String mapperPackage = "mapper";
    private String mapperXMLPackage = "mapper";
    private String serviceImplPackage = "service.Impl";
    private String servicePackage = "service";
    private String controllerPackage = "controller";
    /**
     * 如果不设置则获取所有表
     */
    private String[] tableNames;
    /*表前缀*/
    private String[] TablePrefix;
    private String author="";

    public String getSystem_encoding() {
        return system_encoding;
    }

    public void setSystem_encoding(String system_encoding) {
        this.system_encoding = system_encoding;
    }

    public String getTemplatepath() {
        return templatepath;
    }

    public void setTemplatepath(String templatepath) {
        this.templatepath = templatepath;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String[] getTableNames() {
        return tableNames;
    }

    public void setTableNames(String[] tableNames) {
        this.tableNames = tableNames;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String[] getTablePrefix() {
        return TablePrefix;
    }

    public void setTablePrefix(String[] tablePrefix) {
        TablePrefix = tablePrefix;
    }

    public String getMapperPackage() {
        return mapperPackage;
    }

    public void setMapperPackage(String mapperPackage) {
        this.mapperPackage = mapperPackage;
    }

    public String getServiceImplPackage() {
        return serviceImplPackage;
    }

    public void setServiceImplPackage(String serviceImplPackage) {
        this.serviceImplPackage = serviceImplPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getParentPackage() {
        return parentPackage;
    }

    public void setParentPackage(String parentPackage) {
        this.parentPackage = parentPackage;
    }

    public String getEntityDTOPackage() {
        return entityDTOPackage;
    }

    public void setEntityDTOPackage(String entityDTOPackage) {
        this.entityDTOPackage = entityDTOPackage;
    }

    public String getEntityVOPackage() {
        return entityVOPackage;
    }

    public void setEntityVOPackage(String entityVOPackage) {
        this.entityVOPackage = entityVOPackage;
    }

    public String getMapperXMLPackage() {
        return mapperXMLPackage;
    }

    public void setMapperXMLPackage(String mapperXMLPackage) {
        this.mapperXMLPackage = mapperXMLPackage;
    }
}
