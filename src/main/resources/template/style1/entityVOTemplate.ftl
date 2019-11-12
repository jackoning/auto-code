package ${entityPackage}.vo;

import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 描述: ${table.comment}
 * @author: ${author}
 * @date: ${date}
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "${entity}VO对象")
public class ${entity}VO implements Serializable {

    private static final long serialVersionUID = 1L;
<#-- 循环属性名称 -->
<#list table.fields as field>

<#if field.comment??>
    @ApiModelProperty(value = "${field.comment}")
</#if>
<#if field.propertyType == "Date">
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
</#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#-- 循环set/get方法 使用lombok自动生成因此省略-->
<#--<#list table.fields as field>
<#if field.propertyType == "Boolean">
<#assign getprefix="is"/>
<#else>
<#assign getprefix="get"/>
</#if>
    public ${field.propertyType} ${getprefix}${field.capitalName}() {
	return ${field.propertyName};
    }

    public void set${field.propertyName?cap_first}(${field.propertyType} ${field.propertyName}) {
        this.${field.propertyName} = ${field.propertyName};
    }
</#list>-->
}
