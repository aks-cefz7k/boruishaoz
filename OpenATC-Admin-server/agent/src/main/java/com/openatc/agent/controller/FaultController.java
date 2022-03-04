package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.agent.model.Fault;
import com.openatc.agent.service.FaultDao;
import com.openatc.agent.utils.DateUtil;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
public class FaultController {
    @Autowired
    FaultDao faultDao;

    Gson gson = new Gson();

    // 查询所有当前故障故障
    @GetMapping(value = "/fault/current")
    public RESTRetBase getCurrentFaults() {
        return RESTRetUtils.successObj(transformFaultlist(faultDao.selectCurrentFaults()));
    }

    // 查询单个当前故障故障
    @GetMapping(value = "/fault/{agentid}/current")
    public RESTRetBase getCurrentFaults(@PathVariable String agentid) {
        return RESTRetUtils.successObj(transformFaultlist(faultDao.selectCurrentFaults(agentid)));
    }

    // 查询所有历史故障
    @GetMapping(value = "/fault/history")
    public RESTRetBase getHistoryFaults() {
        return RESTRetUtils.successObj(transformFaultlist(faultDao.selectHistoryFaults()));
    }

    // 查询所有历史故障
    @GetMapping(value = "/fault/{agentid}/history")
    public RESTRetBase getHistoryFaults(@PathVariable String agentid) {
        return RESTRetUtils.successObj(transformFaultlist(faultDao.selectHistoryFaults(agentid)));
    }

    // 删除故障
    @DeleteMapping(value = "/fault/{id}")
    public RESTRetBase deleteFault(@PathVariable Long id) {
        Optional<Fault> optFault = faultDao.findById(id);
        Fault fault = optFault.get();
        if (fault != null) fault.setM_unFaultRenewTime(System.currentTimeMillis() / 1000);
        faultDao.save(fault);
        return RESTRetUtils.successObj();
    }

    public List<JsonObject> transformFaultlist(List<Fault> faults) {
        List<JsonObject> jsonObjects = new ArrayList<>();
        for (Fault fault : faults) {
            JsonObject jsonObject = transformFault(fault);
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    private JsonObject transformFault(Fault fault) {
        JsonObject jsonObject = gson.fromJson(gson.toJson(fault), JsonObject.class);
        if (fault.getM_unFaultOccurTime() != 0) {
            jsonObject.addProperty("m_unFaultOccurTime", DateUtil.longToString(fault.getM_unFaultOccurTime() * 1000));
        } else {
            jsonObject.add("m_unFaultOccurTime", null);
        }
        if (fault.getM_unFaultRenewTime() != 0) {
            jsonObject.addProperty("m_unFaultRenewTime", DateUtil.longToString(fault.getM_unFaultRenewTime() * 1000));
        } else {
            jsonObject.add("m_unFaultRenewTime", null);
        }
        return jsonObject;
    }
}
