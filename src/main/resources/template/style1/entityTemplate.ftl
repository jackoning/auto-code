package ${entityPackage};

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.io.Serializable;
import java.util.Date;

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
@TableName("${table.name}")
public class ${entity} implements Serializable {

    private static final long serialVersionUID = 1L;
<#-- 循环属性名称 -->
<#list table.fields as field>

<#if field.comment??>
    /**
     * ${field.comment}
     */
</#if>
<#if field.keyIdentityFlag>
    @TableId(value="${field.name}", type= IdType.AUTO)
    private ${field.propertyType} ${field.propertyName};
<#else>
    @TableField(value="${field.name}")
    private ${field.propertyType} ${field.propertyName};
</#if>
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
