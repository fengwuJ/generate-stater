package ${Configuration.packageName}.${Configuration.path.controller};

import ${Configuration.packageName}.${Configuration.path.req}.${ReqClassName};
import ${Configuration.packageName}.${Configuration.path.res}.${ResClassName};
import ${Configuration.packageName}.${Configuration.path.search}.${SearchClassName};
${ServiceImport}
<#if Configuration.swaggerEnable>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author ${Configuration.author}
 * @date ${.now?date}
 */
<#if Configuration.swaggerEnable>
@Api(value = "/${EntityName}", tags = "${ClassName}管理接口")
</#if>
@RestController
@RequestMapping(value = "/${EntityName}")
public class ${ControllerClassName} {
    @Autowired
    private ${ServiceClassName} ${ServiceEntityName};

    <#if Configuration.swaggerEnable>
    @ApiOperation(value = "分页查询${ClassName}列表", httpMethod = "GET")
    </#if>
    @GetMapping(value = "/page")
    public Object page(@RequestBody ${SearchClassName} search) {

        Object result = ${ServiceEntityName}.page(search);

        return result;
    }

    <#if Configuration.swaggerEnable>
    @ApiOperation(value = "查询${ClassName}列表", httpMethod = "GET")
    </#if>
    @GetMapping(value = "/list")
    public List<${ResClassName}> list() {

        List<${ResClassName}> ${ResEntityName}List = ${ServiceEntityName}.findAll();

        return ${ResEntityName}List;
    }

    <#if Configuration.swaggerEnable>
    @ApiOperation(value = "查看${ClassName}详情", httpMethod = "GET")
    </#if>
    @GetMapping(value = "/{id}")
    public ${ResClassName} detail(@PathVariable("id") ${pkType} id) {

        ${ResClassName} ${ResEntityName} = ${ServiceEntityName}.findById(id);

        return ${ResEntityName};
    }

    <#if Configuration.swaggerEnable>
    @ApiOperation(value = "创建${ClassName}", httpMethod = "POST")
    </#if>
    @PostMapping
    public void add(@RequestBody ${ReqClassName} ${ReqEntityName}) {

        ${ServiceEntityName}.save(${ReqEntityName});

    }

    <#if Configuration.swaggerEnable>
    @ApiOperation(value = "修改${ClassName}信息", httpMethod = "PUT")
    </#if>
    @PutMapping
    public void update(@RequestBody ${ReqClassName} ${ReqEntityName}) {

        ${ServiceEntityName}.update(${ReqEntityName});

    }


    <#if Configuration.swaggerEnable>
    @ApiOperation(value = "删除${ClassName}", httpMethod = "DELETE")
    </#if>
    @DeleteMapping
    public void delete(@RequestBody List<${pkType}> ids) {

        ${ServiceEntityName}.deleteByIds(ids);

    }

}
