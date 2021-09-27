package ${Configuration.packageName}.${Configuration.path.entity};

<#if Configuration.lombokEnable>
import lombok.Data;
</#if>
<#if Configuration.tkMapperEnable>
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
</#if>
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ${Remarks}
 *
 * @author ${Configuration.author}
 * @date ${.now?date}
 */
<#if Configuration.lombokEnable>
@Data
</#if>
<#if Configuration.tkMapperEnable>
@Table(name = "${TableName}")
</#if>
public class ${ClassName} {
    ${Properties}

    ${Methods}
}