package com.generator.generatestater.test.service;

import com.generator.generatestater.test.entity.UserEx;
import com.generator.generatestater.test.dto.req.UserExReq;
import com.generator.generatestater.test.dto.res.UserExRes;
import com.generator.generatestater.test.dto.search.UserExSearch;
import java.io.Serializable;
import java.util.List;

/**
 * @author Apollo Jiang
 * @date 2021-9-25
 */
public interface UserExService {

    Object page(UserExSearch search);

    List<UserExRes> findAll();

    UserExRes findById(Serializable id);

    void save(UserExReq userExReq);

    void update(UserExReq userExReq);

    void deleteByIds(List<Serializable> ids);

}