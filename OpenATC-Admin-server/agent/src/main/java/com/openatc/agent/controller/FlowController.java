package com.openatc.agent.controller;

import com.google.gson.JsonObject;
import com.openatc.agent.service.impl.FlowServiceImpl;
import com.openatc.comm.data.MessageData;
import com.openatc.core.model.DevCommError;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
public class FlowController {
    @Autowired
    private FlowServiceImpl flowService;

    @Autowired
    private MessageController messageController;

    @PostMapping(value = "/flow/history")
    public RESTRetBase getHistoryFlow(@RequestBody JsonObject jsonObject) throws IOException, ParseException {
        // 1 获取信号机的用户名和密码
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        // 2 包装信号机的messageData
        String agentid = jsonObject.get("agentid").getAsString();
        JsonObject data = new JsonObject();
        data.addProperty("udiskset",1);
        data.addProperty("gainstatus",1);
        MessageData messageData = new MessageData(agentid,"set-request","status/volumelog",data);
        RESTRet restRet = messageController.postDevsMessage(null, messageData);
        if (restRet.getData() instanceof DevCommError) return restRet;
        RESTRetBase historyFlow = flowService.getHistoryFlow(agentid, username, password);
        return historyFlow;
    }
}
