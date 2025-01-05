package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.agent.utils.MyHttpUtil;
import com.openatc.comm.data.AscsBaseModel;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class FlowController {

    private Logger logger = LoggerFactory.getLogger(FlowController.class);


    @Autowired
    private DevController devController;

    @PostMapping(value = "/flow/history")
    public RESTRetBase getHistoryFlow(@RequestBody JsonObject jsonObject) {
        String agentId = jsonObject.get("agentId").getAsString();
        RESTRet<AscsBaseModel> restRet = null;
        try {
            restRet = (RESTRet<AscsBaseModel>) devController.GetDevById(agentId);
        } catch (ParseException e) {
            logger.warn(e.getMessage());
        }
        AscsBaseModel ascsBaseModel = restRet.getData();
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
        String url = "http://" + ip + ":8012/openatc/flow/history"; //读取流量文件
        String json = MyHttpUtil.doPost(url,new JsonObject().toString());
        Gson gson = new Gson();
        return gson.fromJson(json, RESTRet.class);
    }
}
