package com.openatc.agent.controller;

import com.google.gson.JsonObject;
import com.openatc.agent.service.impl.FlowServiceImpl;
import com.openatc.comm.data.MessageData;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

@RestController
public class FlowController {
    @Autowired
    private FlowServiceImpl flowService;

    @Autowired
    private MessageController messageController;

    @PostMapping(value = "/flow/history")
    public RESTRetBase getHistoryFlow(@RequestBody JsonObject jsonObject) throws IOException, ParseException {
        String username = jsonObject.get("username").getAsString();
        String agentid = jsonObject.get("agentid").getAsString();
        String password = jsonObject.get("password").getAsString();
        JsonObject data = new JsonObject();
        data.addProperty("udiskset",1);
        data.addProperty("gainstatus",1);
        MessageData messageData = new MessageData(agentid,"set-request","status/volumelog",data);
        messageController.postDevsMessage(null,messageData);
        Map<String, JsonObject> historyFlow = flowService.getHistoryFlow(agentid, username, password);
        return RESTRetUtils.successObj(historyFlow);
    }
}
