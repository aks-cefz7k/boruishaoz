package com.openatc.agent.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openatc.agent.model.SystemConfig;
import com.openatc.agent.service.SystemConfigDao;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
public class SystemConfigController {

    @Autowired
    private SystemConfigDao systemConfigDao;

    @GetMapping(value = "/systemconfig")
    public RESTRetBase getSystemConfig() {
        Sort.Order order = Sort.Order.asc("module");
        Sort sort = Sort.by(order);
        return RESTRetUtils.successObj(systemConfigDao.findAll(sort));
    }

    @PostMapping(value = "/systemconfig")
    public RESTRetBase addSystemConfig(@RequestBody SystemConfig systemConfig) {
        return RESTRetUtils.successObj(systemConfigDao.save(systemConfig));
    }

    @PutMapping(value = "/systemconfig")
    public RESTRetBase updateSystemConfig(@RequestBody SystemConfig systemConfig) {
        return RESTRetUtils.successObj(systemConfigDao.save(systemConfig));
    }

    @DeleteMapping(value = "/systemconfig/{id}")
    public RESTRetBase deleteSystemConfig(@PathVariable Long id) {
        systemConfigDao.deleteById(id);
        return RESTRetUtils.successObj(true);
    }

    @GetMapping(value = "/systemconfig/{module}")
    public RESTRetBase getSystemConfigByModule(@PathVariable String module) {
        return RESTRetUtils.successObj(systemConfigDao.findAllByModule(module));
    }

    /**
     * @Author: yangyi
     * @Date: 2022/3/1 13:33
     * @Description:
     */
    @PostMapping("/systemconfig/getList")
    public RESTRetBase getSystemConfigList(@RequestBody JsonObject param) {
        List<SystemConfig> list;
        String module = param.get("module").getAsString();
        JsonElement jeIsValid = param.get("isValid");
        if (jeIsValid == null) {
            list = systemConfigDao.findAllByModule(module);
        } else {
            Boolean isValid = jeIsValid.getAsBoolean();
            list = systemConfigDao.findAllByModuleAndAndIsValid(module, isValid);
        }
        return RESTRetUtils.successObj(list);
    }
}
