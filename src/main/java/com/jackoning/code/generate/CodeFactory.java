package com.jackoning.code.generate;

import com.jackoning.code.generate.config.GlobalConfig;
import com.jackoning.code.generate.enmus.CodeType;
import com.jackoning.code.generate.enmus.FileType;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;


public class CodeFactory
{
  private ICallBack callBack;
  private GlobalConfig globalConfig;

  /**
   * 把配置数据注入模版，生成代码文件
   * @param type
   * @param data
   * @throws TemplateException
   * @throws IOException
   */
  public void generateFile(String type, Map data) throws TemplateException,IOException
  {

      String entityName = data.get("entity").toString();
      //获取生成的文件路径
      String fileNamePath = getCodePath(type, entityName);
      String fileDir = StringUtils.substringBeforeLast(fileNamePath, File.separator);
      File f = new File(fileDir);
      if (!f.exists()){
        f.mkdirs();
      }
      CodeType[] cts = CodeType.values();
      String templateName = "";
      for (CodeType ct : cts) {
        if (ct.getType().equals(type)){
          templateName = ct.getTemplate();
          break;
        }
      }
      //获取模版信息
      Template template = getConfiguration().getTemplate(templateName);
      //生成的文件编码
      Writer out = new OutputStreamWriter(
        new FileOutputStream(fileNamePath), globalConfig.getSystem_encoding());
      //根据模版生成代码文件
      template.process(data, out);
      out.close();
  }

  public Configuration getConfiguration()
          throws IOException
  {
    Configuration cfg = new Configuration();
    cfg.setClassForTemplateLoading(super.getClass(), globalConfig.getTemplatepath());
    cfg.setLocale(Locale.CHINA);
    cfg.setDefaultEncoding("UTF-8");
    return cfg;
  }

  public String getClassPath()
  {
    String path = Thread.currentThread().getContextClassLoader().getResource("./").getPath();
    return path;
  }


  /**
   * 获取代码生成的文件路径
   * @param type
   * @param entityName
   * @return
   */
  public String getCodePath(String type, String entityName)
  {
    StringBuilder path = new StringBuilder();
    if (StringUtils.isNotBlank(type)) {
      String codeType = Enum.valueOf(CodeType.class, type).toString();
      if(StringUtils.isEmpty(this.globalConfig.getOutputDir())){
         throw new NullPointerException("代码生成路径不能为空！");
      }
      path.append(this.globalConfig.getOutputDir() + File.separator);
      String parentPackage = globalConfig.getParentPackage() + ".";
      if(CodeType.entity.getType().equals(codeType)){
        //添加实体
        String str = parentPackage + globalConfig.getEntityPackage();
        str = str.replace(".", File.separator);
        path.append(str);
        path.append(File.separator);
        path.append(entityName).append(FileType.JAVA.getType());
      }else if(CodeType.entityDTO.getType().equals(codeType)){
        //添加DTO
        String str = parentPackage + globalConfig.getEntityDTOPackage();
        str = str.replace(".", File.separator);
        path.append(str);
        path.append(File.separator);
        path.append(entityName).append("DTO").append(FileType.JAVA.getType());
      }else if(CodeType.entityVO.getType().equals(codeType)){
        //添加VO
        String str = parentPackage + globalConfig.getEntityVOPackage();
        str = str.replace(".", File.separator);
        path.append(str);
        path.append(File.separator);
        path.append(entityName).append("VO").append(FileType.JAVA.getType());
      }else if(CodeType.mapper.getType().equals(codeType)){
        //添加mapper
        String str = parentPackage + globalConfig.getMapperPackage();
        str = str.replace(".", File.separator);
        path.append(str);
        path.append(File.separator);
        path.append(entityName).append("Mapper").append(FileType.JAVA.getType());
      }else if(CodeType.mapperXML.getType().equals(codeType)){
        //添加mapper.xml
        String str = parentPackage + globalConfig.getMapperXMLPackage();
        str = str.replace(".", File.separator);
        path.append(str);
        path.append(File.separator);
        path.append(entityName).append("Mapper").append(FileType.XML.getType());
      }else if(CodeType.service.getType().equals(codeType)){
        //添加服务
        String str = parentPackage + globalConfig.getServicePackage();
        str = str.replace(".", File.separator);
        path.append(str);
        path.append(File.separator);
        path.append(entityName).append("Service").append(".java");
      }else if(CodeType.serviceImpl.getType().equals(codeType)){
        //添加服务实现
        String str = parentPackage + globalConfig.getServiceImplPackage();
        str = str.replace(".", File.separator);
        path.append(str);
        path.append(File.separator);
        path.append(entityName).append("ServiceImpl").append(".java");
      }else if(CodeType.controller.getType().equals(codeType)){
        //添加控制器
        String str = parentPackage + globalConfig.getControllerPackage();
        str = str.replace(".", File.separator);
        path.append(str);
        path.append(File.separator);
        path.append(entityName).append("Controller").append(".java");
      }else{
        throw new IllegalArgumentException("type is not found");
        //其他类型文件生成
      }
    } else {
      throw new IllegalArgumentException("type is null");
    }
    System.out.println(path.toString());
    return path.toString();
  }

  public void invoke(String type) throws Exception{
    Map data;
    data = this.callBack.execute();
    generateFile(type, data);
  }

  public ICallBack getCallBack() {
    return this.callBack;
  }

  public void setCallBack(ICallBack callBack) {
    this.callBack = callBack;
  }

  public GlobalConfig getGlobalConfig() {
    return globalConfig;
  }

  public void setGlobalConfig(GlobalConfig globalConfig) {
    this.globalConfig = globalConfig;
  }

}
