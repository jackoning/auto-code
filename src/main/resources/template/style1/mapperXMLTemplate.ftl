<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackage}.${entity}Mapper">
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${entityPackage}.${entity}">
        <#list table.fields as field>
            <#if field.keyFlag><#--生成主键排在第一位-->
                <id column="${field.name}" property="${field.propertyName}" />
            <#else>
                <result column="${field.name}" property="${field.propertyName}" />
            </#if>
        </#list>
    </resultMap>

    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        <#list table.fields as field>
            <#if field_has_next>
                ${field.name},
             <#else>
                 ${field.name}
            </#if>
        </#list>
    </sql>
</mapper>
