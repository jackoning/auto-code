package com.jackoning.code.generate;

import com.sun.org.apache.bcel.internal.classfile.Code;
import com.jackoning.code.generate.config.DataSourceConfig;
import com.jackoning.code.generate.config.GlobalConfig;
import com.jackoning.code.generate.enmus.CodeType;
import com.jackoning.code.generate.po.TableField;
import com.jackoning.code.generate.po.TableInfo;
import com.jackoning.code.generate.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerate implements ICallBack{

  private static Logger logger = LoggerFactory.getLogger(CodeGenerate.class);

  private List<TableInfo> tableInfoList;
  private TableInfo tableInfo;
  private GlobalConfig globalConfig;
  private DataSourceConfig dataSourceConfig;

    public CodeGenerate() {

    }

    public CodeGenerate(GlobalConfig globalConfig,DataSourceConfig dataSourceConfig)
    {
        this.globalConfig = globalConfig;
        this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public Map<String, Object> execute() {
        Map<String, Object> map = new HashMap();
        String parentPackage = globalConfig.getParentPackage() + ".";
        //实体的包名
        map.put("entityPackage", parentPackage + globalConfig.getEntityPackage());
        map.put("entityDTOPackage", parentPackage + globalConfig.getEntityDTOPackage());
        map.put("entityVOPackage", parentPackage + globalConfig.getEntityVOPackage());
        map.put("controllerPackage", parentPackage + globalConfig.getControllerPackage());
        map.put("servicePackage", parentPackage + globalConfig.getServicePackage());
        map.put("serviceImplPackage", parentPackage + globalConfig.getServiceImplPackage());
        map.put("mapperPackage", parentPackage + globalConfig.getMapperPackage());
        map.put("mapperXMLPackage", parentPackage + globalConfig.getMapperXMLPackage());
        //移除表前缀，表名之间的下划线，得到实体类型
        String entity = CommonUtils.getNoUnderlineStr(CommonUtils.removePrefix(tableInfo.getName().toLowerCase(),globalConfig.getTablePrefix()));
        //实体名称
        map.put("entity", StringUtils.capitalize(entity));
        //创建作者
        map.put("author", globalConfig.getAuthor());
        map.put("date",  CommonUtils.getFormatTime("yyyy-MM-dd", new Date() ));
        //表信息
        map.put("table", tableInfo);
        for (TableField field:tableInfo.getFields()) {
            //获取主键字段信息
            if(field.isKeyIdentityFlag()){
                map.put("tbKey", field.getName());
                map.put("tbKeyType", field.getPropertyType());
                break;
            }
        }
        return map;
    }


    /**
     * 生成代码文件
     * @return
     */
    public boolean generateToFile() {
        initConfig();
        for(TableInfo tableInfo : tableInfoList){
            //当前需要生成的表
            this.tableInfo = tableInfo;
            logger.info("------[表：" + tableInfo.getName() + "]------- 代码生成中。。。");
            try{
                CodeFactory codeFactory = new CodeFactory();
                codeFactory.setCallBack(this);
                codeFactory.setGlobalConfig(globalConfig);
                CodeType[] cts = CodeType.values();
                for (CodeType ct : cts){
                    codeFactory.invoke(ct.getType());
                }
                logger.info("------[表：" + tableInfo.getName() + "]------- 代码生成成功。。。");
            }catch (Exception e){
                e.printStackTrace();
                logger.info("-------[表：" + tableInfo.getName() + "]------- 代码生成失败。。。");
                return false;
            }
        }
        return true;
    }

    private void initConfig(){
      if(dataSourceConfig == null){
          throw new RuntimeException("dataSourceConfig is null");
      }
      if(globalConfig == null){
          throw new RuntimeException("globalConfig is null");
      }
      tableInfoList = dataSourceConfig.getTablesInfo(globalConfig.getTableNames());
    }
}
