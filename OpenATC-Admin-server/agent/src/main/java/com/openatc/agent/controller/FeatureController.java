package com.openatc.agent.controller;

import com.openatc.agent.service.FeatureService;
import com.openatc.comm.data.MessageData;
import com.openatc.core.model.RESTRet;
import com.openatc.core.util.RESTRetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Classname FeatureController
 * @Description 参数相关api
 * @Date 2022/3/28 11:18
 * @Created by panleilei
 */
@RestController
@CrossOrigin
public class FeatureController {
    @Autowired
    FeatureService featureService;


    // 保存参数
    public RESTRet saveFeature(MessageData requestData){

       return RESTRetUtils.successObj("");
    }
    // 获取参数
    public RESTRet getFeatureAllByAgentId(@PathVariable String agentid){

        return RESTRetUtils.successObj("");
    }
}
