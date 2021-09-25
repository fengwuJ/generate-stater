package com.generator.generatestater.test.controller;

import com.generator.generatestater.test.dto.req.UserExReq;
import com.generator.generatestater.test.dto.res.UserExRes;
import com.generator.generatestater.test.dto.search.UserExSearch;
import com.generator.generatestater.test.service.UserExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Apollo Jiang
 * @date 2021-9-25
 */
@RestController
@RequestMapping(value = "/")
public class UserExController {
    @Autowired
    private UserExService userExService;

    @GetMapping(value = "/page")
    public Object page(@RequestBody UserExSearch search) {

        Object result = userExService.page(search);

        return result;
    }

    @GetMapping(value = "/list")
    public List<UserExRes> list() {

        List<UserExRes> userExResList = userExService.findAll();

        return userExResList;
    }

    @GetMapping(value = "/{id}")
    public UserExRes detail(@PathVariable("id") Serializable id) {

        UserExRes userExRes = userExService.findById(id);

        return userExRes;
    }

    @PostMapping
    public void add(@RequestBody UserExReq userExReq) {

        userExService.save(userExReq);

    }

    @PutMapping
    public void update(@RequestBody UserExReq userExReq) {

        userExService.update(userExReq);

    }


    @DeleteMapping
    public void delete(@RequestBody List<Serializable> ids) {

        userExService.deleteByIds(ids);

    }

}
