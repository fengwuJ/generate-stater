package ${Configuration.packageName}.${Configuration.path.service};

import ${Configuration.packageName}.${Configuration.path.dao}.${DaoClassName};
import ${Configuration.packageName}.${Configuration.path.entity}.${ClassName};
import ${Configuration.packageName}.${Configuration.path.req}.${ReqClassName};
import ${Configuration.packageName}.${Configuration.path.res}.${ResClassName};
import ${Configuration.packageName}.${Configuration.path.search}.${SearchClassName};
${InterfaceImport}
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author ${Configuration.author}
 * @date ${.now?date}
 */
@Slf4j
@Service
public class ${ServiceClassName} ${Implements} {
    @Autowired
    private ${DaoClassName} ${DaoEntityName};

    ${Override}
    public Object page(${SearchClassName} search){

        return null;
    }

    ${Override}
    public List<${ResClassName}> findAll(){

        return null;
    }

    ${Override}
    public ${ResClassName} findById(${pkType} id){


        return null;
    }

    ${Override}
    public void save(${ReqClassName} ${ReqEntityName}){

    }

    ${Override}
    public void update(${ReqClassName} ${ReqEntityName}){

    }

    ${Override}
    public void deleteByIds(List<${pkType}> ids){

    }

}