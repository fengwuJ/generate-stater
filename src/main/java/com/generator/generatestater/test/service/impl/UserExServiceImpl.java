package com.generator.generatestater.test.service.impl;

import com.generator.generatestater.test.mapper.UserExMapper;
import com.generator.generatestater.test.entity.UserEx;
import com.generator.generatestater.test.service.UserExService;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author Apollo Jiang
 * @date 2021-9-25
 */
@Service
public class UserExServiceImpl implements UserExService {
    @Autowired
    private UserExMapper userExMapper;
    
    @Override
    public UserEx get(Serializable id) {
        return userExMapper.get(id);
    }
    
    @Override
    public List<UserEx> findAll() {
        return userExMapper.findAll();
    }
    
    @Override
    public int insert(UserEx userEx) {
        return userExMapper.insert(userEx);
    }
    
    @Override
    public int insertBatch(List<UserEx> userExs) {
        return userExMapper.insertBatch(userExs);
    }
    
    @Override
    public int update(UserEx userEx) {
        return userExMapper.update(userEx);
    }
    
    @Override
    public int delete(UserEx userEx) {
        return userExMapper.delete(userEx);
    }
}
