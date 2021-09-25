package com.generator.generatestater.test.dto.req;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Apollo Jiang
 * @date 2021-9-25
 */
@Data
public class UserExReq implements Serializable {
    /**
     * 主键自增ID
     */
    private Integer id;

    /**
     * 中文名
     */
    private String name;

    /**
     * 英文名
     */
    private String enName;



    
}