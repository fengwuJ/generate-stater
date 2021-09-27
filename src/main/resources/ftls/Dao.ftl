package ${Configuration.packageName}.${Configuration.path.dao};

import ${Configuration.packageName}.${Configuration.path.entity}.${ClassName};
<#if Configuration.tkMapperEnable>
import ${Configuration.tkMapper.baseMapper};
</#if>

import org.apache.ibatis.annotations.Mapper;
import java.io.Serializable;
import java.util.List;

/**
 * @author ${Configuration.author}
 * @date ${.now?date}
 */
<#if Configuration.tkMapperEnable><#-- tkMapper -->
public interface ${DaoClassName} extends ${CustomizeMapperClass}<${ClassName}> {

}
</#if>