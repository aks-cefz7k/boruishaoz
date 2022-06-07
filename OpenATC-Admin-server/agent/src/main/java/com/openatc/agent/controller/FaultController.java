package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.agent.model.Fault;
import com.openatc.agent.service.FaultDao;
import com.openatc.agent.service.impl.FaultServiceImpl;
import com.openatc.agent.utils.DateUtil;
import com.openatc.comm.data.MessageData;
import com.openatc.core.model.DevCommError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class FaultController {
    @Autowired
    FaultDao faultDao;

    @Autowired
    FaultServiceImpl faultService;

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
            jsonObject.addProperty("m_unFaultOccurTime", 0);
        }
        if (fault.getM_unFaultRenewTime() != 0) {
            jsonObject.addProperty("m_unFaultRenewTime", DateUtil.longToString(fault.getM_unFaultRenewTime() * 1000));
        } else {
            jsonObject.addProperty("m_unFaultRenewTime", 0);
        }
        return jsonObject;
    }

    @PostMapping(value = "/fault/history/ftp")
    public RESTRetBase getHistoryFlow(@RequestBody JsonObject jsonObject) throws IOException, ParseException {
        // 1 获取信号机的用户名和密码
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        // 2 包装信号机的messageData
        String agentid = jsonObject.get("agentid").getAsString();
        RESTRetBase historyFlow = faultService.getHistoryFault(agentid, username, password);
        return historyFlow;
    }
}
