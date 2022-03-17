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
package com.openatc.agent.controller;

import com.openatc.agent.service.SysConfigRepository;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@CrossOrigin
public class SysConfigController {
    private Logger logger = Logger.getLogger(RouteController.class.toString());

    @Autowired(required = false)
    private SysConfigRepository sysConfigRepository;

    // 查询全部配置
    @GetMapping(value = "/sysconfig")
    public RESTRetBase getSysConfig() {
        logger.info("GetSysConfig");
        //获取全部配置
        return RESTRetUtils.successObj(sysConfigRepository.findAll());
    }

    // 按照类型配置查询
    @GetMapping(value = "/sysconfig/{configtype}")
    public RESTRetBase getSysConfigByType(@PathVariable int configtype) {
        logger.info("GetSysConfigByType");
        //获取配置
        return RESTRetUtils.successObj(sysConfigRepository.findByConfigtype(configtype));
    }

}
