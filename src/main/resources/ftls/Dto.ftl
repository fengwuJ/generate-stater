package ${Configuration.packageName}.${dtoPath};

<#if Configuration.lombokEnable>
import lombok.Data;
</#if>
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ${Configuration.author}
 * @date ${.now?date}
 */
<#if Configuration.lombokEnable>
@Data
</#if>
public class ${ClassName} implements Serializable {
    ${Properties}

    ${Methods}
}