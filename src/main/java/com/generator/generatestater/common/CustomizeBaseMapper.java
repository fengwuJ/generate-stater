package com.generator.generatestater.common;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;
import tk.mybatis.mapper.provider.SpecialProvider;

public interface CustomizeBaseMapper<T> extends BaseMapper<T>, ExampleMapper<T>, IdsMapper<T>, InsertListMapper<T>, ConditionMapper<T> {

    //忽略id，回写主键
    @Options(
            useGeneratedKeys = true
    )
    @InsertProvider(
            type = SpecialProvider.class,
            method = "dynamicSQL"
    )
    int insert(T var1);

    //忽略id，回写主键
    @Options(
            useGeneratedKeys = true
    )
    @InsertProvider(
            type = SpecialProvider.class,
            method = "dynamicSQL"
    )
    int insertSelective(T var1);

}
