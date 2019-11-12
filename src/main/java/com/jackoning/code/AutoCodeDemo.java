package com.jackoning.code;

import com.jackoning.code.generate.CodeGenerate;
import com.jackoning.code.generate.config.DataSourceConfig;
import com.jackoning.code.generate.config.GlobalConfig;


public class AutoCodeDemo {

    public static void main(String[] args) {
        GlobalConfig globalConfig = new GlobalConfig();//全局配置
        globalConfig.setTemplatepath("/template/style1");//自定义模板路径
        globalConfig.setAuthor("gwj");

        /**
         * TODO 待完善当设置包名为空或null的时候则不生成
         */
        globalConfig.setParentPackage("com.test");
        globalConfig.setEntityPackage("entity");
        globalConfig.setEntityDTOPackage("entity.dto");
        globalConfig.setEntityVOPackage("entity.vo");
        globalConfig.setMapperPackage("mapper");
        globalConfig.setMapperXMLPackage("mapper.mapper");
        globalConfig.setServicePackage("service");
        globalConfig.setServiceImplPackage("service.impl");
        globalConfig.setControllerPackage("controller");

        //globalConfig.setTableNames(new String[]{"t_sys_menu"});//需要生成的实体
        globalConfig.setTablePrefix(new String[]{"t_"});//生成的实体移除前缀
        globalConfig.setOutputDir("E:\\ideaSpace\\auto-code\\src\\main\\java");//文件输出路径，不配置的话默认输出当前项目的resources/code目录下

        DataSourceConfig dsc = new DataSourceConfig();//数据库配置
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://localhost:3306/gatewaydemo?characterEncoding=utf8&useUnicode=true&useSSL=false&serverTimezone=UTC");
        dsc.setUsername("root");//填写自己的数据库账号
        dsc.setPassword("123456");//填写自己的数据库密码
        CodeGenerate codeGenerate = new CodeGenerate(globalConfig, dsc);
        //生成代码
        codeGenerate.generateToFile();
    }
}
