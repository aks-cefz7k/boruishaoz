package com.openatc.agent.controller;

import com.google.gson.JsonObject;
import com.openatc.agent.model.DictConfig;
import com.openatc.agent.service.DictConfigRepository;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.openatc.core.common.IErrorEnumImplOuter.E_1003;


/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/

/**
 * @author ：zhangwenchao
 * @date ：Created in 2022/1/4 16:41
 * @description：字典配置接口
 * @modified By：
 * @version: $
 */

@RestController
@CrossOrigin
public class DictController {

    private Logger logger = LoggerFactory.getLogger(DictController.class);

    @Autowired
    private DictConfigRepository dictConfigRepository;

    // 查询全部配置
    @GetMapping(value = "/dict")
    public RESTRetBase getDictConfig() {
        return RESTRetUtils.successObj(dictConfigRepository.findAll());
    }

    // 按照类型配置查询
    @GetMapping(value = "/dict/{configtype}")
    public RESTRetBase getDictConfigByType(@PathVariable String configtype) {
        return RESTRetUtils.successObj(dictConfigRepository.findByConfigtype(configtype));
    }


    // 设置配置
    @PostMapping(value = "/dict")
    public RESTRetBase setDictConfig(@RequestBody DictConfig dictConfig) {
        String key = dictConfig.getKey();
        String value = dictConfig.getValue();
        if(key.isEmpty() || value.isEmpty())
            return RESTRetUtils.errorObj(E_1003);
        String type = dictConfig.getType();
        if (type.equals("platform")) {
            List<DictConfig> list = dictConfigRepository.findByConfigtypeAndKey(type,key);
            if (list.size() > 0) {
                int id = dictConfig.getId();
                if (list.get(0).getId() != id) {
                    return RESTRetUtils.errorObj(IErrorEnumImplOuter.E_1004);
                }
            }
        }
        List<DictConfig> find = dictConfigRepository.findByConfigtypeAndKeyAndVale(dictConfig.getType(),key, value);
        if(find.size() == 0 )
            return RESTRetUtils.successObj(dictConfigRepository.save(dictConfig));
        else{
            return RESTRetUtils.successObj();
        }
    }

    // 删除配置
    @DeleteMapping(value = "/dict")
    public RESTRetBase delDictConfig(@RequestBody DictConfig dictConfig) {
        dictConfigRepository.delete(dictConfig);
        return RESTRetUtils.successObj();
    }

}