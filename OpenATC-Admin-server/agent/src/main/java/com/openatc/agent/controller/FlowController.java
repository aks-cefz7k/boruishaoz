package com.openatc.agent.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.agent.service.AscsDao;
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

    @Autowired
    AscsDao ascsDao;

    private Logger logger = LoggerFactory.getLogger(FlowController.class);

    @PostMapping(value = "/flow/history")
    public RESTRetBase getHistoryFlow(@RequestBody JsonObject jsonObject) {
        String agentId = jsonObject.get("agentid").getAsString();
        AscsBaseModel ascsBaseModel = ascsDao.getAscsByID(agentId);
        String ip = ascsBaseModel.getJsonparam().get("ip").getAsString();
        String url = "http://" + ip + ":8012/openatc/flow/history"; //读取流量文件
        String json = MyHttpUtil.doPost(url,new JsonObject().toString());
        Gson gson = new Gson();
        return gson.fromJson(json, RESTRet.class);
    }
}
