package com.generator.generatestater.common;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface CustomizeBaseMapper<T> extends BaseMapper<T>, ExampleMapper<T>, IdsMapper<T>, InsertListMapper<T>, ConditionMapper<T> {
}
