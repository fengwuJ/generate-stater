package ${Configuration.packageName}.${Configuration.path.interf};

import ${Configuration.packageName}.${Configuration.path.req}.${ReqClassName};
import ${Configuration.packageName}.${Configuration.path.res}.${ResClassName};
import ${Configuration.packageName}.${Configuration.path.search}.${SearchClassName};
import java.io.Serializable;
import java.util.List;

/**
 * @author ${Configuration.author}
 * @date ${.now?date}
 */
public interface ${InterfaceClassName} {

    Object page(${SearchClassName} search);

    List<${ResClassName}> findAll();

    ${ResClassName} findById(${pkType} id);

    void save(${ReqClassName} ${ReqEntityName});

    void update(${ReqClassName} ${ReqEntityName});

    void deleteByIds(List<${pkType}> ids);

}