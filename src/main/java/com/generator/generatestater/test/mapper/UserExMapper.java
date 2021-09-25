package com.generator.generatestater.test.mapper;

import com.generator.generatestater.test.entity.UserEx;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author Apollo Jiang
 * @date 2021-9-25
 */
@Mapper
public interface UserExMapper {

    UserEx get(Serializable id);

    List<UserEx> findAll();

    int insert(UserEx userEx);

    int insertBatch(List<UserEx> userExs);

    int update(UserEx userEx);

    int delete(UserEx userEx);

}
