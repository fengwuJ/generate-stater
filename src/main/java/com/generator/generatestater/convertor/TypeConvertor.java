package com.generator.generatestater.convertor;

import java.sql.JDBCType;

public interface TypeConvertor {

    /**
     * 将JDBC类型转换为Java类型
     *
     * @param type JDBC类型名
     * @return Java类型名
     */
    String convertType(JDBCType type);

}
