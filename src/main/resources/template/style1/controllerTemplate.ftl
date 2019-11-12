package ${controllerPackage};

import ${entityPackage}.${entity};
import ${servicePackage}.${entity}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

/**
 * 描述: ${table.comment}
 * @author: ${author}
 * @date: ${date}
 */
@Api("${table.comment} API")
@RestController
@RequestMapping(value="/${entity?uncap_first}")
public class ${entity}Controller {

    @Autowired
    private ${entity}Service ${entity?uncap_first}Service;

    /**
     * 新增
     * @param ${entity?uncap_first}
     */
    @ApiOperation(value = "${table.comment}新增接口",notes = "${table.comment}",response = ${entity}.class)
    @PostMapping("/save")
    public boolean save(@RequestBody ${entity} ${entity?uncap_first}){
       return ${entity?uncap_first}Service.insert(${entity?uncap_first});
    }

    /**
    * 更新
    * @param ${entity?uncap_first}
    */
    @ApiOperation(value = "${table.comment}更新接口",notes = "${table.comment}",response = ${entity}.class)
    @PostMapping("/save")
    public boolean update(@RequestBody ${entity} ${entity?uncap_first}){
        return ${entity?uncap_first}Service.updateById(${entity?uncap_first});
    }

    /**
     * 删除
     */
    @ApiOperation(value = "${table.comment}删除接口",notes = "${table.comment}",response = ${entity}.class)
    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable String id){
        return ${entity?uncap_first}Service.deleteById(id);
    }

    /**
    * 根据id获取信息
    * @param id id
    * @return ${table.comment}
    */
    @ApiOperation(value = "根据id获取${table.comment}信息接口",notes = "${table.comment}",response = ${entity}.class)
    @GetMapping("/get/{id}")
    public ${entity} get(@PathVariable String id){
        return ${entity?uncap_first}Service.selectById(id);
    }

    /**
    * 获取列表
    * @return List<${entity}>
    */
    @ApiOperation(value = "${table.comment}查询接口",notes = "${table.comment}",response = ${entity}.class)
    @GetMapping("/queryList")
    public List<${entity}> queryPage(){
        Wrapper
        return ${entity?uncap_first}Service.selectPage();
    }
}
